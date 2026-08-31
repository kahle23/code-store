/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.rule;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * 抽象规则引擎测试（纯规则层，不涉及限制语义）.<br />
 * @author Kahle
 */
public class AbstractRuleEngineTest {

    private static final String SCENE = "test.scene";

    private TestEngine engine;
    private Map<String, String> dimensions;
    private Map<String, Object> bizData;

    @Before
    public void init() {
        engine = new TestEngine();
        dimensions = new LinkedHashMap<String, String>();
        bizData = new LinkedHashMap<String, Object>();
    }

    private Rule buildRule(String ruleCode, CombineMode combineMode, String message) {
        Rule rule = new Rule(SCENE, ruleCode, ruleCode + "-name");
        rule.setStrategyType(StrategyType.JAVA);
        rule.setStrategyBean("flagStrategy");
        rule.setPriority(100);
        rule.setCombineMode(combineMode);
        rule.setMessage(message);
        rule.setEnabled(true);
        return rule;
    }

    private void registerSingleRule(Rule rule, List<RuleParam> params, Boolean matched) {
        engine.registerRule(rule);
        engine.registerParams(rule.getRuleCode(), params);
        engine.registerStrategy("flagStrategy", new FlagStrategy());
        bizData.put("matched", matched);
    }

    @Test
    public void testDimensionMatchPriority() {
        Rule rule = buildRule("R-1", CombineMode.AND, null);
        List<RuleParam> params = new ArrayList<RuleParam>();
        params.add(new RuleParam(DimensionType.USER, "88", "user-param"));
        params.add(new RuleParam(DimensionType.ORG, "1001", "org-param"));
        params.add(new RuleParam(DimensionType.GLOBAL, null, "global-param"));
        // 策略把命中参数的参数值放进 output，用于断言命中了哪一行参数
        registerSingleRule(rule, params, true);
        // USER 维度优先级最高
        dimensions.put("userId", "88");
        dimensions.put("orgId", "1001");
        RuleResult result = engine.execute(SCENE, new RuleContext(SCENE, dimensions, bizData));
        assertTrue(result.isMatched());
        assertEquals("user-param", result.getDetails().get(0).getOutput());
        // ORG 维度次之
        dimensions.remove("userId");
        result = engine.execute(SCENE, new RuleContext(SCENE, dimensions, bizData));
        assertTrue(result.isMatched());
        assertEquals("org-param", result.getDetails().get(0).getOutput());
        // GLOBAL 维度兜底
        dimensions.remove("orgId");
        result = engine.execute(SCENE, new RuleContext(SCENE, dimensions, bizData));
        assertTrue(result.isMatched());
        assertEquals("global-param", result.getDetails().get(0).getOutput());
    }

    @Test
    public void testDimensionNotMatchSkipRule() {
        Rule rule = buildRule("R-1", CombineMode.AND, null);
        List<RuleParam> params = new ArrayList<RuleParam>();
        params.add(new RuleParam(DimensionType.ORG, "1001", "org-param"));
        // 部门维度值不匹配（1001 != 1002），纯引擎默认跳过该规则
        dimensions.put("orgId", "1002");
        registerSingleRule(rule, params, false);
        RuleResult result = engine.execute(SCENE, new RuleContext(SCENE, dimensions, bizData));
        assertTrue(result.isMatched());
        assertTrue(result.getDetails().isEmpty());
        assertTrue(engine.logs.isEmpty());
    }

    @Test
    public void testAndCombine() {
        Rule rule1 = buildRule("R-1", CombineMode.AND, null);
        Rule rule2 = buildRule("R-2", CombineMode.AND, null);
        engine.registerRule(rule1);
        engine.registerRule(rule2);
        List<RuleParam> params = new ArrayList<RuleParam>();
        params.add(new RuleParam(DimensionType.GLOBAL, null, "p"));
        engine.registerParams("R-1", params);
        engine.registerParams("R-2", params);
        engine.registerStrategy("flagStrategy", new FlagStrategy());
        // R-1 未命中且 R-2 命中，AND 汇总为未命中
        bizData.put("matched", false);
        RuleResult result = engine.execute(SCENE, new RuleContext(SCENE, dimensions, bizData));
        assertFalse(result.isMatched());
        assertEquals(2, result.getDetails().size());
    }

    @Test
    public void testOrCombine() {
        Rule rule1 = buildRule("R-1", CombineMode.OR, null);
        Rule rule2 = buildRule("R-2", CombineMode.OR, null);
        engine.registerRule(rule1);
        engine.registerRule(rule2);
        List<RuleParam> params = new ArrayList<RuleParam>();
        params.add(new RuleParam(DimensionType.GLOBAL, null, "p"));
        engine.registerParams("R-1", params);
        engine.registerParams("R-2", params);
        engine.registerStrategy("flagStrategy", new FlagStrategy());
        // 全部规则未命中，OR 汇总为未命中
        bizData.put("matched", false);
        RuleResult result = engine.execute(SCENE, new RuleContext(SCENE, dimensions, bizData));
        assertFalse(result.isMatched());
        // 全部规则命中，OR 汇总为命中
        bizData.put("matched", true);
        result = engine.execute(SCENE, new RuleContext(SCENE, dimensions, bizData));
        assertTrue(result.isMatched());
    }

