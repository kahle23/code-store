/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.rule;

/**
 * 规则与同场景其他规则的组合方式.<br />
 * @author Kahle
 */
public enum CombineMode {

    /**
     * 所有 AND 模式的规则必须全部通过（默认）.
     */
    AND,

    /**
     * 任一 OR 模式的规则通过即可.
     */
    OR

}
