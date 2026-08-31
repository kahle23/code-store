/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.rule;

import store.code.rule.support.SinglePassExecutionModel;
import kunlun.util.StrUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static kunlun.util.Assert.notBlank;
import static kunlun.util.Assert.notNull;

/**
 * 抽象规则引擎（入口形状与单规则求值能力的模板）.<br />
 * <p>
 * 引擎只承载两件不变的事：
 * <ol>
 * <li>入口形状：查场景下启用的规则（优先级升序、过滤启用），
 * 委托执行模型（{@link RuleExecutionModel}）按其语义执行；</li>
 * <li>单规则求值能力（经 {@link RuleEvaluator} 回调给执行模型）：
 * 按维度匹配参数（USER &gt; ORG &gt; GLOBAL &gt; CUSTOM），
 * 装配评估上下文，按策略类型路由（JAVA：策略 Bean；SCRIPT：预留），
 * 补全结果并留痕.</li>
 * </ol>
 * "怎么执行"（顺序、轮次、结果组合方式）全部由可替换的执行模型承载：
 * 默认为单轮求值 + 平铺 / 表达式组合
 * （{@link SinglePassExecutionModel}），
 * 评分聚合、链式依赖、多轮推理等语义以新执行模型的形态接入，
 * 引擎入口不变.
 * <p>
 * 子类实现数据访问方法
 * （findRules / findRuleParams / saveLog / getStrategy），
 * 也可覆写各钩子调整单规则求值行为.
 * <p>
 * 规则上下文（{@link RuleContext}）同时充当事实集合（工作内存）：
 * 策略可把中间产出写回 bizData，
 * 按执行顺序排列的后续规则可直接读取——规则间依赖无需额外机制.
 * <p>
 * 引擎不预设任何业务语义：参数未命中时默认跳过该规则
 * （{@link #handleParamNotMatch(Rule, RuleContext)} 返回 Null），
 * 结果如何解释（拦截、提示、计价...）由使用方决定.
 * @author Kahle
 */
public abstract class AbstractRuleEngine implements RuleEngine {

    private RuleExecutionModel executionModel = new SinglePassExecutionModel();

    /**
     * 获取执行模型.<br />
     * @return 执行模型
     */
    public RuleExecutionModel getExecutionModel() {

        return executionModel;
    }

    /**
     * 设置执行模型（替换默认的单轮执行模型）.<br />
     * @param executionModel 执行模型
     */
    public void setExecutionModel(RuleExecutionModel executionModel) {
        notNull(executionModel, "Parameter \"executionModel\" must not null. ");
        this.executionModel = executionModel;
    }

    @Override
    public RuleResult execute(String sceneCode, RuleContext context) {
        notBlank(sceneCode, "Parameter \"sceneCode\" must not blank. ");
        notNull(context, "Parameter \"context\" must not null. ");
        List<Rule> rules = filterEnabled(sort(findRules(sceneCode)));
        return getExecutionModel()
                .execute(sceneCode, rules, context, createEvaluator());
    }

    /**
     * 构建回调给执行模型的单规则求值器.<br />
     * @return 单规则求值器
     */
    protected RuleEvaluator createEvaluator() {

        return new RuleEvaluator() {
            @Override
            public RuleResult evaluate(Rule rule, RuleContext facts) {

                return evaluateRule(rule, facts);
            }

            @Override
            public String findCombineExpression(String sceneCode) {

                return AbstractRuleEngine.this.findCombineExpression(sceneCode);
            }
        };
    }

    /**
     * 求值单条规则：按维度匹配参数 → 路由策略求值 → 补全结果 → 留痕.<br />
     * @param rule 规则
     * @param facts 规则上下文（事实集合）
     * @return 单规则结果或 Null（Null 表示跳过该规则）
     */
    protected RuleResult evaluateRule(Rule rule, RuleContext facts) {
        List<RuleParam> params = findRuleParams(rule);
        RuleParam param = matchParam(params, facts.getDimensions());
        RuleResult detail = param == null
                ? handleParamNotMatch(rule, facts)
                : routeStrategy(rule, param, facts);
        if (detail == null) { return null; }
        fillDetail(rule, detail);
        saveLog(rule, param, detail, facts);
        return detail;
    }

