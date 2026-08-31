/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.rule.support.limit;

import store.code.rule.CombineMode;
import store.code.rule.DimensionType;
import store.code.rule.Rule;
import store.code.rule.RuleContext;
import store.code.rule.RuleParam;
import store.code.rule.RuleResult;
import store.code.rule.RuleStrategy;
import store.code.rule.StrategyType;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * 抽象限制规则引擎测试.<br />
 * @author Kahle
 */
public class AbstractLimitRuleEngineTest {

    private static final String SCENE = "test.scene";

    private TestLimitEngine engine;
    private Map<String, String> dimensions;
    private Map<String, Object> bizData;

    @Before
    public void init() {
        engine = new TestLimitEngine();
        dimensions = new LinkedHashMap<String, String>();
        bizData = new LinkedHashMap<String, Object>();
    }

    private LimitRule buildRule(String ruleCode, CombineMode combineMode, Effect effect, String message) {
        LimitRule rule = new LimitRule(SCENE, ruleCode, ruleCode + "-name");
        rule.setStrategyType(StrategyType.JAVA);
        rule.setStrategyBean("amountStrategy");
        rule.setPriority(100);
        rule.setCombineMode(combineMode);
        rule.setEffect(effect);
        rule.setMessage(message);
        rule.setEnabled(true);
        return rule;
    }

    private void registerSingleRule(LimitRule rule, List<RuleParam> params, BigDecimal amount) {
        engine.registerRule(rule);
        engine.registerParams(rule.getRuleCode(), params);
        engine.registerStrategy("amountStrategy", new AmountLimitStrategy());
        bizData.put("amount", amount);
    }

    @Test
    public void testDimensionMatchPriority() {
        LimitRule rule = buildRule("R-1", CombineMode.AND, Effect.BLOCK, null);
        List<RuleParam> params = new ArrayList<RuleParam>();
        params.add(new LimitRuleParam(DimensionType.USER, "88", new BigDecimal("100")));
        params.add(new LimitRuleParam(DimensionType.ORG, "1001", new BigDecimal("300")));
        params.add(new LimitRuleParam(DimensionType.GLOBAL, null, new BigDecimal("500")));
        registerSingleRule(rule, params, new BigDecimal("250"));
        // USER 维度优先级最高（阈值 100）
        dimensions.put("userId", "88");
        dimensions.put("orgId", "1001");
        RuleResult result = engine.execute(SCENE, new RuleContext(SCENE, dimensions, bizData));
        assertFalse(result.isMatched());
        LimitRuleResult detail = (LimitRuleResult) result.getDetails().get(0);
        assertEquals(0, new BigDecimal("100").compareTo(detail.getThresholdValue()));
        // ORG 维度次之（阈值 300）
        dimensions.remove("userId");
        bizData.put("amount", new BigDecimal("350"));
        result = engine.execute(SCENE, new RuleContext(SCENE, dimensions, bizData));
        assertFalse(result.isMatched());
        detail = (LimitRuleResult) result.getDetails().get(0);
        assertEquals(0, new BigDecimal("300").compareTo(detail.getThresholdValue()));
        // GLOBAL 维度兜底（阈值 500）
        dimensions.remove("orgId");
        result = engine.execute(SCENE, new RuleContext(SCENE, dimensions, bizData));
        assertTrue(result.isMatched());
        detail = (LimitRuleResult) result.getDetails().get(0);
        assertEquals(0, new BigDecimal("500").compareTo(detail.getThresholdValue()));
    }

    @Test
    public void testDimensionNotMatchDefaultBlock() {
        LimitRule rule = buildRule("R-1", CombineMode.AND, Effect.BLOCK, null);
        List<RuleParam> params = new ArrayList<RuleParam>();
        params.add(new LimitRuleParam(DimensionType.ORG, "1001", new BigDecimal("300")));
        // 部门维度值不匹配（1001 != 1002），限制引擎默认未配置即拦截
        dimensions.put("orgId", "1002");
        registerSingleRule(rule, params, new BigDecimal("250"));
        RuleResult result = engine.execute(SCENE, new RuleContext(SCENE, dimensions, bizData));
        assertFalse(result.isMatched());
        assertTrue(((LimitRuleResult) result).isBlocked());
        assertTrue(result.getMessage().contains("未配置参数"));
    }

    @Test
    public void testOperatorMatch() {
        LimitRuleParam param = new LimitRuleParam(DimensionType.GLOBAL, null, new BigDecimal("100"));
        param.setThresholdOperator("<=");
        assertTrue(param.matches(new BigDecimal("100")));
        assertFalse(param.matches(new BigDecimal("100.01")));
        param.setThresholdOperator("<");
        assertFalse(param.matches(new BigDecimal("100")));
        assertTrue(param.matches(new BigDecimal("99.99")));
        param.setThresholdOperator(">=");
        assertTrue(param.matches(new BigDecimal("100")));
        assertFalse(param.matches(new BigDecimal("99.99")));
        param.setThresholdOperator(">");
        assertFalse(param.matches(new BigDecimal("100")));
        param.setThresholdOperator("=");
        assertTrue(param.matches(new BigDecimal("100.00")));
        assertFalse(param.matches(null));
    }

