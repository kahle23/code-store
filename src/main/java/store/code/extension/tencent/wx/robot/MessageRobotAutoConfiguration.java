package store.code.extension.tencent.wx.robot;

import artoria.bot.MessageBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Work WeChat configuration. 迁移自 misaka-extend 的 misaka.tencent.work.wx.robot.MessageRobotAutoConfiguration
 * （配置属性前缀由 misaka. 迁移为 store.code.）。
 * @author Kahle
 */
@Configuration
@ConditionalOnProperty(name = "store.code.tencent.wx-work.robot.enabled", havingValue = "true")
public class MessageRobotAutoConfiguration {
    private static Logger log = LoggerFactory.getLogger(MessageRobotAutoConfiguration.class);

    @Value("${store.code.tencent.wx-work.robot.key}")
    private String key;

    @Bean
    public MessageBot wxWorkMessageRobot() {
        MessageBot messageBot = new WxWorkMessageRobot(key);
        log.info("Wx-Work robot init success. ");
        return messageBot;
    }

}