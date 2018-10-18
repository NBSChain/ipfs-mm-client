package io.nbs.ipfs.biz.listeners;

import net.nbsio.ipfs.beans.MessageItem;
import net.nbsio.ipfs.beans.OnlineMessage;

/**
 * @Package : io.nbs.client.listener
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/3-22:24
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public interface IPFSSubscribeListener {

    /**
     *
     * @param item
     */
    void notifyRecvMessage(MessageItem item);

    /**
     *
     * @param message
     */
    void notifyOnlineMessage(OnlineMessage message);
}
