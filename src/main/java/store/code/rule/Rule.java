/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.rule;

/**
 * 规则模型.<br />
 * <p>
 * 规则挂在场景下，描述"怎么求值"：
 * 策略（Java Bean 或脚本）、优先级、与其他规则的组合方式.
 * @author Kahle
 */
public class Rule {

    private Long id;
    private String sceneCode;
    private String ruleCode;
    private String ruleName;
    private StrategyType strategyType;
    private String strategyBean;
    private Integer priority;
    private CombineMode combineMode;
    private String message;
    private Boolean enabled;

    public Rule() {
    }

    public Rule(String sceneCode, String ruleCode, String ruleName) {
        this.sceneCode = sceneCode;
        this.ruleCode = ruleCode;
        this.ruleName = ruleName;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSceneCode() {

        return sceneCode;
    }

    public void setSceneCode(String sceneCode) {
        this.sceneCode = sceneCode;
    }

    public String getRuleCode() {

        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getRuleName() {

        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public StrategyType getStrategyType() {

        return strategyType;
    }

    public void setStrategyType(StrategyType strategyType) {
        this.strategyType = strategyType;
    }

    public String getStrategyBean() {

        return strategyBean;
    }

    public void setStrategyBean(String strategyBean) {
        this.strategyBean = strategyBean;
    }

    public Integer getPriority() {

        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public CombineMode getCombineMode() {

        return combineMode;
    }

    public void setCombineMode(CombineMode combineMode) {
        this.combineMode = combineMode;
    }

    /**
     * 获取规则的提示语模板（支持占位符，
     * 由引擎在结果产生后格式化）.<br />
     * @return 提示语模板
     */
    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getEnabled() {

        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}
