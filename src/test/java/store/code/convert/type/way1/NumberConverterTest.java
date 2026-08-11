//
// 迁移注记（2026-08-12）：本测试引用的主类仍是整类注释空壳（旧版 artoria API）或
// 依赖在新版 artoria 中不存在，暂无法编译。整类注释保留源码。
//
// ----- 以下为原始测试代码（整类注释）-----
// package store.code.convert.type.way1;
// 
// import artoria.logging.Logger;
// import artoria.logging.LoggerFactory;
// import org.junit.Test;
// 
// import java.math.BigDecimal;
// import java.math.BigInteger;
// 
// public class NumberConverterTest {
//     private static Logger log = LoggerFactory.getLogger(NumberConverterTest.class);
//     private TypeConverter converter = new NumberConverter();
// 
//     @Test
//     public void testIntToInteger() {
//         int src = 100;
//         Object obj = converter.convert(src, Integer.class);
//         log.info("{} {}", obj.getClass(), obj);
//     }
// 
//     @Test
//     public void testIntToBigDecimal() {
//         int src = 100;
//         Object obj = converter.convert(src, BigDecimal.class);
//         log.info("{} {}", obj.getClass(), obj);
//     }
// 
//     @Test
//     public void testIntToBigInteger() {
//         int src = 100;
//         Object obj = converter.convert(src, BigInteger.class);
//         log.info("{} {}", obj.getClass(), obj);
//     }
// 
//     @Test
//     public void testIntToPrimitiveDouble() {
//         int src = 100;
//         Object obj = converter.convert(src, double.class);
//         log.info("{} {}", obj.getClass(), obj);
//     }
// 
// }
// 
