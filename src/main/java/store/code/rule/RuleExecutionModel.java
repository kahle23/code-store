/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.rule;

import java.util.List;

/**
 * 为规则的执行模型提供最高层次的抽象.<br />
 * <p>
 * 执行模型决定"怎么执行"：哪些规则参与、什么顺序、执行几轮、
 * 单规则结果如何组合成总结果（布尔组合、算术聚合、首条生效...）.
 * 引擎（{@link RuleEngine}）只负责入口形状与单规则求值能力
 * （经 {@link RuleEvaluator} 回调），执行语义全部由执行模型承载，
 * 可按场景替换（单轮求值、链式依赖、多轮推理、评分聚合...）.
 * <p>
 * 这是不变的入口与可生长的语义之间的边界：
 * 入口十年不变，执行模型无限生长.
 * @author Kahle
 */
public interface RuleExecutionModel {

    /**
     * 按执行模型的语义执行规则集.<br />
     * @param sceneCode 场景码
     * @param rules 待执行的规则集（已按优先级排序、已过滤启用）
     * @param facts 规则上下文（事实集合，策略可写回供后续规则读取）
     * @param evaluator 引擎侧的单规则求值能力
     * @return 总结果
     */
    RuleResult execute(String sceneCode, List<Rule> rules, RuleContext facts, RuleEvaluator evaluator);

}
