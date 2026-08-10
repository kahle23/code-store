/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.ai.way1.support;

/*
 * 迁移注记：依赖 kunlun.ai.AbstractAiHandler 大小写差异可修复，但 kunlun.core.handler.StrategySupportedHandler
 * 在当前 kunlun 版本 (1.0.0.20240217.beta) 中不存在，且该文件无其他引用，已整体注释。
 */
// import kunlun.ai.AbstractAiHandler;
// import kunlun.core.handler.StrategySupportedHandler;
// import kunlun.data.tuple.Pair;
// import kunlun.util.ArgumentUtils;
// import artoria.util.Assert;
//
// public abstract class AbstractStrategyAIHandler extends AbstractAiHandler implements StrategySupportedHandler {
//
//     @Override
//     public Object execute(Object[] arguments) {
//         Pair<Object, Object[]> pair = ArgumentUtils.parseToObjArr(arguments);
//         return execute(pair.getLeft(), pair.getRight());
//     }
//
//     @Override
//     public Object execute(Object strategy, Object[] arguments) {
//         Assert.notNull(strategy, "Parameter \"strategy\" must not null. ");
//         Pair<Object, Class<?>> pair = ArgumentUtils.parseToObjCls(arguments);
//         return execute(pair.getLeft(), String.valueOf(strategy), pair.getRight());
//     }
//
//     public abstract Object execute(Object input, String strategy, Class<?> clazz);
//
// }
