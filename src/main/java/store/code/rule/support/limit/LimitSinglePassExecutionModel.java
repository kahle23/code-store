/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.rule.support.limit;

import store.code.rule.RuleResult;
import store.code.rule.support.SinglePassExecutionModel;
import kunlun.util.StrUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 限制语义的单轮执行模型（限制引擎的默认执行模型）.<br />
 * <p>
 * "限制"是规则引擎的一次使用：规则未命中时对业务动作
 * 产生拦截 / 放行 / 提示的效果（{@link Effect}）.
 * 本类在单轮执行之上按限制语义解释汇总结果：
 * <ul>
 * <li>平铺组合：未命中且效果为 BLOCK 的规则参与 AND / OR 组合决定是否整体拦截；</li>
 * <li>组合表达式：整体是否命中由条件树求值，效果、提示语与数值快照仍按明细呈现；</li>
 * <li>效果为 TIP 的规则永不拦截，提示语呈现在总结果中；</li>
 * <li>效果为 ALLOW 的规则仅记录在明细中.</li>
 * </ul>
 * @author Kahle
 */
public class LimitSinglePassExecutionModel extends SinglePassExecutionModel {

    /**
     * 把各单规则结果按限制语义汇总为总结果（平铺兜底）.<br />
     * @param details 各规则的结果明细
     * @return 总结果（LimitRuleResult）
     */
    @Override
    protected RuleResult combine(List<RuleResult> details) {
        if (details == null || details.isEmpty()) {
            LimitRuleResult summary = new LimitRuleResult();
            summary.setDetails(details);
            summary.setMatched(true);
            return summary;
        }
        boolean andBlock = false;
        boolean hasOr = false;
        boolean orAllBlock = true;
        for (RuleResult item : details) {
            boolean blocked = isBlocked(item);
            store.code.rule.CombineMode mode = item.getCombineMode();
            if (mode == null || mode == store.code.rule.CombineMode.AND) {
                andBlock = andBlock || blocked;
            }
            else {
                hasOr = true;
                orAllBlock = orAllBlock && blocked;
            }
        }
        boolean blocked = andBlock || (hasOr && orAllBlock);
        return summarize(!blocked, details);
    }

    /**
     * 按组合表达式把各单规则结果按限制语义汇总为总结果.<br />
     * <p>
     * 整体是否命中由条件树对"规则编码 -&gt; 是否命中"结果表求值得出，
     * 效果、提示语与数值快照仍按明细中的限制语义呈现.
     * @param expression 组合表达式
     * @param details 各规则的结果明细
     * @return 总结果（LimitRuleResult）
     */
    @Override
    protected RuleResult combineByExpression(String expression, List<RuleResult> details) {
        RuleResult base = super.combineByExpression(expression, details);
        return summarize(base.isMatched(), details);
    }

    /**
     * 按整体命中与否与各明细的限制语义装配总结果.<br />
     */
    protected LimitRuleResult summarize(boolean matched, List<RuleResult> details) {
        LimitRuleResult summary = new LimitRuleResult();
        summary.setDetails(details);
        summary.setMatched(matched);
        boolean hasTip = false;
        List<String> messages = new ArrayList<String>();
        for (RuleResult item : details) {
            boolean blocked = isBlocked(item);
            boolean tip = item instanceof LimitRuleResult
                    && ((LimitRuleResult) item).isTipOnly();
            if ((blocked || tip) && StrUtil.isNotBlank(item.getMessage())) {
                messages.add(item.getMessage());
            }
            if (blocked && summary.getUsagedValue() == null) {
                summary.setUsagedValue(((LimitRuleResult) item).getUsagedValue());
                summary.setThresholdValue(((LimitRuleResult) item).getThresholdValue());
                summary.setRuleCode(item.getRuleCode());
                summary.setRuleName(item.getRuleName());
            }
            hasTip = hasTip || tip;
        }
        summary.setEffect(matched ? (hasTip ? Effect.TIP : null) : Effect.BLOCK);
        if (!messages.isEmpty()) { summary.setMessage(joinMessages(messages)); }
        return summary;
    }

    /**
     * 判断单条规则结果是否为限制拦截
     * （未带效果的通用结果，未命中视同拦截）.<br />
     */
    protected boolean isBlocked(RuleResult item) {
        if (item instanceof LimitRuleResult) { return ((LimitRuleResult) item).isBlocked(); }

        return !item.isMatched();
    }

}
