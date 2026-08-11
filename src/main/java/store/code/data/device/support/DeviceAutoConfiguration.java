//
// 迁移注记（2026-08-12）：本文件暂无法编译，原因：依赖 misaka.data.device.Device/DeviceQuery（misaka 核心库基类），无法解析。
// 保留原始源码（整类注释），如需启用请先解决上述依赖再取消注释。
//
// ----- 以下为原始迁移代码（整类注释）-----
// package store.code.data.device.support;
// 
// import artoria.action.ActionUtils;
// import artoria.crypto.EncryptUtils;
// import artoria.file.Csv;
// import artoria.io.IOUtils;
// import artoria.util.ClassLoaderUtils;
// import store.code.data.device.DeviceQuery;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.DisposableBean;
// import org.springframework.beans.factory.InitializingBean;
// import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
// import org.springframework.context.annotation.Configuration;
// 
// import java.io.InputStream;
// 
// import static artoria.common.Constants.DEFAULT_ENCODING_NAME;
// 
// @Configuration
// @ConditionalOnProperty(name = "misaka.device.enabled", havingValue = "true")
// public class DeviceAutoConfiguration implements InitializingBean, DisposableBean {
//     private static Logger log = LoggerFactory.getLogger(DeviceAutoConfiguration.class);
// 
//     @Override
//     public void afterPropertiesSet() throws Exception {
//         Class<?> callingClass = DeviceAutoConfiguration.class;
//         String resourceName = "device_info.data";
//         InputStream inputStream =
//                 ClassLoaderUtils.getResourceAsStream(resourceName, callingClass);
//         byte[] byteArray = IOUtils.toByteArray(inputStream);
//         byte[] decrypt = EncryptUtils.decrypt(byteArray);
//         Csv csv = new Csv();
//         csv.setCharset(DEFAULT_ENCODING_NAME);
//         csv.readFromByteArray(decrypt);
//         ActionUtils.registerHandler(DeviceQuery.class, new FileBasedDeviceActionHandler(csv));
// //        DeviceUtils.setDeviceProvider();
//     }
// 
//     @Override
//     public void destroy() throws Exception {
//     }
// 
// }
// 
