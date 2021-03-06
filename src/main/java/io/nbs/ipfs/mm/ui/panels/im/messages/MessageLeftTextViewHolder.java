package io.nbs.ipfs.mm.ui.panels.im.messages;

import io.nbs.ipfs.mm.cnsts.ColorCnst;
import io.nbs.ipfs.mm.ui.components.GBC;
import io.nbs.ipfs.mm.ui.components.SizeAutoAdjustTextArea;
import io.nbs.ipfs.mm.ui.components.VerticalFlowLayout;
import io.nbs.ipfs.mm.ui.components.messages.IMLeftImageMessageBubble;
import io.nbs.ipfs.mm.ui.frames.MainFrame;
import io.nbs.ipfs.mm.ui.holder.BaseMessageViewHolder;
import io.nbs.ipfs.mm.util.FontUtil;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : com.nbs.ui.holder
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/27-3:32
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class MessageLeftTextViewHolder extends BaseMessageViewHolder {
    public JLabel sender = new JLabel();
    public SizeAutoAdjustTextArea text;
    public IMLeftImageMessageBubble messageBubble = new IMLeftImageMessageBubble();

    private JPanel timePanel = new JPanel();
    private JPanel messageAvatarPanel = new JPanel();

    public MessageLeftTextViewHolder() {
        initComponents();
        initView();
    }

    private void initComponents()
    {
        int maxWidth = (int) (MainFrame.getContext().currentWindowWidth * 0.385);
        text = new SizeAutoAdjustTextArea(maxWidth);
        text.setParseUrl(true);

        time.setForeground(ColorCnst.FONT_GRAY);
        time.setFont(FontUtil.getDefaultFont(12));

        sender.setFont(FontUtil.getDefaultFont(12));
        sender.setForeground(ColorCnst.FONT_GRAY);

        messageAvatarPanel.setBackground(ColorCnst.WINDOW_BACKGROUND);
        timePanel.setBackground(ColorCnst.WINDOW_BACKGROUND);
    }

    private void initView()
    {
        setLayout(new BorderLayout());
        timePanel.add(time);

        messageBubble.add(text);

        JPanel senderMessagePanel = new JPanel();
        senderMessagePanel.setBackground(ColorCnst.WINDOW_BACKGROUND);
        senderMessagePanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP, 0,0,true, false));
        senderMessagePanel.add(sender);
        senderMessagePanel.add(messageBubble);

        messageAvatarPanel.setLayout(new GridBagLayout());
        messageAvatarPanel.add(avatar, new GBC(1, 0).setWeight(1, 1).setAnchor(GBC.NORTH).setInsets(4,5,0,0));
        messageAvatarPanel.add(senderMessagePanel, new GBC(2, 0)
                .setWeight(1000, 1)
                .setAnchor(GBC.WEST)
                .setInsets(0,5,5,0));

        add(timePanel, BorderLayout.NORTH);
        add(messageAvatarPanel, BorderLayout.CENTER);
    }
}
