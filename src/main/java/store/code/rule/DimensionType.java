/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.rule;

/**
 * 规则参数的维度类型.<br />
 * <p>
 * 同一条规则的参数可以按不同维度配置，
 * 匹配优先级为：USER &gt; ORG &gt; GLOBAL &gt; CUSTOM.
 * @author Kahle
 */
public enum DimensionType {

    /**
     * 个人维度（默认维度键为 "userId"）.
     */
    USER(1, "userId"),

    /**
     * 部门维度（默认维度键为 "orgId"）.
     */
    ORG(2, "orgId"),

    /**
     * 全局维度（作为兜底，无需维度键）.
     */
    GLOBAL(3, null),

    /**
     * 自定义维度（预留，暂不支持）.
     */
    CUSTOM(9, null);

    private final int priority;
    private final String defaultKey;

    DimensionType(int priority, String defaultKey) {
        this.priority = priority;
        this.defaultKey = defaultKey;
    }

    /**
     * 获取匹配优先级（数值越小优先级越高）.<br />
     * @return 匹配优先级
     */
    public int getPriority() {

        return priority;
    }

    /**
     * 获取默认维度键（"userId" / "orgId" 等）.<br />
     * @return 默认维度键或 Null
     */
    public String getDefaultKey() {

        return defaultKey;
    }

}
