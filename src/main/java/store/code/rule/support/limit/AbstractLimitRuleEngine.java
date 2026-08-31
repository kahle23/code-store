/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.rule.support.limit;

import store.code.rule.AbstractRuleEngine;
import store.code.rule.Rule;
import store.code.rule.RuleContext;
import store.code.rule.RuleResult;
import kunlun.util.StrUtil;

/**
 * 抽象限制规则引擎（规则引擎在"限制"场景的支持实现）.<br />
 * <p>
 * "限制"是规则引擎的一次使用：规则未命中时对业务动作
 * 产生拦截 / 放行 / 提示的效果（{@link Effect}）.
 * 本类在通用引擎之上补充限制域语义：
 * <ul>
 * <li>默认使用限制语义的单轮执行模型
 * （{@link LimitSinglePassExecutionModel}），按效果解释汇总结果；</li>
 * <li>参数未命中时不跳过，默认按规则效果（通常是拦截）产生结果；</li>
 * <li>提示语模板额外支持 "${usagedValue}" 与 "${thresholdValue}" 占位符.</li>
 * </ul>
 * @author Kahle
 */
public abstract class AbstractLimitRuleEngine extends AbstractRuleEngine {

    /**
     * 规则参数未命中时的默认提示语.
     */
    protected static final String DEFAULT_PARAM_NOT_MATCH_MESSAGE = "规则 [%s] 未配置参数，请联系管理员配置！";

    public AbstractLimitRuleEngine() {
        // 默认使用限制语义的单轮执行模型
        setExecutionModel(new LimitSinglePassExecutionModel());
    }

    @Override
    protected RuleResult handleParamNotMatch(Rule rule, RuleContext context) {
        LimitRuleResult result = new LimitRuleResult();
        result.setMatched(false);
        result.setEffect(effectOf(rule));
        String message = rule.getMessage();
        result.setMessage(StrUtil.isNotBlank(message) ? message
                : String.format(DEFAULT_PARAM_NOT_MATCH_MESSAGE, rule.getRuleCode()));
        return result;
    }

    @Override
    protected void fillDetail(Rule rule, RuleResult detail) {
        super.fillDetail(rule, detail);
        if (detail instanceof LimitRuleResult) {
            LimitRuleResult limitDetail = (LimitRuleResult) detail;
            if (limitDetail.getEffect() == null) {
                limitDetail.setEffect(effectOf(rule));
            }
        }
    }

    /**
     * 按结果格式化提示语模板，额外支持
     * "${usagedValue}" 与 "${thresholdValue}" 占位符.<br />
     * @param template 提示语模板
     * @param result 结果
     * @return 格式化后的提示语
     */
    @Override
    protected String formatMessage(String template, RuleResult result) {
        String message = super.formatMessage(template, result);
        if (result instanceof LimitRuleResult) {
            LimitRuleResult limitResult = (LimitRuleResult) result;
            message = message.replace("${usagedValue}", limitResult.getUsagedValue() == null
                    ? "" : limitResult.getUsagedValue().toPlainString());
            message = message.replace("${thresholdValue}", limitResult.getThresholdValue() == null
                    ? "" : limitResult.getThresholdValue().toPlainString());
        }
        return message;
    }

    /**
     * 取规则未命中时的效果（默认 BLOCK）.<br />
     */
    private Effect effectOf(Rule rule) {
        if (rule instanceof LimitRule) {
            Effect effect = ((LimitRule) rule).getEffect();
            if (effect != null) { return effect; }
        }
        return Effect.BLOCK;
    }

}
