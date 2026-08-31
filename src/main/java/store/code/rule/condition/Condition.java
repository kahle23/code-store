/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.rule.condition;

import java.util.Map;

/**
 * 为条件提供最高层次的抽象（组合模式的条件树）.<br />
 * <p>
 * 条件树是布尔组合表达式的内部模型（AST）：
 * 叶子（{@link RuleRefCondition}，引用规则编码）经
 * AND / OR / NOT 节点任意嵌套组合而成.
 * 求值时传入"规则编码 -&gt; 是否命中"的结果表.
 * @author Kahle
 */
public interface Condition {

    /**
     * 按各规则的命中结果对条件树求值.<br />
     * @param results 规则命中结果表（规则编码 -&gt; 是否命中）
     * @return True 表示条件成立
     */
    boolean evaluate(Map<String, Boolean> results);

}
