/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.rule;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 规则上下文（事实集合 / 工作内存）.<br />
 * <p>
 * 由业务代码在钩子点构造：场景码、维度（如 orgId / userId）
 * 与业务数据. 它在整次执行中贯穿传递：
 * 策略可把中间产出写回 bizData（addBizData），
 * 按执行顺序排列的后续规则可直接读取——
 * 规则间依赖（前一条规则的产出作为后一条的输入）无需额外机制.
 * @author Kahle
 */
public class RuleContext {

    private String sceneCode;
    private Map<String, String> dimensions;
    private Map<String, Object> bizData;

    public RuleContext() {
        this(null, null, null);
    }

    public RuleContext(String sceneCode) {
        this(sceneCode, null, null);
    }

    public RuleContext(String sceneCode, Map<String, String> dimensions, Map<String, Object> bizData) {
        this.sceneCode = sceneCode;
        this.dimensions = dimensions == null
                ? new LinkedHashMap<String, String>() : dimensions;
        this.bizData = bizData == null
                ? new LinkedHashMap<String, Object>() : bizData;
    }

    /**
     * 添加一个维度（如 orgId=1001）.<br />
     * @param key 维度键
     * @param value 维度值
     * @return 当前上下文
     */
    public RuleContext addDimension(String key, String value) {
        dimensions.put(key, value);
        return this;
    }

    /**
     * 添加业务数据（如 demandId=1）.<br />
     * @param key 业务数据键
     * @param value 业务数据值
     * @return 当前上下文
     */
    public RuleContext addBizData(String key, Object value) {
        bizData.put(key, value);
        return this;
    }

    /**
     * 按键获取维度值.<br />
     * @param key 维度键
     * @return 维度值或 Null
     */
    public String getDimension(String key) {

        return dimensions.get(key);
    }

    /**
     * 按键获取业务数据.<br />
     * @param key 业务数据键
     * @return 业务数据值或 Null
     */
    public Object getBizData(String key) {

        return bizData.get(key);
    }

    public String getSceneCode() {

        return sceneCode;
    }

    public void setSceneCode(String sceneCode) {
        this.sceneCode = sceneCode;
    }

    public Map<String, String> getDimensions() {

        return dimensions;
    }

    public void setDimensions(Map<String, String> dimensions) {
        this.dimensions = dimensions == null
                ? new LinkedHashMap<String, String>() : dimensions;
    }

    public Map<String, Object> getBizData() {

        return bizData;
    }

    public void setBizData(Map<String, Object> bizData) {
        this.bizData = bizData == null
                ? new LinkedHashMap<String, Object>() : bizData;
    }

}
