/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.rule.support.limit;

import store.code.rule.DimensionType;
import store.code.rule.RuleParam;
import kunlun.util.StrUtil;

import java.math.BigDecimal;

/**
 * 限制规则的维度化参数模型.<br />
 * <p>
 * 在通用参数之上补充限制域语义：
 * 阈值数值与比较运算符，如"实际值 &lt;= 3000000 即通过".
 * @author Kahle
 */
public class LimitRuleParam extends RuleParam {

    /**
     * 默认比较运算符：实际值 &lt;= 阈值 即通过.
     */
    public static final String DEFAULT_OPERATOR = "<=";

    private BigDecimal thresholdValue;
    private String thresholdOperator;

    public LimitRuleParam() {
    }

    public LimitRuleParam(DimensionType dimensionType, String dimensionValue, BigDecimal thresholdValue) {
        super(dimensionType, dimensionValue, null);
        this.thresholdValue = thresholdValue;
    }

    /**
     * 按比较运算符比较实际值与阈值.<br />
     * <p>
     * 支持的运算符为 "&lt;="、"&lt;"、"&gt;="、"&gt;"、"="，
     * 默认运算符为 "&lt;=".
     * @param actualValue 实际值（Null 表示未通过）
     * @return True 表示实际值满足阈值要求
     */
    public boolean matches(BigDecimal actualValue) {
        if (actualValue == null || thresholdValue == null) { return false; }
        int compareResult = actualValue.compareTo(thresholdValue);
        String operator = StrUtil.isBlank(thresholdOperator) ? DEFAULT_OPERATOR : thresholdOperator.trim();
        if ("<=".equals(operator)) { return compareResult <= 0; }
        else if ("<".equals(operator)) { return compareResult < 0; }
        else if (">=".equals(operator)) { return compareResult >= 0; }
        else if (">".equals(operator)) { return compareResult > 0; }
        else if ("=".equals(operator) || "==".equals(operator)) { return compareResult == 0; }
        else { return false; }
    }

    public BigDecimal getThresholdValue() {

        return thresholdValue;
    }

    public void setThresholdValue(BigDecimal thresholdValue) {
        this.thresholdValue = thresholdValue;
    }

    public String getThresholdOperator() {

        return thresholdOperator;
    }

    public void setThresholdOperator(String thresholdOperator) {
        this.thresholdOperator = thresholdOperator;
    }

}
