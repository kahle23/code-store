package store.code.demo.ua;

/*
 * 迁移注记（2026-08-09）：
 * 本文件源自 demo4j 的 demo/ua/UaParserDemo，依赖 ua_parser.*（com.github.ua_parser:uap-java）。
 * 该构件仅在 JCenter（已停运）发布，Maven Central 无对应版本，无法解析，故整类注释。
 * 若后续需要 User-Agent 解析能力，可改用同包下已迁移并可解析的依赖：
 *   - cz.mallat.uasparser:uasparser:0.6.2  （UserAgentDemo）
 *   - eu.bitwalker:UserAgentUtils:1.21      （UserAgentUtilsTest）
 *
import org.junit.Test;
import ua_parser.Client;
import ua_parser.Parser;

public class UaParserDemo {

    @Test
    public void test1() throws Exception {
        String uaString = "Mozilla/5.0 (iPhone; CPU iPhone OS 5_1_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9B206 Safari/7534.48.3";

        Parser uaParser = new Parser();
        Client client = uaParser.parse(uaString);

        System.out.println(client.userAgent.family); // => "Mobile Safari"
        System.out.println(client.userAgent.major);  // => "5"
        System.out.println(client.userAgent.minor);  // => "1"

        System.out.println(client.os.family);        // => "iOS"
        System.out.println(client.os.major);         // => "5"
        System.out.println(client.os.minor);         // => "1"

        System.out.println(client.device.family);    // => "iPhone"
    }

}
*/
