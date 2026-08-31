/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.rule;

/**
 * 规则求值器（执行模型回调引擎的单规则求值能力）.<br />
 * <p>
 * 执行模型（{@link RuleExecutionModel}）决定"怎么执行"，
 * 而单条规则的求值（参数匹配、策略路由、留痕）属于引擎侧能力，
 * 由本接口回调，避免执行模型关心数据访问细节.
 * @author Kahle
 */
public interface RuleEvaluator {

    /**
     * 求值单条规则（参数匹配、策略路由、结果补全、留痕）.<br />
     * @param rule 规则
     * @param facts 规则上下文（事实集合，策略可写回供后续规则读取）
     * @return 单规则结果或 Null（Null 表示跳过该规则）
     */
    RuleResult evaluate(Rule rule, RuleContext facts);

    /**
     * 查场景的组合表达式（数据访问钩子，可返回 Null）.<br />
     * @param sceneCode 场景码
     * @return 组合表达式或 Null
     */
    String findCombineExpression(String sceneCode);

}
