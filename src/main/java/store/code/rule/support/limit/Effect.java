/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.rule.support.limit;

/**
 * 限制规则未命中时的效果.<br />
 * @author Kahle
 */
public enum Effect {

    /**
     * 拦截业务动作（默认）.
     */
    BLOCK,

    /**
     * 直接放行（未命中仅记录在明细中）.
     */
    ALLOW,

    /**
     * 仅提示，不拦截业务动作.
     */
    TIP

}
