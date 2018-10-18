package io.nbs.ipfs.mm.ui.components.messages;

import java.awt.*;

/**
 * @Package : com.nbs.ui.components.messages
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/27-3:34
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IMLeftImageMessageBubble extends IMAttachmentMessageBubble {

    public IMLeftImageMessageBubble() {
        NBS9PathImageIcon normal = new NBS9PathImageIcon(this.getClass().getResource("/icons/nbs128.png"));
        NBS9PathImageIcon active = new NBS9PathImageIcon(this.getClass().getResource("/icons/nbs128.png"));
        setBackgroundIcon(normal);
        setNormal(normal);
        setActive(active);
    }

    @Override
    public Insets getInsets() {
        return new Insets(2,9,3,2);
    }
}
