/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.rule.condition;

import java.util.Map;

/**
 * 条件树的 NOT 节点：子条件取反.<br />
 * @author Kahle
 */
public class NotCondition implements Condition {

    private final Condition condition;

    public NotCondition(Condition condition) {
        this.condition = condition;
    }

    @Override
    public boolean evaluate(Map<String, Boolean> results) {

        return !condition.evaluate(results);
    }

    @Override
    public String toString() {

        return "!" + condition;
    }

}