    @Test
    public void testOrCombineAnyMatched() {
        // R-1 命中、R-2 未命中：单靠 bizData 无法区分两条规则，
        // 用不同策略 Bean 控制：flagStrategy 恒命中、falseStrategy 恒未命中
        Rule rule1 = buildRule("R-1", CombineMode.OR, null);
        rule1.setStrategyBean("trueStrategy");
        Rule rule2 = buildRule("R-2", CombineMode.OR, null);
        rule2.setStrategyBean("falseStrategy");
        engine.registerRule(rule1);
        engine.registerRule(rule2);
        List<RuleParam> params = new ArrayList<RuleParam>();
        params.add(new RuleParam(DimensionType.GLOBAL, null, "p"));
        engine.registerParams("R-1", params);
        engine.registerParams("R-2", params);
        engine.registerStrategy("trueStrategy", new ConstantStrategy(true));
        engine.registerStrategy("falseStrategy", new ConstantStrategy(false));
        // OR 模式下任一命中即整体命中
        RuleResult result = engine.execute(SCENE, new RuleContext(SCENE, dimensions, bizData));
        assertTrue(result.isMatched());
    }

    @Test
    public void testPlaceholderFormat() {
        Rule rule = buildRule("R-1", CombineMode.AND, "规则 ${ruleCode} 未命中（matched=${matched}）");
        List<RuleParam> params = new ArrayList<RuleParam>();
        params.add(new RuleParam(DimensionType.GLOBAL, null, "p"));
        registerSingleRule(rule, params, false);
        RuleResult result = engine.execute(SCENE, new RuleContext(SCENE, dimensions, bizData));
        assertFalse(result.isMatched());
        assertEquals("规则 R-1 未命中（matched=false）", result.getMessage());
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
        Rule rule1 = buildRule("R-1", CombineMode.AND, null);
        rule1.setStrategyBean("trueStrategy");
        Rule rule2 = buildRule("R-2", CombineMode.AND, null);
        rule2.setStrategyBean("falseStrategy");
        Rule rule3 = buildRule("R-3", CombineMode.AND, null);
        rule3.setStrategyBean("trueStrategy");
        engine.registerRule(rule1);
        engine.registerRule(rule2);
        engine.registerRule(rule3);
        List<RuleParam> params = new ArrayList<RuleParam>();
        params.add(new RuleParam(DimensionType.GLOBAL, null, "p"));
        engine.registerParams("R-1", params);
        engine.registerParams("R-2", params);
        engine.registerParams("R-3", params);
        engine.registerStrategy("trueStrategy", new ConstantStrategy(true));
        engine.registerStrategy("falseStrategy", new ConstantStrategy(false));
        engine.combineExpression = "(R-1 || R-2) && R-3";
        // R-1 命中、R-2 未命中、R-3 命中：(真 || 假) && 真 = 整体命中
        RuleResult result = engine.execute(SCENE, new RuleContext(SCENE, dimensions, bizData));
        assertTrue(result.isMatched());
        assertEquals(3, result.getDetails().size());
        // R-3 换为未命中：(真 || 假) && 假 = 整体未命中
        rule3.setStrategyBean("falseStrategy");
        result = engine.execute(SCENE, new RuleContext(SCENE, dimensions, bizData));
        assertFalse(result.isMatched());
    }

    @Test
    public void testCombineExpressionNot() {
        Rule rule1 = buildRule("R-1", CombineMode.AND, null);
        rule1.setStrategyBean("trueStrategy");
        Rule rule2 = buildRule("R-2", CombineMode.AND, null);
        rule2.setStrategyBean("falseStrategy");
        engine.registerRule(rule1);
        engine.registerRule(rule2);
        List<RuleParam> params = new ArrayList<RuleParam>();
        params.add(new RuleParam(DimensionType.GLOBAL, null, "p"));
        engine.registerParams("R-1", params);
        engine.registerParams("R-2", params);
        engine.registerStrategy("trueStrategy", new ConstantStrategy(true));
        engine.registerStrategy("falseStrategy", new ConstantStrategy(false));
        engine.combineExpression = "!R-1 && !R-2";
        // R-1 命中、R-2 未命中：!真 && !假 = 整体未命中
        RuleResult result = engine.execute(SCENE, new RuleContext(SCENE, dimensions, bizData));
        assertFalse(result.isMatched());
    }

    /**
     * 测试用策略：按业务数据 "matched" 返回命中与否，
     * 并把命中参数的参数值放进 output.<br />
     * @author Kahle
     */
    private static class FlagStrategy implements RuleStrategy {

        @Override
        public RuleResult evaluate(RuleEvaluationContext context) {
            Boolean matched = (Boolean) context.getBizData("matched");
            RuleResult result = new RuleResult();
            result.setMatched(Boolean.TRUE.equals(matched));
            result.setOutput(context.getParam().getParamValue());
            return result;
        }

    }

    /**
     * 测试用常量策略：恒命中或恒未命中.<br />
     * @author Kahle
     */
    private static class ConstantStrategy implements RuleStrategy {

        private final boolean matched;

        ConstantStrategy(boolean matched) {
            this.matched = matched;
        }

        @Override
        public RuleResult evaluate(RuleEvaluationContext context) {
            RuleResult result = new RuleResult();
            result.setMatched(matched);
            return result;
        }

    }

    /**
     * 基于内存数据的测试引擎.<br />
     * @author Kahle
     */
    private static class TestEngine extends AbstractRuleEngine {
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