    @Test
    public void testAndCombine() {
        LimitRule rule1 = buildRule("R-1", CombineMode.AND, Effect.BLOCK, null);
        LimitRule rule2 = buildRule("R-2", CombineMode.AND, Effect.BLOCK, null);
        engine.registerRule(rule1);
        engine.registerRule(rule2);
        List<RuleParam> params1 = new ArrayList<RuleParam>();
        params1.add(new LimitRuleParam(DimensionType.GLOBAL, null, new BigDecimal("100")));
        List<RuleParam> params2 = new ArrayList<RuleParam>();
        params2.add(new LimitRuleParam(DimensionType.GLOBAL, null, new BigDecimal("1000")));
        engine.registerParams("R-1", params1);
        engine.registerParams("R-2", params2);
        engine.registerStrategy("amountStrategy", new AmountLimitStrategy());
        // R-1 未命中（250 > 100）且 R-2 命中（250 <= 1000），AND 汇总为拦截
        bizData.put("amount", new BigDecimal("250"));
        RuleResult result = engine.execute(SCENE, new RuleContext(SCENE, dimensions, bizData));
        assertFalse(result.isMatched());
        assertTrue(((LimitRuleResult) result).isBlocked());
        assertEquals(0, new BigDecimal("100").compareTo(((LimitRuleResult) result).getThresholdValue()));
    }

    @Test
    public void testOrCombine() {
        LimitRule rule1 = buildRule("R-1", CombineMode.OR, Effect.BLOCK, null);
        LimitRule rule2 = buildRule("R-2", CombineMode.OR, Effect.BLOCK, null);
        engine.registerRule(rule1);
        engine.registerRule(rule2);
        List<RuleParam> params1 = new ArrayList<RuleParam>();
        params1.add(new LimitRuleParam(DimensionType.GLOBAL, null, new BigDecimal("100")));
        List<RuleParam> params2 = new ArrayList<RuleParam>();
        params2.add(new LimitRuleParam(DimensionType.GLOBAL, null, new BigDecimal("1000")));
        engine.registerParams("R-1", params1);
        engine.registerParams("R-2", params2);
        engine.registerStrategy("amountStrategy", new AmountLimitStrategy());
        // R-1 未命中且 R-2 命中，OR 汇总为放行
        bizData.put("amount", new BigDecimal("250"));
        RuleResult result = engine.execute(SCENE, new RuleContext(SCENE, dimensions, bizData));
        assertTrue(result.isMatched());
        // 两条 OR 规则全部未命中，OR 汇总为拦截
        engine.registerParams("R-2", params1);
        bizData.put("amount", new BigDecimal("1000"));
        result = engine.execute(SCENE, new RuleContext(SCENE, dimensions, bizData));
        assertFalse(result.isMatched());
    }

    @Test
    public void testTipEffect() {
        LimitRule rule = buildRule("R-1", CombineMode.AND, Effect.TIP, "额度即将用尽");
        List<RuleParam> params = new ArrayList<RuleParam>();
        params.add(new LimitRuleParam(DimensionType.GLOBAL, null, new BigDecimal("100")));
        registerSingleRule(rule, params, new BigDecimal("250"));
        RuleResult result = engine.execute(SCENE, new RuleContext(SCENE, dimensions, bizData));
        // TIP 效果永不拦截业务动作
        assertTrue(result.isMatched());
        assertEquals(Effect.TIP, ((LimitRuleResult) result).getEffect());
        assertTrue(result.getMessage().contains("额度即将用尽"));
    }

    @Test
    public void testAllowEffect() {
        LimitRule rule = buildRule("R-1", CombineMode.AND, Effect.ALLOW, null);
        List<RuleParam> params = new ArrayList<RuleParam>();
        params.add(new LimitRuleParam(DimensionType.GLOBAL, null, new BigDecimal("100")));
        registerSingleRule(rule, params, new BigDecimal("250"));
        RuleResult result = engine.execute(SCENE, new RuleContext(SCENE, dimensions, bizData));
        // ALLOW 效果永不拦截且不呈现提示语
        assertTrue(result.isMatched());
        assertNull(result.getMessage());
    }

