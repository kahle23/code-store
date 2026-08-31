/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.rule;

/**
 * 规则的维度化参数模型.<br />
 * <p>
 * 一条规则可按不同维度（USER / ORG / GLOBAL / CUSTOM）
 * 配置多行参数，执行时由引擎按规则上下文的维度匹配
 * （匹配优先级为 USER &gt; ORG &gt; GLOBAL &gt; CUSTOM），
 * 并把命中的参数装配进评估上下文.
 * 参数值本身只是字符串，具体含义（数值、开关、表达式等）
 * 由规则策略自行解释.
 * @author Kahle
 */
public class RuleParam {

    private Long id;
    private Long ruleId;
    private DimensionType dimensionType;
    private String dimensionKey;
    private String dimensionValue;
    private String paramValue;
    private Boolean enabled;

    public RuleParam() {
    }

    public RuleParam(DimensionType dimensionType, String dimensionValue, String paramValue) {
        this.dimensionType = dimensionType;
        this.dimensionValue = dimensionValue;
        this.paramValue = paramValue;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRuleId() {

        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public DimensionType getDimensionType() {

        return dimensionType;
    }

    public void setDimensionType(DimensionType dimensionType) {
        this.dimensionType = dimensionType;
    }

    public String getDimensionKey() {

        return dimensionKey;
    }

    public void setDimensionKey(String dimensionKey) {
        this.dimensionKey = dimensionKey;
    }

    public String getDimensionValue() {

        return dimensionValue;
    }

    public void setDimensionValue(String dimensionValue) {
        this.dimensionValue = dimensionValue;
    }

    public String getParamValue() {

        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public Boolean getEnabled() {

        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}
