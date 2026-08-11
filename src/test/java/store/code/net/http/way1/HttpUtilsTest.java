//
// 迁移注记（2026-08-12）：本测试引用的主类仍是整类注释空壳（旧版 artoria API）或
// 依赖在新版 artoria 中不存在，暂无法编译。整类注释保留源码。
//
// ----- 以下为原始测试代码（整类注释）-----
// package store.code.net.http.way1;
// 
// import artoria.logging.Logger;
// import artoria.logging.LoggerFactory;
// import org.junit.Ignore;
// import org.junit.Test;
// 
// @Ignore
// public class HttpUtilsTest {
//     private static Logger log = LoggerFactory.getLogger(HttpUtilsTest.class);
//     private String testUrl0 = "https://www.github.com";
//     private String testUrl1 = "https://www.bing.com";
// 
//     @Test
//     public void test1() {
//         log.info(HttpUtils.get(testUrl0));
//         log.info(HttpUtils.execute(testUrl0, HttpMethod.GET));
//     }
// 
//     @Test
//     public void test2() {
//         log.info(HttpUtils.get(testUrl1));
//         log.info(HttpUtils.execute(testUrl1, HttpMethod.GET));
//     }
// 
// }
// 
