//
// 迁移注记（2026-08-12）：本测试引用的依赖在新版 artoria 中不存在，或引用的主类仍是
// 整类注释空壳（旧版 artoria API），暂无法编译。整类注释保留源码。
//
// ----- 以下为原始测试代码（整类注释）-----
// /*
//  * Copyright (c) 2018. the original author or authors.
//  * Kunlun is licensed under the "LICENSE" file in the project's root directory.
//  */
// 
// package store.code.generator.code.java.way1;
// 
// import kunlun.renderer.support.VelocityTextRenderer;
// import org.junit.Ignore;
// import org.junit.Test;
// import store.code.jdbc.way1.DatabaseClient;
// import store.code.jdbc.way1.SimpleDataSource;
// 
// @Ignore
// public class JavaCodeGeneratorTest {
//     private static DatabaseClient databaseClient = new DatabaseClient(new SimpleDataSource());
// 
//     @Test
//     public void test1() {
//         JavaCodeGenerator generator = new JavaCodeGenerator().newCreator()
//                 .setDatabaseClient(databaseClient)
// //                .setBaseTemplatePath("classpath:templates/generator/java/custom")
//                 .setBaseOutputPath("src\\test\\java")
//                 .setBasePackageName("kunlun.generator.out")
//                 .setTextRenderer(new VelocityTextRenderer())
//                 .addRemovedTableNamePrefixes("t_")
// //                .addExcludedTables("t_15_user")
// //                .addReservedTables("t_user")
//                 ;
//         generator.addAttribute("author", "Kahle");
//         generator.generate();
//     }
// 
// }
// 
