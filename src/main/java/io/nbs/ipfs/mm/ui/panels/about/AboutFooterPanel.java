package io.nbs.ipfs.mm.ui.panels.about;

import io.nbs.ipfs.mm.Launcher;
import io.nbs.ipfs.mm.cnsts.ColorCnst;
import io.nbs.ipfs.mm.cnsts.DappCnsts;
import io.nbs.ipfs.mm.ui.panels.ParentAvailablePanel;
import io.nbs.ipfs.mm.util.FontUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * @Package : io.nbs.client.ui.panels.about
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/9-11:39
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class AboutFooterPanel extends ParentAvailablePanel {
    private static AboutFooterPanel context;
    private final String sitUrl;

    private final String SITE_HOME;

    private JLabel siteLabel;

    /**
     * construction
     */
    public AboutFooterPanel(JPanel parent) {
        super(parent);
        SITE_HOME = Launcher.LaucherConfMapUtil.getValue("nbs.site.home.label","nbsio.net");
        sitUrl = Launcher.LaucherConfMapUtil.getValue("nbs.site.home.url","http://nbsio.net/");
        initComponents();
        initView();
        setListeners();
    }

    /**
     * [initComponents description]
     *
     * @return {[type]} [description]
     */
    private void initComponents() {
        siteLabel = new JLabel(SITE_HOME);
        siteLabel.setToolTipText(sitUrl);
        siteLabel.setForeground(ColorCnst.FONT_ABOUT_TITLE_BLUE);
        siteLabel.setFont(FontUtil.getDefaultFont(18));
        //siteLabel.setOpaque(true);
        siteLabel.setBackground(ColorCnst.WINDOW_BACKGROUND_LIGHT);

    }

    /**
     * [initView description]
     *
     * @return {[type]} [description]
     */
    private void initView() {
        this.setLayout(new FlowLayout(FlowLayout.RIGHT,30,5));
        this.add(siteLabel);
    }

    private void setListeners() {
        siteLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                java.net.URI uri = java.net.URI.create(sitUrl);
                Desktop desktop = Desktop.getDesktop();
                if(desktop.isSupported(Desktop.Action.BROWSE)){
                    try {
                        desktop.browse(uri);
                    } catch (IOException e1) {
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                e.getComponent().setForeground(ColorCnst.FONT_URL_BLUE);
                e.getComponent().setCursor(DappCnsts.HAND_CURSOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                e.getComponent().setForeground(ColorCnst.FONT_ABOUT_TITLE_BLUE);
            }
        });
    }

    /**
     * [getContext description]
     *
     * @return {[type]} [description]
     */
    public static AboutFooterPanel getContext() {
        return context;
    }
}