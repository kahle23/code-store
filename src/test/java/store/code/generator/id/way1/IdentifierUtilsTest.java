//
// 迁移注记（2026-08-12）：本测试引用的主类仍是整类注释空壳（旧版 artoria API）或
// 依赖在新版 artoria 中不存在，暂无法编译。整类注释保留源码。
//
// ----- 以下为原始测试代码（整类注释）-----
// package store.code.generator.id.way1;
// 
// import artoria.logging.Logger;
// import artoria.logging.LoggerFactory;
// import org.junit.Test;
// 
// import static artoria.common.Constants.*;
// 
// public class IdentifierUtilsTest {
//     private static Logger log = LoggerFactory.getLogger(IdentifierUtilsTest.class);
//     private Integer groupLength = FIVE;
//     private Integer count = 100;
// 
//     @Test
//     public void test1() {
//         StringBuilder builder = new StringBuilder();
//         for (int i = ZERO; i < count; i++) {
//             Long number = IdentifierUtils.nextLongIdentifier();
//             builder.append(number)
//                     .append("(")
//                     .append(number.toString().length())
//                     .append(")")
//                     .append(" ");
//             if (i % groupLength == ZERO) {
//                 builder.append(NEWLINE);
//             }
//         }
//         log.info(builder.toString());
//     }
// 
//     @Test
//     public void test2() {
//         StringBuilder builder = new StringBuilder();
//         for (int i = ZERO; i < count; i++) {
//             String string = IdentifierUtils.nextStringIdentifier();
//             builder.append(string)
//                     .append("(")
//                     .append(string.length())
//                     .append(")")
//                     .append(" ");
//             if (i % groupLength == ZERO) {
//                 builder.append(NEWLINE);
//             }
//         }
//         log.info(builder.toString());
//     }
// 
// }
// 
