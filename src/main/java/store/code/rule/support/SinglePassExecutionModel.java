/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.rule.support;

import store.code.rule.CombineMode;
import store.code.rule.Rule;
import store.code.rule.RuleContext;
import store.code.rule.RuleEvaluator;
import store.code.rule.RuleExecutionModel;
import store.code.rule.RuleResult;
import store.code.rule.condition.Condition;
import store.code.rule.condition.ConditionParser;
import kunlun.util.StrUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单轮执行模型（默认）.<br />
 * <p>
 * 按优先级逐条执行一遍规则（经 {@link RuleEvaluator} 回调引擎求值），
 * 再把各单规则结果汇总为总结果：
 * <ul>
 * <li>场景配置了组合表达式时，把"规则编码 -&gt; 是否命中"代入条件树求值；</li>
 * <li>未配置时按平铺组合：AND 模式的规则任一未命中即整体未命中，
 * OR 模式的规则（存在时）全部未命中才整体未命中.</li>
 * </ul>
 * 本类的汇总逻辑均为可覆写的钩子，
 * 子类（如限制语义的汇总）按需继承调整.
 * @author Kahle
 */
public class SinglePassExecutionModel implements RuleExecutionModel {

    /**
     * 组合表达式的解析缓存（表达式 -&gt; 条件树）.
     */
    private final Map<String, Condition> conditionCache =
            new ConcurrentHashMap<String, Condition>();

    @Override
    public RuleResult execute(String sceneCode, List<Rule> rules, RuleContext facts, RuleEvaluator evaluator) {
        List<RuleResult> details = new ArrayList<RuleResult>();
        if (rules != null) {
            for (Rule rule : rules) {
                if (rule == null) { continue; }
                RuleResult detail = evaluator.evaluate(rule, facts);
                if (detail == null) { continue; }
                details.add(detail);
            }
        }
        String expression = evaluator.findCombineExpression(sceneCode);
        return StrUtil.isNotBlank(expression)
                ? combineByExpression(expression, details)
                : combine(details);
    }

    /**
     * 把各单规则结果按平铺方式汇总为总结果（未配置组合表达式时的兜底）.<br />
     * <p>
     * AND 模式的规则任一未命中即整体未命中；
     * OR 模式的规则（存在时）全部未命中才整体未命中.
     * @param details 各规则的结果明细
     * @return 总结果
     */
    protected RuleResult combine(List<RuleResult> details) {
        RuleResult summary = new RuleResult();
        summary.setDetails(details);
        summary.setMatched(true);
        if (details == null || details.isEmpty()) { return summary; }
        boolean andFail = false;
        boolean hasOr = false;
        boolean orAllFail = true;
        List<String> messages = new ArrayList<String>();
        for (RuleResult detail : details) {
            boolean fail = !detail.isMatched();
            if (fail && StrUtil.isNotBlank(detail.getMessage())) {
                messages.add(detail.getMessage());
            }
            CombineMode mode = detail.getCombineMode();
            if (mode == null || mode == CombineMode.AND) {
                andFail = andFail || fail;
            }
            else {
                hasOr = true;
                orAllFail = orAllFail && fail;
            }
        }
        boolean notMatched = andFail || (hasOr && orAllFail);
        summary.setMatched(!notMatched);
        if (!messages.isEmpty()) {
            summary.setMessage(joinMessages(messages));
        }
        return summary;
    }

    /**
     * 按组合表达式把各单规则结果汇总为总结果.<br />
     * <p>
     * 各规则全部执行后，把"规则编码 -&gt; 是否命中"的结果表代入条件树求值.
     * @param expression 组合表达式
     * @param details 各规则的结果明细
     * @return 总结果
     */
    protected RuleResult combineByExpression(String expression, List<RuleResult> details) {
        RuleResult summary = new RuleResult();
        summary.setDetails(details);
        summary.setMatched(true);
        if (details == null || details.isEmpty()) { return summary; }
        Condition condition = getCondition(expression);
        Map<String, Boolean> results = new LinkedHashMap<String, Boolean>();
        List<String> messages = new ArrayList<String>();
        for (RuleResult detail : details) {
            if (StrUtil.isNotBlank(detail.getRuleCode())) {
                results.put(detail.getRuleCode(), detail.isMatched());
            }
            if (!detail.isMatched() && StrUtil.isNotBlank(detail.getMessage())) {
                messages.add(detail.getMessage());
            }
        }
        summary.setMatched(condition.evaluate(results));
        if (!summary.isMatched() && !messages.isEmpty()) {
            summary.setMessage(joinMessages(messages));
        }
        return summary;
    }

    /**
     * 解析组合表达式为条件树（带缓存）.<br />
     * @param expression 组合表达式
     * @return 条件树
     */
    protected Condition getCondition(String expression) {
        Condition condition = conditionCache.get(expression);
        if (condition != null) { return condition; }
        condition = ConditionParser.parse(expression);
        conditionCache.put(expression, condition);
        return condition;
    }

    /**
     * 拼接提示语.<br />
     */
    protected String joinMessages(List<String> messages) {
        StringBuilder joiner = new StringBuilder();
        for (String message : messages) {
            if (joiner.length() > 0) { joiner.append("; "); }
            joiner.append(message);
        }
        return joiner.toString();
    }

}
