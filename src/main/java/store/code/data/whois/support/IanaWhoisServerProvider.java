//
// 迁移注记（2026-08-12）：本文件暂无法编译，原因：依赖 misaka.data.whois.WhoisServerProvider（misaka 核心库基类），无法解析。
// 保留原始源码（整类注释），如需启用请先解决上述依赖再取消注释。
//
// ----- 以下为原始迁移代码（整类注释）-----
// package store.code.data.whois.support;
// 
// import artoria.exception.ExceptionUtils;
// import artoria.net.*;
// import store.code.data.whois.WhoisServer;
// import store.code.data.whois.WhoisServerProvider;
// 
// public class IanaWhoisServerProvider implements WhoisServerProvider {
//     // https://www.iana.org/whois
// 
//     @Override
//     public WhoisServer findByDomainName(String domainName) {
//         try {
//             HttpRequest httpRequest = new HttpRequest();
//             String accessAddress = "https://www.iana.org/whois?q=" + domainName;
//             httpRequest.setUrl(accessAddress);
//             httpRequest.setMethod(HttpMethod.GET);
//             httpRequest.addHeader("Accept-Language", "zh-CN,zh;q=0.9");
//             httpRequest.addHeader("Host", "www.iana.org");
//             httpRequest.addHeader("Referer", accessAddress);
//             HttpClient httpClient = HttpUtils.getHttpClient();
//             HttpResponse httpResponse = httpClient.execute(httpRequest);
//             return null;
//         }
//         catch (Exception e) {
//             throw ExceptionUtils.wrap(e);
//         }
//     }
// 
// }
// 
