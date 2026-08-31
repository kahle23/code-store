/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.rule.condition;

import kunlun.util.StrUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 布尔组合表达式的解析器（递归下降）.<br />
 * <p>
 * 表达式由规则编码与运算符组成，如
 * "(RULE-A &amp;&amp; RULE-B) || !RULE-C".
 * 支持的语法：
 * <ul>
 * <li>叶子：规则编码（字母、数字与 "_ . $ -" 组成，如 READY-STOCK-VALUE）；</li>
 * <li>运算符："&amp;&amp;"（AND）、"||"（OR）、"!"（NOT）；</li>
 * <li>括号分组，NOT 优先级最高，AND 次之，OR 最低；</li>
 * <li>多余空白忽略.</li>
 * </ul>
 * 解析结果为条件树（AST），求值见 {@link Condition#evaluate(java.util.Map)}.
 * @author Kahle
 */
public class ConditionParser {

    private final List<String> tokens;
    private int position;

    private ConditionParser(List<String> tokens) {
        this.tokens = tokens;
        this.position = 0;
    }

    /**
     * 解析布尔组合表达式为条件树.<br />
     * @param expression 布尔组合表达式
     * @return 条件树
     */
    public static Condition parse(String expression) {
        if (StrUtil.isBlank(expression)) {
            throw new IllegalArgumentException(
                    "Parameter \"expression\" must not blank. ");
        }
        List<String> tokens = tokenize(expression);
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException(
                    "The expression has no valid token: " + expression);
        }
        ConditionParser parser = new ConditionParser(tokens);
        Condition condition = parser.parseOr();
        if (parser.position < tokens.size()) {
            throw new IllegalArgumentException("Unexpected token \""
                    + parser.tokens.get(parser.position) + "\" in expression: " + expression);
        }
        return condition;
    }

    /**
     * 分词：规则编码 / "&amp;&amp;" / "||" / "!" / "(" / ")"，忽略空白.<br />
     */
    private static List<String> tokenize(String expression) {
        List<String> tokens = new ArrayList<String>();
        int length = expression.length();
        int index = 0;
        while (index < length) {
            char ch = expression.charAt(index);
            if (Character.isWhitespace(ch)) { index++; continue; }
            if (ch == '(' || ch == ')' || ch == '!') {
                tokens.add(String.valueOf(ch));
                index++;
            }
            else if (ch == '&' || ch == '|') {
                if (index + 1 < length && expression.charAt(index + 1) == ch) {
                    tokens.add(ch == '&' ? "&&" : "||");
                    index += 2;
                }
                else {
                    throw new IllegalArgumentException("Invalid operator \""
                            + ch + "\" in expression: " + expression + ". Use \"&&\" or \"||\". ");
                }
            }
            else if (isIdentifierChar(ch)) {
                StringBuilder token = new StringBuilder();
                while (index < length && isIdentifierChar(expression.charAt(index))) {
                    token.append(expression.charAt(index));
                    index++;
                }
                tokens.add(token.toString());
            }
            else {
                throw new IllegalArgumentException("Invalid character \""
                        + ch + "\" in expression: " + expression);
            }
        }
        return tokens;
    }

    private static boolean isIdentifierChar(char ch) {

        return Character.isLetterOrDigit(ch) || ch == '_' || ch == '.' || ch == '$' || ch == '-';
    }

    /**
     * 解析 OR 层（最低优先级）：expr := term ("||" term)*.<br />
     */
    private Condition parseOr() {
        Condition left = parseAnd();
        while (position < tokens.size() && "||".equals(tokens.get(position))) {
            position++;
            Condition right = parseAnd();
            // 同层扁平化："(A || B) || C" 合并为一个三元的 OR 节点
            if (left instanceof OrCondition) { ((OrCondition) left).add(right); }
            else {
                OrCondition or = new OrCondition(new ArrayList<Condition>());
                or.add(left);
                or.add(right);
                left = or;
            }
        }
        return left;
    }

    /**
     * 解析 AND 层：term := unary ("&&" unary)*.<br />
     */
    private Condition parseAnd() {
        Condition left = parseUnary();
        while (position < tokens.size() && "&&".equals(tokens.get(position))) {
            position++;
            Condition right = parseUnary();
            if (left instanceof AndCondition) { ((AndCondition) left).add(right); }
            else {
                AndCondition and = new AndCondition(new ArrayList<Condition>());
                and.add(left);
                and.add(right);
                left = and;
            }
        }
        return left;
    }

    /**
     * 解析一元层（最高优先级）：unary := "!" unary | "(" expr ")" | 规则编码.<br />
     */
    private Condition parseUnary() {
        if (position >= tokens.size()) {
            throw new IllegalArgumentException("Unexpected end of expression. ");
        }
        String token = tokens.get(position);
        if ("!".equals(token)) {
            position++;
            return new NotCondition(parseUnary());
        }
        if ("(".equals(token)) {
            position++;
            Condition condition = parseOr();
            expect(")");
            return condition;
        }
        if (")".equals(token) || "&&".equals(token) || "||".equals(token)) {
            throw new IllegalArgumentException("Unexpected token \"" + token + "\". ");
        }
        position++;
        return new RuleRefCondition(token);
    }

    private void expect(String token) {
        if (position >= tokens.size() || !token.equals(tokens.get(position))) {
            throw new IllegalArgumentException("Expected \"" + token + "\" but not found. ");
        }
        position++;
    }

}
