/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.rule.support.limit;

import store.code.rule.Rule;

/**
 * 限制规则模型.<br />
 * <p>
 * 在通用规则之上补充限制域语义：
 * 规则未命中时对业务动作的效果（拦截 / 放行 / 提示）.
 * 提示语模板沿用 {@link Rule#getMessage()}.
 * @author Kahle
 */
public class LimitRule extends Rule {

    private Effect effect;

    public LimitRule() {
    }

    public LimitRule(String sceneCode, String ruleCode, String ruleName) {
        super(sceneCode, ruleCode, ruleName);
    }

    /**
     * 获取规则未命中时的效果（Null 视为 BLOCK）.<br />
     * @return 未命中时的效果
     */
    public Effect getEffect() {

        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

}
