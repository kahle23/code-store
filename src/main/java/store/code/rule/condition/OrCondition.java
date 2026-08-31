/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.rule.condition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 条件树的 OR 节点：任一子条件成立即成立.<br />
 * @author Kahle
 */
public class OrCondition implements Condition {

    private final List<Condition> conditions;

    public OrCondition(List<Condition> conditions) {
        this.conditions = conditions == null
                ? new ArrayList<Condition>() : conditions;
    }

    /**
     * 添加一个子条件.<br />
     * @param condition 子条件
     * @return 当前节点
     */
    public OrCondition add(Condition condition) {
        conditions.add(condition);
        return this;
    }

    @Override
    public boolean evaluate(Map<String, Boolean> results) {
        if (conditions.isEmpty()) { return true; }
        for (Condition condition : conditions) {
            if (condition.evaluate(results)) { return true; }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder joiner = new StringBuilder("(");
        for (Condition condition : conditions) {
            if (joiner.length() > 1) { joiner.append(" || "); }
            joiner.append(condition);
        }
        return joiner.append(")").toString();
    }

}
