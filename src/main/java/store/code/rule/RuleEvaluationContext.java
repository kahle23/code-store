/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.rule;

import kunlun.core.Strategy;

/**
 * 单条规则的评估上下文.<br />
 * <p>
 * 引擎在调用规则策略前，把规则、命中的维度化参数
 * 与外层规则上下文装配进评估上下文.
 * @author Kahle
 */
public class RuleEvaluationContext extends Strategy.StrategyContext {

    private Rule rule;
    private RuleParam param;
    private RuleContext ruleContext;

    public RuleEvaluationContext() {
    }

    public RuleEvaluationContext(Rule rule, RuleParam param, RuleContext ruleContext) {
        this.rule = rule;
        this.param = param;
        this.ruleContext = ruleContext;
        this.setStrategy(rule == null ? null : rule.getRuleCode());
        this.setInput(ruleContext);
    }

    /**
     * 按键获取业务数据（来自规则上下文）.<br />
     * @param key 业务数据键
     * @param <T> 业务数据类型
     * @return 业务数据值或 Null
     */
    public <T> T getBizData(String key) {

        return ruleContext == null ? null
                : (T) ruleContext.getBizData(key);
    }

    /**
     * 按键获取维度值（来自规则上下文）.<br />
     * @param key 维度键
     * @return 维度值或 Null
     */
    public String getDimension(String key) {

        return ruleContext == null ? null
                : ruleContext.getDimension(key);
    }

    public Rule getRule() {

        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public RuleParam getParam() {

        return param;
    }

    public void setParam(RuleParam param) {
        this.param = param;
    }

    public RuleContext getRuleContext() {

        return ruleContext;
    }

    public void setRuleContext(RuleContext ruleContext) {
        this.ruleContext = ruleContext;
    }

}
