//
// 迁移注记（2026-08-12）：本测试引用的主类仍是整类注释空壳（旧版 artoria API）或
// 依赖在新版 artoria 中不存在，暂无法编译。整类注释保留源码。
//
// ----- 以下为原始测试代码（整类注释）-----
// package store.code.dict.way1;
// 
// import artoria.logging.Logger;
// import artoria.logging.LoggerFactory;
// import com.alibaba.fastjson.JSON;
// import org.junit.Test;
// 
// public class DictUtilsTest {
//     private static Logger log = LoggerFactory.getLogger(DictUtilsTest.class);
// 
//     @Test
//     public void test1() {
//         DictUtils.setValue("test1_key1", "test1_key1_val");
//         DictUtils.setValue("test1_key2", "test1_key2_val");
//         log.info(DictUtils.getStringValue("test1_key1"));
//         log.info(DictUtils.getStringValue("test1_key2"));
// //        log.info(JSON.toJSONString(DictUtils.findList(DEFAULT), true));
//         DictUtils.delete("test1_key1");
// //        log.info(JSON.toJSONString(DictUtils.findList(DEFAULT), true));
//         DictUtils.delete("test1_key2");
// //        log.info(JSON.toJSONString(DictUtils.findList(DEFAULT), true));
//     }
// 
//     @Test
//     public void test2() {
//         DictUtils.setValue("config", "auto_update", "false");
//         DictUtils.setValue("config", "auto_refresh", "true");
//         DictUtils.setValue("config", "refresh_interval", "10");
// //        log.info(JSON.toJSONString(DictUtils.findList("config"), true));
//         log.info(JSON.toJSONString(DictUtils.getProperties("config"), true));
//     }
// 
//     @Test
//     public void test3() {
//         DictUtils.setValue("zheng_jian", "身份证", "sheng_feng_zheng");
//         DictUtils.setValue("zheng_jian", "居住证", "ju_zhu_zheng");
//         DictUtils.setValue("zheng_jian", "驾驶证", "jia_shi_zheng");
// //        log.info(JSON.toJSONString(DictUtils.findList("zheng_jian"), true));
//         log.info(JSON.toJSONString(DictUtils.getProperties("zheng_jian"), true));
//     }
// 
// }
// 
