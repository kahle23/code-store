//
// 迁移注记（2026-08-12）：本测试引用的依赖在新版 artoria 中不存在，或引用的主类仍是
// 整类注释空壳（旧版 artoria API），暂无法编译。整类注释保留源码。
//
// ----- 以下为原始测试代码（整类注释）-----
// package store.code.storage.way2;
// 
// import artoria.logging.Logger;
// import artoria.logging.LoggerFactory;
// import org.junit.Ignore;
// import org.junit.Test;
// 
// @Ignore
// public class LocalFileStorageProviderTest {
//     private static Logger log = LoggerFactory.getLogger(LocalFileStorageProviderTest.class);
//     private static StorageProvider storageProvider = new LocalFileStorageProvider();
//     private static String bucketName = "E:\\Test";
// 
//     @Test
//     public void test1() throws Exception {
// //        File file = new File("E:\\test.png");
// //        Map<String, Object> metadata = new LinkedHashMap<String, Object>();
// //        metadata.put("test.metadata", "metadata");
// //        metadata.put("file.type", "png");
// //        storageProvider.putObject(bucketName, "2019\\09\\15\\01\\20190915163400.png", file, metadata);
//     }
// 
//     @Test
//     public void test2() throws Exception {
// //        StorageObject storageObject = storageProvider.getObject(bucketName, "2019\\09\\15\\01\\20190915163400.png");
// //        Map<String, Object> metadata = storageObject.getMetadata();
// //        System.out.println(metadata);
// //        InputStream objectContent = storageObject.getObjectContent();
// //        FileUtils.write(objectContent, new File("E:\\test_" + System.currentTimeMillis() + ".png"));
// //        CloseUtils.closeQuietly(objectContent);
//     }
// 
// }
// 
