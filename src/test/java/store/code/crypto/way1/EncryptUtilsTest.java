//
// 迁移注记（2026-08-12）：本测试引用的主类仍是整类注释空壳（旧版 artoria API）或
// 依赖在新版 artoria 中不存在，暂无法编译。整类注释保留源码。
//
// ----- 以下为原始测试代码（整类注释）-----
// /*
//  * Copyright (c) 2018. the original author or authors.
//  * Kunlun is licensed under the "LICENSE" file in the project's root directory.
//  */
// 
// package store.code.crypto.way1;
// 
// import kunlun.codec.CodecUtils;
// import kunlun.file.Text;
// import kunlun.logging.Logger;
// import kunlun.logging.LoggerFactory;
// import org.junit.Test;
// 
// import java.io.IOException;
// 
// import static kunlun.codec.CodecUtils.BASE64;
// 
// public class EncryptUtilsTest {
//     private static final Logger log = LoggerFactory.getLogger(EncryptUtilsTest.class);
//     private static final String data = "Hello, Java! ";
// 
//     @Test
//     public void test1() {
//         byte[] encrypt = EncryptUtils.encrypt(data.getBytes());
//         log.info("Encrypt base64: {}", CodecUtils.encodeToString(BASE64, encrypt));
//         byte[] decrypt = EncryptUtils.decrypt(encrypt);
//         log.info("Decrypt string: {}", decrypt != null ? new String(decrypt) : null);
//     }
// 
//     @Test
//     public void test2() throws IOException {
//         Text text = new Text();
//         text.readFromClasspath("logging.properties");
//         byte[] encrypt = EncryptUtils.encrypt(text.writeToByteArray());
//         log.info("Encrypt base64: {}", CodecUtils.encodeToString(BASE64, encrypt));
//         byte[] decrypt = EncryptUtils.decrypt(encrypt);
//         log.info("Decrypt string: {}", decrypt != null ? new String(decrypt) : null);
//     }
// 
//     @Test
//     public void test3() {
//         byte[] bytes = EncryptUtils.digest(data.getBytes());
//         log.info("Digest base64: {}", CodecUtils.encodeToString(BASE64, bytes));
//     }
// 
//     @Test
//     public void test4() {
//         byte[] bytes = EncryptUtils.digest256(data.getBytes());
//         log.info("Digest 256 base64: {}", CodecUtils.encodeToString(BASE64, bytes));
//     }
// 
//     @Test
//     public void test5() {
//         byte[] bytes = EncryptUtils.digest512(data.getBytes());
//         log.info("Digest 512 base64: {}", CodecUtils.encodeToString(BASE64, bytes));
//     }
// 
// }
// 