    /**
     * 过滤未启用的规则（Null 一并剔除）.<br />
     * @param rules 规则列表
     * @return 过滤后的规则列表
     */
    protected List<Rule> filterEnabled(List<Rule> rules) {
        if (rules == null) { return null; }
        List<Rule> copy = new ArrayList<Rule>();
        for (Rule rule : rules) {
            if (rule == null || Boolean.FALSE.equals(rule.getEnabled())) { continue; }
            copy.add(rule);
        }
        return copy;
    }

    /**
     * 按优先级升序排序规则（Null 靠后）.<br />
     * @param rules 规则列表
     * @return 排序后的规则列表
     */
    protected List<Rule> sort(List<Rule> rules) {
        if (rules == null || rules.size() < 2) { return rules; }
        List<Rule> copy = new ArrayList<Rule>(rules);
        Collections.sort(copy, new Comparator<Rule>() {
            @Override
            public int compare(Rule left, Rule right) {
                Integer lp = left == null || left.getPriority() == null ? Integer.MAX_VALUE : left.getPriority();
                Integer rp = right == null || right.getPriority() == null ? Integer.MAX_VALUE : right.getPriority();
                return lp.compareTo(rp);
            }
        });
        return copy;
    }

    /**
     * 按规则上下文的维度匹配参数.<br />
     * <p>
     * 匹配优先级为 USER &gt; ORG &gt; GLOBAL &gt; CUSTOM，
     * 其中 CUSTOM 维度暂不支持.
     * @param params 规则的参数列表（可为 Null）
     * @param dimensions 规则上下文的维度（如 orgId / userId）
     * @return 命中的参数或 Null
     */
    protected RuleParam matchParam(List<RuleParam> params, Map<String, String> dimensions) {
        if (params == null || params.isEmpty()) { return null; }
        List<RuleParam> sorted = new ArrayList<RuleParam>();
        for (RuleParam param : params) {
            if (param == null || Boolean.FALSE.equals(param.getEnabled())) { continue; }
            if (param.getDimensionType() == null) { continue; }
            sorted.add(param);
        }
        Collections.sort(sorted, new Comparator<RuleParam>() {
            @Override
            public int compare(RuleParam left, RuleParam right) {
                Integer lp = left.getDimensionType().getPriority();
                Integer rp = right.getDimensionType().getPriority();
                return lp.compareTo(rp);
            }
        });
        for (RuleParam param : sorted) {
            DimensionType type = param.getDimensionType();
            if (type == DimensionType.GLOBAL) { return param; }
            if (type == DimensionType.CUSTOM) { continue; }
            if (dimensions == null || dimensions.isEmpty()) { continue; }
            String key = StrUtil.isNotBlank(param.getDimensionKey())
                    ? param.getDimensionKey() : type.getDefaultKey();
            if (StrUtil.isBlank(key) || StrUtil.isBlank(param.getDimensionValue())) { continue; }
            String actualValue = dimensions.get(key);
            if (param.getDimensionValue().equals(actualValue)) { return param; }
        }
        return null;
    }

    /**
     * 按策略类型路由到规则策略并执行.<br />
     * @param rule 规则
     * @param param 命中的参数
     * @param context 规则上下文
     * @return 单条规则的结果
     */
    protected RuleResult routeStrategy(Rule rule, RuleParam param, RuleContext context) {
        StrategyType type = rule.getStrategyType() == null
                ? StrategyType.JAVA : rule.getStrategyType();
        if (type == StrategyType.SCRIPT) {
            throw new UnsupportedOperationException(
                    "The script strategy of the rule is not supported yet. ");
        }
        RuleStrategy strategy = getStrategy(rule.getStrategyBean());
        notNull(strategy, "The strategy bean \"{}\" of the rule \"{}\" could not be found. ",
                rule.getStrategyBean(), rule.getRuleCode());
        RuleResult result = strategy.evaluate(new RuleEvaluationContext(rule, param, context));
        return notNull(result, "The strategy of the rule \"{}\" returned null. ", rule.getRuleCode());
    }

