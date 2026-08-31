/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.rule.condition;

import java.util.Map;

/**
 * 条件树的叶子节点：引用一个规则编码.<br />
 * <p>
 * 结果表中不存在该规则（规则未执行）时按未命中处理.
 * @author Kahle
 */
public class RuleRefCondition implements Condition {

    private final String ruleCode;

    public RuleRefCondition(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    /**
     * 获取引用的规则编码.<br />
     * @return 规则编码
     */
    public String getRuleCode() {

        return ruleCode;
    }

    @Override
    public boolean evaluate(Map<String, Boolean> results) {
        Boolean matched = results.get(ruleCode);
        return Boolean.TRUE.equals(matched);
    }

    @Override
    public String toString() {

        return ruleCode;
    }

}
