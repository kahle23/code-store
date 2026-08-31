/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.rule.condition;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * 布尔组合表达式解析器测试.<br />
 * @author Kahle
 */
public class ConditionParserTest {

    private Map<String, Boolean> results(String... pairs) {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        for (int i = 0; i < pairs.length; i += 2) {
            map.put(pairs[i], Boolean.valueOf(pairs[i + 1]));
        }
        return map;
    }

    @Test
    public void testSingleRuleRef() {
        Condition condition = ConditionParser.parse("READY-STOCK-VALUE");
        assertTrue(condition.evaluate(results("READY-STOCK-VALUE", "true")));
        assertFalse(condition.evaluate(results("READY-STOCK-VALUE", "false")));
        // 结果表中不存在的规则按未命中处理
        assertFalse(condition.evaluate(results("OTHER-RULE", "true")));
    }

    @Test
    public void testAndOr() {
        Condition condition = ConditionParser.parse("A && B || C");
        assertTrue(condition.evaluate(results("A", "true", "B", "true")));
        assertTrue(condition.evaluate(results("C", "true")));
        assertFalse(condition.evaluate(results("A", "true", "B", "false")));
    }

    @Test
    public void testPrecedence() {
        // "A || B && C" 等价于 "A || (B && C)"
        Condition condition = ConditionParser.parse("A || B && C");
        assertFalse(condition.evaluate(results("B", "true", "C", "false")));
        assertTrue(condition.evaluate(results("A", "true")));
    }

    @Test
    public void testParentheses() {
        // "(A || B) && C"：括号改变优先级
        Condition condition = ConditionParser.parse("(A || B) && C");
        assertTrue(condition.evaluate(results("B", "true", "C", "true")));
        assertFalse(condition.evaluate(results("A", "true", "B", "false", "C", "false")));
    }

    @Test
    public void testNot() {
        Condition condition = ConditionParser.parse("!A");
        assertTrue(condition.evaluate(results("A", "false")));
        assertFalse(condition.evaluate(results("A", "true")));
    }

    @Test
    public void testNested() {
        Condition condition = ConditionParser.parse("(A && B) || !(C && D)");
        assertTrue(condition.evaluate(results("A", "true", "B", "true")));
        assertTrue(condition.evaluate(results("C", "false")));
        assertFalse(condition.evaluate(results("A", "false", "C", "true", "D", "true")));
    }

    @Test
    public void testToString() {
        Condition condition = ConditionParser.parse("(A && B) || C");
        assertEquals("((A && B) || C)", condition.toString());
    }

    @Test
    public void testWhitespaceIgnored() {
        Condition condition = ConditionParser.parse("  A\n&&\tB ");
        assertTrue(condition.evaluate(results("A", "true", "B", "true")));
        assertFalse(condition.evaluate(results("A", "true", "B", "false")));
    }

    @Test
    public void testIllegalExpression() {
        String[] illegalExpressions = {
                null, "", "   ",
                "A &&",             // 表达式突然结束
                "(A",               // 缺右括号
                "A)",               // 多余右括号
                "A B",              // 两个叶子之间无运算符
                "A & B",            // 单个 & 不合法
                "A | B",            // 单个 | 不合法
                "A && || B",        // 运算符连续
        };
        for (String expression : illegalExpressions) {
            try {
                ConditionParser.parse(expression);
                fail("Expected exception for expression: " + expression);
            }
            catch (IllegalArgumentException e) {
                // 预期抛出非法参数异常
            }
        }
    }

}
