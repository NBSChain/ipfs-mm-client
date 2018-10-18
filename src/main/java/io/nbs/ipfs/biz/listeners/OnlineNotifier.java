package io.nbs.ipfs.biz.listeners;

import net.nbsio.ipfs.beans.OnlineMessage;
import net.nbsio.ipfs.beans.SystemCtrlMessageBean;

/**
 * @Package : io.nbs.client.listener
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/4-2:22
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public interface OnlineNotifier {
    /**
     *
     * @param messageBean
     */
    void notifyRecvSystemMessage(SystemCtrlMessageBean messageBean);

    void notifyRecvystemMessage(SystemCtrlMessageBean<OnlineMessage> ctrlMessageBean);
}
