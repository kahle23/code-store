//
// 迁移注记（2026-08-12）：本测试引用的主类仍是整类注释空壳（旧版 artoria API）或
// 依赖在新版 artoria 中不存在，暂无法编译。整类注释保留源码。
//
// ----- 以下为原始测试代码（整类注释）-----
// package store.code.storage.way2;
// 
// import artoria.file.FileUtils;
// import artoria.logging.Logger;
// import artoria.logging.LoggerFactory;
// import artoria.util.CloseUtils;
// import com.alibaba.fastjson.JSON;
// import org.junit.Ignore;
// import org.junit.Test;
// 
// import java.io.File;
// import java.io.InputStream;
// import java.util.LinkedHashMap;
// import java.util.Map;
// 
// @Ignore
// public class StorageUtilsTest {
//     private static Logger log = LoggerFactory.getLogger(StorageUtilsTest.class);
//     private static String bucketName = "E:\\Test";
// 
//     @Test
//     public void test1() throws Exception {
//         File file = new File("E:\\test.png");
//         Map<String, Object> metadata = new LinkedHashMap<String, Object>();
//         metadata.put("test-metadata", "metadata");
//         metadata.put("file-type", "png");
//         StorageUtils.putObject(bucketName, "2020\\12\\31\\20190915163400.png", file, metadata);
//     }
// 
//     @Test
//     public void test2() throws Exception {
//         StorageObject storageObject = StorageUtils.getObject(bucketName, "2019\\09\\15\\01\\20190915163400.png");
//         Map<String, Object> metadata = storageObject.getMetadata();
//         log.info(JSON.toJSONString(metadata, true));
//         InputStream objectContent = storageObject.getObjectContent();
//         FileUtils.write(objectContent, new File("E:\\test_" + System.currentTimeMillis() + ".png"));
//         CloseUtils.closeQuietly(objectContent);
//     }
// 
//     @Test
//     public void test3() throws Exception {
//         ListObjectsResult listObjectsResult = StorageUtils.listObjects(bucketName, "");
//         log.info(JSON.toJSONString(listObjectsResult, true));
//     }
// 
// }
// 
