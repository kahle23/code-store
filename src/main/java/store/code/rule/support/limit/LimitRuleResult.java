/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.rule.support.limit;

import store.code.rule.RuleResult;

import java.math.BigDecimal;

/**
 * 限制规则的执行结果.<br />
 * <p>
 * 在通用结果之上补充限制域语义：
 * 规则未命中时的效果，以及已用值与阈值的快照.
 * @author Kahle
 */
public class LimitRuleResult extends RuleResult {

    private Effect effect;
    private BigDecimal usagedValue;
    private BigDecimal thresholdValue;

    public LimitRuleResult() {
    }

    /**
     * 构建一个命中（放行）的结果.<br />
     * @return 命中的结果
     */
    public static LimitRuleResult matched() {
        LimitRuleResult result = new LimitRuleResult();
        result.setMatched(true);
        return result;
    }

    /**
     * 构建一个带提示语的未命中（拦截）结果.<br />
     * @param message 提示语
     * @return 未命中的结果
     */
    public static LimitRuleResult blocked(String message) {
        LimitRuleResult result = new LimitRuleResult();
        result.setMatched(false);
        result.setEffect(Effect.BLOCK);
        result.setMessage(message);
        return result;
    }

    /**
     * 判断结果是否表示拦截业务动作.<br />
     * @return True 表示应拦截
     */
    public boolean isBlocked() {

        return !isMatched() && effect == Effect.BLOCK;
    }

    /**
     * 判断结果是否表示仅提示（不拦截）.<br />
     * @return True 表示放行并附带提示
     */
    public boolean isTipOnly() {

        return !isMatched() && effect == Effect.TIP;
    }

    /**
     * 获取规则未命中时的效果.<br />
     * @return 未命中时的效果
     */
    public Effect getEffect() {

        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    /**
     * 获取实际值快照（如已用货值 + 当前申请）.<br />
     * @return 实际值快照或 Null
     */
    public BigDecimal getUsagedValue() {

        return usagedValue;
    }

    public void setUsagedValue(BigDecimal usagedValue) {
        this.usagedValue = usagedValue;
    }

    /**
     * 获取阈值快照（命中维度配置的数值）.<br />
     * @return 阈值快照或 Null
     */
    public BigDecimal getThresholdValue() {

        return thresholdValue;
    }

    public void setThresholdValue(BigDecimal thresholdValue) {
        this.thresholdValue = thresholdValue;
    }

}