    /**
     * 处理规则参数未命中的情况.<br />
     * <p>
     * 默认返回 Null 表示跳过该规则（不产生明细、不记日志），
     * 子类可覆写为返回未命中的结果以改变该行为.
     * @param rule 规则
     * @param context 规则上下文
     * @return 单条规则的结果或 Null（Null 表示跳过）
     */
    protected RuleResult handleParamNotMatch(Rule rule, RuleContext context) {

        return null;
    }

    /**
     * 按规则补全单规则结果中缺失的字段.<br />
     * @param rule 规则
     * @param detail 单规则结果
     */
    protected void fillDetail(Rule rule, RuleResult detail) {
        if (detail.getCombineMode() == null) {
            detail.setCombineMode(rule.getCombineMode() == null
                    ? CombineMode.AND : rule.getCombineMode());
        }
        if (StrUtil.isBlank(detail.getRuleCode())) { detail.setRuleCode(rule.getRuleCode()); }
        if (StrUtil.isBlank(detail.getRuleName())) { detail.setRuleName(rule.getRuleName()); }
        if (!detail.isMatched() && StrUtil.isBlank(detail.getMessage())) {
            String template = rule.getMessage();
            detail.setMessage(StrUtil.isNotBlank(template)
                    ? formatMessage(template, detail) : "规则 [" + rule.getRuleCode() + "] 未命中. ");
        }
        else if (!detail.isMatched() && StrUtil.isNotBlank(detail.getMessage())
                && detail.getMessage().indexOf("${") >= 0) {
            detail.setMessage(formatMessage(detail.getMessage(), detail));
        }
    }

    /**
     * 查场景的组合表达式（可选钩子，默认返回 Null）.<br />
     * <p>
     * 表达式由规则编码与 "&amp;&amp;"、"||"、"!"、括号组成，
     * 如 "(RULE-A &amp;&amp; RULE-B) || !RULE-C".
     * 表达式的消费方是执行模型（如单轮执行模型据此选择条件树求值）.
     * @param sceneCode 场景码
     * @return 组合表达式或 Null
     */
    protected String findCombineExpression(String sceneCode) {

        return null;
    }

    /**
     * 按结果格式化提示语模板.<br />
     * <p>
     * 支持的占位符为 "${ruleCode}"、"${ruleName}"、
     * "${message}"、"${matched}"、"${output}".
     * @param template 提示语模板
     * @param result 结果
     * @return 格式化后的提示语
     */
    protected String formatMessage(String template, RuleResult result) {
        String message = template;
        message = replace(message, "ruleCode", result.getRuleCode());
        message = replace(message, "ruleName", result.getRuleName());
        message = replace(message, "message", result.getMessage());
        message = replace(message, "matched", String.valueOf(result.isMatched()));
        message = replace(message, "output", result.getOutput() == null
                ? null : String.valueOf(result.getOutput()));
        return message;
    }

    /**
     * 替换提示语中的 "${key}" 占位符.
     */
    private String replace(String message, String key, String value) {
        if (message == null) { return null; }
        return message.replace("${" + key + "}", value == null ? "" : value);
    }

    /**
     * 查场景下启用的规则（子类负责数据访问，
     * 如数据库、缓存或内存）.<br />
     * @param sceneCode 场景码
     * @return 规则列表或 Null
     */
    protected abstract List<Rule> findRules(String sceneCode);

    /**
     * 查规则的参数列表.<br />
     * @param rule 规则
     * @return 参数列表或 Null
     */
    protected abstract List<RuleParam> findRuleParams(Rule rule);

    /**
     * 按策略 Bean 名称获取规则策略
     * （如从 Spring 容器或 Bean 容器中获取）.<br />
     * @param strategyBean 策略 Bean 名称
     * @return 规则策略或 Null
     */
    protected abstract RuleStrategy getStrategy(String strategyBean);

    /**
     * 记录单条规则的执行日志（可为空实现）.<br />
     * @param rule 规则
     * @param param 命中的参数（未命中时为 Null）
     * @param result 单规则结果
     * @param context 规则上下文
     */
    protected abstract void saveLog(Rule rule, RuleParam param, RuleResult result, RuleContext context);

}
