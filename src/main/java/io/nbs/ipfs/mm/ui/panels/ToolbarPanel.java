package io.nbs.ipfs.mm.ui.panels;

import io.nbs.ipfs.mm.ui.components.VerticalFlowLayout;

import javax.swing.*;
import java.awt.*;

/**
 * Copyright © 2015-2020 NBSChain Holdings Limited.
 * All rights reserved.
 *
 * @project ipfs-mm
 * <p>
 * Author   : lanbery
 * Created  : 2018/10/15
 */
public class ToolbarPanel extends JPanel {

    private static ToolbarPanel context;

    private JPanel upButtonPanel;
    private JPanel bottomPanel;

    /**
     * 头像
     */
    private JLabel avatarLabel;

    /* ToolbarPanel comments :  构造函数*/
    public ToolbarPanel() {
        initComponents();
        initView();
        setListeners();
    }

    private void initComponents(){
        upButtonPanel = new JPanel();
        upButtonPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP,0,30,false,false));
    }

    private void initView(){

    }

    private void setListeners(){

    }

    public static ToolbarPanel getContext() {
        return context;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(!isOpaque())return;
        int width       = getWidth();
        int height      = getHeight();
        Graphics2D g2   = (Graphics2D) g;
        GradientPaint gradientPaint = new GradientPaint(0,0,new Color(37,32,72),width,height,new Color(59,54,98));
        g2.setPaint(gradientPaint);
        g2.fillRect(0,0,width,height);
    }
}
