/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.rule;

/**
 * 规则策略（规则域的内部 SPI）.<br />
 * <p>
 * 策略描述"求值什么"：读取评估上下文
 * （含命中的维度化参数）进行判定与计算，
 * 返回"是否命中 + 任意输出"的结果.
 * @author Kahle
 */
public interface RuleStrategy {

    /**
     * 执行规则求值.<br />
     * @param context 评估上下文（含规则、命中的
     *                维度化参数与外层规则上下文）
     * @return 求值结果（不允许为 Null）
     */
    RuleResult evaluate(RuleEvaluationContext context);

}
