/*
 * Copyright (c) 2018. the original author or authors.
 * Kunlun is licensed under the "LICENSE" file in the project's root directory.
 */

package store.code.message.way3;

import kunlun.core.Listener;

/**
 * The message listener.
 * @author Kahle
 */
@Deprecated
public interface MessageListener extends Listener {

    /**
     * Processing received messages.
     * @param data The received data
     * @return The necessary return value or null
     */
    Object onMessage(Object data);

}
