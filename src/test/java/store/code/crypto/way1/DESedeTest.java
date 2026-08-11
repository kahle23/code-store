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
// import kunlun.exception.ExceptionUtils;
// import kunlun.logging.Logger;
// import kunlun.logging.LoggerFactory;
// import org.junit.Test;
// 
// import javax.crypto.SecretKey;
// import javax.crypto.spec.IvParameterSpec;
// import java.security.GeneralSecurityException;
// 
// import static kunlun.codec.CodecUtils.BASE64;
// import static kunlun.common.constant.Algorithms.DESEDE;
// 
// /**
//  * DESede/ECB/ZeroPadding(NoPadding)
//  * DESede/ECB/PKCS5Padding
//  * DESede/CBC/ZeroPadding(NoPadding)
//  * DESede/CBC/PKCS5Padding
//  */
// public class DESedeTest extends BouncyCastleSupport {
//     private static Logger log = LoggerFactory.getLogger(DESedeTest.class);
//     private static SymmetricCrypto symmetricCrypto = new SimpleSymmetricCrypto();
//     private static IvParameterSpec ivParameterSpec;
//     private byte[] data = "Hello, Java!".getBytes();
// 
//     static {
//         try {
//             // Wrong keySize: must be equal to 112 or 168
//             String algorithm = DESEDE; int keySize = 168;
//             SecretKey secretKey = KeyUtils.generateKey(algorithm, keySize);
//             log.info("Algorithm = {}, KeySize = {}", algorithm, keySize);
//             log.info("Secret key: {}", KeyUtils.toBase64String(secretKey));
//             // Wrong IV length: must be 8 bytes long
//             ivParameterSpec = new IvParameterSpec("TeTestIv".getBytes());
//             symmetricCrypto.setSecretKey(secretKey);
//             symmetricCrypto.setAlgorithm(algorithm);
//             symmetricCrypto.setMode(Mode.ECB);
//             symmetricCrypto.setPadding(Padding.NO_PADDING);
//         }
//         catch (GeneralSecurityException e) {
//             throw ExceptionUtils.wrap(e);
//         }
//     }
// 
//     private void testEncryptAndDecrypt(Mode mode, Padding padding) throws Exception {
//         log.info("Start test DESede/{}/{}", mode, padding);
//         symmetricCrypto.setMode(mode);
//         symmetricCrypto.setPadding(padding);
//         byte[] bytes = symmetricCrypto.encrypt(data);
//         log.info("Encrypt: {}", CodecUtils.encodeToString(BASE64, bytes));
//         byte[] bytes1 = symmetricCrypto.decrypt(bytes);
//         log.info("Decrypt: {}", new String(bytes1));
//         log.info("End test DESede/{}/{}", mode, padding);
//     }
// 
//     @Test
//     public void testEcbZeroPadding() throws Exception {
// 
//         this.testEncryptAndDecrypt(Mode.ECB, Padding.ZERO_PADDING);
//     }
// 
//     @Test
//     public void testEcbPKCS5Padding() throws Exception {
// 
//         this.testEncryptAndDecrypt(Mode.ECB, Padding.PKCS5_PADDING);
//     }
// 
//     @Test
//     public void testCbcZeroPadding() throws Exception {
//         symmetricCrypto.setAlgorithmParameterSpec(ivParameterSpec);
//         this.testEncryptAndDecrypt(Mode.CBC, Padding.ZERO_PADDING);
//         symmetricCrypto.setAlgorithmParameterSpec(null);
//     }
// 
//     @Test
//     public void testCbcPKCS5Padding() throws Exception {
//         symmetricCrypto.setAlgorithmParameterSpec(ivParameterSpec);
//         this.testEncryptAndDecrypt(Mode.CBC, Padding.PKCS5_PADDING);
//         symmetricCrypto.setAlgorithmParameterSpec(null);
//     }
// 
// }
// 
