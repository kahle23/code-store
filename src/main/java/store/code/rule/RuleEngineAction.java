/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.rule;

import kunlun.action.ActionUtil;
import kunlun.core.Action;
import kunlun.util.StrUtil;

import static kunlun.util.Assert.notNull;

/**
 * 基于 Action 体系的规则引擎适配器.<br />
 * <p>
 * 两个体系的定位：
 * <ul>
 * <li>Bean 体系（BeanHolder / BeanManager）是无 Spring 场景下的
 * 轻量容器替身（服务定位器：名字 -&gt; 实例）；</li>
 * <li>Action 体系（ActionManager / ActionUtil）是统一门面
 * （命令分发器）. 功能不各自建门面，避免门面泛滥.</li>
 * </ul>
 * 两个体系并不重叠. 规则引擎（store.code.rule）是纯领域，
 * 不依赖其中任何一个；当规则引擎需要经统一门面调用时，
 * 由本适配器充当规则域与 Action 体系的混合体
 * （与 InvokeAction / HttpCallAction 同范式）.
 * <p>
 * 使用方式：
 * <pre>
 * // 注册规则引擎（在应用的配置中）：
 * RuleEngineAction.register(ruleEngineImpl);
 *
 * // 按显式 command 调用（strategy 即场景码）：
 * RuleResult result = ActionUtil.execute("rule-engine.purchase.demand.ready-precheck", context);
 *
 * // 或按 shortcut 调用（按入参类型 RuleContext 路由）：
 * RuleResult result = ActionUtil.execute(context);
 * </pre>
 * @author Kahle
 */
public class RuleEngineAction implements Action {

    /**
     * 规则引擎默认的 Action 名称.
     */
    public static final String ACTION_NAME = "rule-engine";

    private final RuleEngine ruleEngine;

    public RuleEngineAction(RuleEngine ruleEngine) {

        this.ruleEngine = notNull(ruleEngine, "Parameter \"ruleEngine\" must not null. ");
    }

    /**
     * 把规则引擎注册进 Action 体系（Action 名称为 "rule-engine"，
     * 同时注册入参类型 RuleContext 的 shortcut）.<br />
     * @param ruleEngine 规则引擎
     */
    public static void register(RuleEngine ruleEngine) {
        RuleEngineAction action = new RuleEngineAction(ruleEngine);
        ActionUtil.registerAction(ACTION_NAME, action);
        ActionUtil.registerShortcut(RuleContext.class, ACTION_NAME);
    }

    @Override
    public Object execute(String strategy, Object input, Object[] arguments) {
        RuleContext context = null;
        if (input instanceof RuleContext) { context = (RuleContext) input; }
        else if (arguments != null) {
            for (Object argument : arguments) {
                if (argument instanceof RuleContext) { context = (RuleContext) argument; break; }
            }
        }
        notNull(context, "The input of the rule engine action must be a RuleContext. ");
        // strategy 即场景码，为空时回退取规则上下文内的场景码
        String sceneCode = StrUtil.isNotBlank(strategy)
                ? strategy : context.getSceneCode();
        return ruleEngine.execute(sceneCode, context);
    }

}
