//
// 迁移注记（2026-08-12）：本测试引用的主类仍是整类注释空壳（旧版 artoria API）或
// 依赖在新版 artoria 中不存在，暂无法编译。整类注释保留源码。
//
// ----- 以下为原始测试代码（整类注释）-----
// package store.code.convert;
// 
// import org.junit.Test;
// 
// public class CarryDigitConverterTest {
//     private static char[] thirtySix1 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
//     private static char[] thirtySix = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
//     private static char[] hex1 = "0123456789ABCDEF".toCharArray();
//     private static char[] hex = "0123456789abcdef".toCharArray();
//     private static char[] decimal = "0123456789".toCharArray();
// 
//     @Test
//     public void test1() {
//         System.out.println(new CarryDigitConverter(thirtySix, decimal).convert("iu93wa"));
//         System.out.println(new CarryDigitConverter(decimal, thirtySix).convert("1314520"));
//         System.out.println(new CarryDigitConverter(decimal, hex).convert("1314520"));
//     }
// 
// }
// 
