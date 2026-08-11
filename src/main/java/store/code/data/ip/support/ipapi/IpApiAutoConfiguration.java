//
// 迁移注记（2026-08-12）：本文件暂无法编译，原因：依赖 misaka.data.ip.IpQuery（misaka 核心库基类），无法解析。
// 保留原始源码（整类注释），如需启用请先解决上述依赖再取消注释。
//
// ----- 以下为原始迁移代码（整类注释）-----
// package store.code.data.ip.support.ipapi;
// 
// import artoria.action.ActionUtils;
// import store.code.location.way1.IpQuery;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.DisposableBean;
// import org.springframework.beans.factory.InitializingBean;
// import org.springframework.context.annotation.Configuration;
// 
// /**
//  * Network physical address auto configuration.
//  * @author Kahle
//  */
// @Configuration
// public class IpApiAutoConfiguration implements InitializingBean, DisposableBean {
//     private static Logger log = LoggerFactory.getLogger(IpApiAutoConfiguration.class);
// 
//     @Override
//     public void afterPropertiesSet() throws Exception {
//         IpApiIpActionHandler handler = new IpApiIpActionHandler();
//         String actionName = "ip-query-ipapi";
//         ActionUtils.registerHandler(actionName, handler);
//         ActionUtils.registerHandler(IpQuery.class, handler);
//     }
// 
//     @Override
//     public void destroy() throws Exception {
//     }
// 
// }
// 
