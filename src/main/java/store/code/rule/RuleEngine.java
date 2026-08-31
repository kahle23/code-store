/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.rule;

/**
 * 为规则引擎提供最高层次的抽象.<br />
 * <p>
 * 什么是规则引擎？
 * 把"在什么条件下、得出什么判定"的业务决策从代码中抽离为可配置的规则，
 * 运行时按输入的事实执行一个场景下的规则集：按优先级排序、
 * 为每条规则解析维度化参数（USER &gt; ORG &gt; GLOBAL 兜底）、
 * 路由到规则策略求值，再按组合方式（AND / OR）汇总，
 * 产出判定结果与输出（是否命中 + 任意输出）.
 * <p>
 * 规则引擎是通用的决策组件：校验、限制、计价、路由、预警、风控等
 * 都只是它的应用，引擎本身不预设任何业务语义.
 * @author Kahle
 */
public interface RuleEngine {

    /**
     * 执行场景下的规则.<br />
     * @param sceneCode 场景码（钩子点标识，
     *                  如 "purchase.demand.ready-precheck"）
     * @param context 规则上下文（维度与业务数据）
     * @return 总结果（附每条规则的明细）
     */
    RuleResult execute(String sceneCode, RuleContext context);

}
