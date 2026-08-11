//
// 迁移注记（2026-08-12）：本测试引用的主类仍是整类注释空壳（旧版 artoria API）或
// 依赖在新版 artoria 中不存在，暂无法编译。整类注释保留源码。
//
// ----- 以下为原始测试代码（整类注释）-----
// package store.code.dict.way2;
// 
// import artoria.logging.Logger;
// import artoria.logging.LoggerFactory;
// import org.junit.Test;
// 
// public class PropertyUtilsTest {
//     private static Logger log = LoggerFactory.getLogger(PropertyUtilsTest.class);
// 
//     @Test
//     public void test1() {
//         PropertyUtils.setProperty("default_time", "1000");
//         PropertyUtils.setProperty("default_switch", false);
//         log.info("default_time: {}", PropertyUtils.getIntegerProperty("default_time"));
//         log.info("default_time1: {}", PropertyUtils.getIntegerProperty("default_time1"));
//         log.info("default_switch: {}", PropertyUtils.getBooleanProperty("default_switch"));
//     }
// 
// }
// 
