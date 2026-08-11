//
// 迁移注记（2026-08-12）：本文件暂无法编译，原因：依赖 misaka.data.company.CompanyQuery（misaka 核心库基类），无法解析。
// 保留原始源码（整类注释），如需启用请先解决上述依赖再取消注释。
//
// ----- 以下为原始迁移代码（整类注释）-----
// package store.code.data.company.support.yonyou;
// 
// import artoria.action.ActionUtils;
// import artoria.util.Assert;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
// import org.springframework.boot.context.properties.EnableConfigurationProperties;
// import org.springframework.context.annotation.Configuration;
// 
// @Configuration
// @ConditionalOnProperty(name = "misaka.company.yonyou.enabled", havingValue = "true")
// @EnableConfigurationProperties({YonyouCompanyProperties.class})
// public class YonyouCompanyAutoConfiguration {
//     private static Logger log = LoggerFactory.getLogger(YonyouCompanyAutoConfiguration.class);
// 
//     @Autowired
//     public YonyouCompanyAutoConfiguration(YonyouCompanyProperties properties) {
//         Assert.notNull(properties, "Parameter \"properties\" must not null. ");
//         String baseInfoApiCode = properties.getBaseInfoApiCode();
//         String searchApiCode = properties.getSearchApiCode();
//         Integer timeout = properties.getTimeout();
//         YonyouCompanyActionHandler actionHandler =
//                 new YonyouCompanyActionHandler(baseInfoApiCode, searchApiCode, timeout);
//         ActionUtils.registerHandler("yonyou", actionHandler);
//     }
// 
// }
// 
