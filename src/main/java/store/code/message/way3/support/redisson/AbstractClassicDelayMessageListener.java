/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.message.way3.support.redisson;

import kunlun.message.MessageListener;
import kunlun.message.support.delay.DelayMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The abstract classic delay message listener.
 * @author Kahle
 */
public abstract class AbstractClassicDelayMessageListener implements MessageListener {
    private static final Logger log = LoggerFactory.getLogger(AbstractClassicDelayMessageListener.class);

    /**
     * Get the delay message listener's topic.
     * @return The delay message listener's topic
     */
    public abstract String getTopic();

    /**
     * Processing the received delayed message.
     * @param message The received delayed message
     */
    public abstract void process(kunlun.message.support.delay.DelayMessage message);

    @Override
    public Object onMessage(Object data) {
        try {
            process((DelayMessage) data);
        }
        catch (Exception e) {
            log.error("Processing the received message error! ", e);
        }
        return null;
    }

}
