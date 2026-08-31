/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.rule;

import java.util.ArrayList;
import java.util.List;

/**
 * 规则执行结果.<br />
 * <p>
 * 规则策略返回单条规则的结果（是否命中 + 任意输出），
 * 引擎将各规则结果汇总为总结果（附每条规则的明细）.
 * 结果不预设任何业务语义，"命中后要做什么"
 * （拦截、提示、计价...）由使用方自行解释.
 * @author Kahle
 */
public class RuleResult {

    private Boolean matched;
    private String ruleCode;
    private String ruleName;
    private String message;
    private CombineMode combineMode;
    private Object output;
    private List<RuleResult> details;

    public RuleResult() {
    }

    /**
     * 构建一个命中的结果.<br />
     * @return 命中的结果
     */
    public static RuleResult matched() {
        RuleResult result = new RuleResult();
        result.setMatched(true);
        return result;
    }

    /**
     * 构建一个带提示语的未命中结果.<br />
     * @param message 提示语
     * @return 未命中的结果
     */
    public static RuleResult notMatched(String message) {
        RuleResult result = new RuleResult();
        result.setMatched(false);
        result.setMessage(message);
        return result;
    }

    /**
     * 添加一条单规则结果明细.<br />
     * @param detail 单规则结果
     * @return 当前结果
     */
    public RuleResult addDetail(RuleResult detail) {
        if (details == null) { details = new ArrayList<RuleResult>(); }
        details.add(detail);
        return this;
    }

    /**
     * 判断规则是否命中.<br />
     * @return True 表示命中
     */
    public boolean isMatched() {

        return matched != null && matched;
    }

    public Boolean getMatched() {

        return matched;
    }

    public void setMatched(Boolean matched) {
        this.matched = matched;
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

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取规则的组合方式（由引擎按规则配置填充，
     * 用于汇总总结果）.<br />
     * @return 组合方式
     */
    public CombineMode getCombineMode() {

        return combineMode;
    }

    public void setCombineMode(CombineMode combineMode) {
        this.combineMode = combineMode;
    }

    /**
     * 获取规则的输出（由策略产生，任意类型）.<br />
     * @return 规则的输出或 Null
     */
    public Object getOutput() {

        return output;
    }

    public void setOutput(Object output) {
        this.output = output;
    }

    public List<RuleResult> getDetails() {

        return details;
    }

    public void setDetails(List<RuleResult> details) {
        this.details = details;
    }

}
