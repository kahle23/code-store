/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.rule;

import kunlun.action.SimpleActionManager;
import store.code.rule.CombineMode;
import store.code.rule.DimensionType;
import store.code.rule.Rule;
import store.code.rule.RuleContext;
import store.code.rule.RuleEngine;
import store.code.rule.RuleParam;
import store.code.rule.RuleResult;
import store.code.rule.RuleStrategy;
import store.code.rule.StrategyType;
import store.code.rule.support.limit.AbstractLimitRuleEngine;
import store.code.rule.support.limit.LimitRule;
import store.code.rule.support.limit.LimitRuleParam;
import store.code.rule.support.limit.LimitRuleResult;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * 规则引擎适配器测试.<br />
 * @author Kahle
 */
public class RuleEngineActionTest {

    private static final String SCENE = "purchase.demand.ready-precheck";

    private RuleEngine buildEngine() {
        LimitRule rule = new LimitRule(SCENE, "READY-STOCK-VALUE", "备货采购库存货值限制");
        rule.setStrategyType(StrategyType.JAVA);
        rule.setStrategyBean("amountStrategy");
        rule.setPriority(100);
        rule.setCombineMode(CombineMode.AND);
        rule.setEnabled(true);
        final List<Rule> rules = new ArrayList<Rule>();
        rules.add(rule);
        final List<RuleParam> params = new ArrayList<RuleParam>();
        params.add(new LimitRuleParam(DimensionType.GLOBAL, null, new BigDecimal("3000000")));
        return new AbstractLimitRuleEngine() {
            @Override
            protected List<Rule> findRules(String sceneCode) { return rules; }
            @Override
            protected List<RuleParam> findRuleParams(Rule rule) { return params; }
            @Override
            protected RuleStrategy getStrategy(String strategyBean) {
                return new RuleStrategy() {
                    @Override
                    public RuleResult evaluate(store.code.rule.RuleEvaluationContext context) {
                        LimitRuleResult result = new LimitRuleResult();
                        result.setUsagedValue((BigDecimal) context.getBizData("amount"));
                        result.setThresholdValue(((LimitRuleParam) context.getParam()).getThresholdValue());
                        result.setMatched(((LimitRuleParam) context.getParam()).matches(result.getUsagedValue()));
                        return result;
                    }
                };
            }
            @Override
            protected void saveLog(Rule rule, RuleParam param, RuleResult result, RuleContext context) { }
        };
    }

    @Test
    public void testCommandRoute() {
        SimpleActionManager manager = new SimpleActionManager();
        manager.registerAction(RuleEngineAction.ACTION_NAME, new RuleEngineAction(buildEngine()));
        RuleContext context = new RuleContext(SCENE);
        context.addBizData("amount", new BigDecimal("4000000"));
        // command 的 strategy 即场景码
        LimitRuleResult result = (LimitRuleResult) manager.execute(
                RuleEngineAction.ACTION_NAME + "." + SCENE, context, new Object[0]);
        assertFalse(result.isMatched());
        assertTrue(result.isBlocked());
        assertEquals(0, new BigDecimal("3000000").compareTo(result.getThresholdValue()));
    }

    @Test
    public void testShortcutRoute() {
        SimpleActionManager manager = new SimpleActionManager();
        manager.registerAction(RuleEngineAction.ACTION_NAME, new RuleEngineAction(buildEngine()));
        manager.registerShortcut(RuleContext.class, RuleEngineAction.ACTION_NAME);
        RuleContext context = new RuleContext(SCENE);
        context.addBizData("amount", new BigDecimal("2000000"));
        // command 为空，按入参类型 RuleContext 路由
        LimitRuleResult result = (LimitRuleResult) manager.execute(null, context, null);
        assertTrue(result.isMatched());
    }

    @Test
    public void testSceneCodeFallback() {
        RuleEngineAction action = new RuleEngineAction(buildEngine());
        RuleContext context = new RuleContext(SCENE);
        context.addBizData("amount", new BigDecimal("4000000"));
        // strategy 为空，回退取规则上下文内的场景码
        LimitRuleResult result = (LimitRuleResult) action.execute(null, context, null);
        assertFalse(result.isMatched());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInputNotRuleContext() {
        RuleEngineAction action = new RuleEngineAction(buildEngine());
        action.execute(SCENE, "just a string", null);
    }

}