    @Test
    public void testPlaceholderFormat() {
        LimitRule rule = buildRule("R-1", CombineMode.AND, Effect.BLOCK,
                "已用 ${usagedValue} 超过阈值 ${thresholdValue}");
        List<RuleParam> params = new ArrayList<RuleParam>();
        params.add(new LimitRuleParam(DimensionType.GLOBAL, null, new BigDecimal("100")));
        registerSingleRule(rule, params, new BigDecimal("250.50"));
        RuleResult result = engine.execute(SCENE, new RuleContext(SCENE, dimensions, bizData));
        assertFalse(result.isMatched());
        assertEquals("已用 250.50 超过阈值 100", result.getMessage());
    }

    @Test
    public void testNoRules() {
        RuleResult result = engine.execute(SCENE, new RuleContext(SCENE, dimensions, bizData));
        assertTrue(result.isMatched());
        assertTrue(result.getDetails().isEmpty());
        assertTrue(engine.logs.isEmpty());
    }

    @Test
    public void testCombineExpression() {
        LimitRule rule1 = buildRule("R-1", CombineMode.AND, Effect.BLOCK, null);
        LimitRule rule2 = buildRule("R-2", CombineMode.AND, Effect.BLOCK, null);
        engine.registerRule(rule1);
        engine.registerRule(rule2);
        List<RuleParam> params1 = new ArrayList<RuleParam>();
        params1.add(new LimitRuleParam(DimensionType.GLOBAL, null, new BigDecimal("100")));
        List<RuleParam> params2 = new ArrayList<RuleParam>();
        params2.add(new LimitRuleParam(DimensionType.GLOBAL, null, new BigDecimal("1000")));
        engine.registerParams("R-1", params1);
        engine.registerParams("R-2", params2);
        engine.registerStrategy("amountStrategy", new AmountLimitStrategy());
        engine.combineExpression = "R-1 || R-2";
        // R-1 未命中（250 > 100）且 R-2 命中（250 <= 1000），表达式整体命中
        bizData.put("amount", new BigDecimal("250"));
        RuleResult result = engine.execute(SCENE, new RuleContext(SCENE, dimensions, bizData));
        assertTrue(result.isMatched());
        // 全部未命中（1000 > 100 且 1000 > 1000），表达式整体拦截，快照取首个拦截明细
        bizData.put("amount", new BigDecimal("1000.01"));
        result = engine.execute(SCENE, new RuleContext(SCENE, dimensions, bizData));
        assertFalse(result.isMatched());
        assertTrue(((LimitRuleResult) result).isBlocked());
        assertEquals(0, new BigDecimal("100").compareTo(((LimitRuleResult) result).getThresholdValue()));
    }

    /**
     * 测试用金额限制策略：按业务数据 "amount"
     * 与命中参数的阈值比较判定是否超限.<br />
     * @author Kahle
     */
    private static class AmountLimitStrategy implements RuleStrategy {

        @Override
        public RuleResult evaluate(store.code.rule.RuleEvaluationContext context) {
            BigDecimal amount = (BigDecimal) context.getBizData("amount");
            LimitRuleParam param = (LimitRuleParam) context.getParam();
            LimitRuleResult result = new LimitRuleResult();
            result.setUsagedValue(amount);
            result.setThresholdValue(param.getThresholdValue());
            result.setMatched(param.matches(amount));
            return result;
        }

    }

    /**
     * 基于内存数据的测试引擎.<br />
     * @author Kahle
     */
    private static class TestLimitEngine extends AbstractLimitRuleEngine {
        private final Map<String, List<Rule>> rules = new LinkedHashMap<String, List<Rule>>();
        private final Map<String, List<RuleParam>> params = new LinkedHashMap<String, List<RuleParam>>();
        private final Map<String, RuleStrategy> strategies = new LinkedHashMap<String, RuleStrategy>();
        private final List<String> logs = new ArrayList<String>();
        private String combineExpression;

        void registerRule(Rule rule) {
            List<Rule> list = rules.get(rule.getSceneCode());
            if (list == null) {
                list = new ArrayList<Rule>();
                rules.put(rule.getSceneCode(), list);
            }
            list.add(rule);
        }

        void registerParams(String ruleCode, List<RuleParam> list) {
            params.put(ruleCode, list);
        }

        void registerStrategy(String beanName, RuleStrategy strategy) {
            strategies.put(beanName, strategy);
        }

        @Override
        protected List<Rule> findRules(String sceneCode) {

            return rules.get(sceneCode);
        }

        @Override
        protected String findCombineExpression(String sceneCode) {

            return combineExpression;
        }

        @Override
        protected List<RuleParam> findRuleParams(Rule rule) {

            return params.get(rule.getRuleCode());
        }

        @Override
        protected RuleStrategy getStrategy(String strategyBean) {

            return strategies.get(strategyBean);
        }

        @Override
        protected void saveLog(Rule rule, RuleParam param, RuleResult result, RuleContext context) {

            logs.add(rule.getRuleCode());
        }

    }

}
