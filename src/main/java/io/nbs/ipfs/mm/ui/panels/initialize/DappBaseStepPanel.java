package io.nbs.ipfs.mm.ui.panels.initialize;

import io.nbs.ipfs.mm.Launcher;
import io.nbs.ipfs.mm.cnsts.ColorCnst;
import io.nbs.ipfs.mm.ui.components.GBC;
import io.nbs.ipfs.mm.ui.components.NBSButton;
import io.nbs.ipfs.mm.ui.components.VerticalFlowLayout;
import io.nbs.ipfs.mm.ui.frames.InitialDappFrame;
import io.nbs.ipfs.mm.util.FontUtil;
import io.nbs.ipfs.mm.util.IconUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Copyright © 2015-2020 NBSChain Holdings Limited.
 * All rights reserved.
 *
 * @project ipfs-mm
 * <p>
 * Author   : lanbery
 * Created  : 2018/10/16
 */
public class DappBaseStepPanel extends JPanel {

    private JPanel          editPanel;
    private JTextArea       peerIdText;
    private JTextField      nickField;

    private JPanel          buttonPanel;
    private NBSButton       prevButton;
    private NBSButton       saveButton;
    private NBSButton       cancelButton;

    private NBSButton avatarButton;


    private JLabel statusLabel;
    private JPanel statusPanel;
    private JLabel avatarLabel;



    public DappBaseStepPanel(){
        initComponents();
        initView();
        setListeners();
    }

    /**
     * @author      : lanbery
     * @Datetime    : 2018/10/17
     * @Description  :
     * 组件初始化
     */
    private void initComponents(){
        /**
         * 内容编辑区
         */
        editPanel = new JPanel();
        editPanel.setLayout(new GridBagLayout());

        JPanel editLeft = new JPanel();
        editLeft.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP,15,20,false,false));
        avatarLabel = new JLabel();
        avatarLabel.setIcon(IconUtil.getIcon(this,"/icons/avatardef128.png"));
        avatarLabel.setPreferredSize(new Dimension(128,128));
        avatarButton = new NBSButton(
                Launcher.LaucherConfMapUtil.getValue("dapp.initStepBase.frame.button.upload.label","Upload Avatar")
                ,ColorCnst.MAIN_COLOR, ColorCnst.MAIN_COLOR_DARKER);
        avatarButton.setPreferredSize(new Dimension(100,25));
        editLeft.add(avatarLabel);
        editLeft.add(avatarButton);

        JPanel editRight = new JPanel();
        editRight.setLayout(new VerticalFlowLayout(VerticalFlowLayout.LEADING,10,25,false,false));

        JPanel peerLabelPanel = new JPanel();
        peerLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT,5,2));
        JLabel peerLabel = new JLabel(
                Launcher.LaucherConfMapUtil.getValue("dapp.initStepBase.frame.peerid.label","PeerID :"));
        peerLabel.setFont(FontUtil.getDefaultFont(15));
        peerLabel.setHorizontalAlignment(JLabel.RIGHT);
        peerLabel.setPreferredSize(new Dimension(60,40));
        peerLabelPanel.add(peerLabel);

        peerIdText = new JTextArea();
        peerIdText.setPreferredSize(new Dimension(245,40));
        peerIdText.setFont(FontUtil.getDefaultFont(13));
        peerIdText.setForeground(ColorCnst.FONT_GRAY);
        peerIdText.setLineWrap(true);
        peerIdText.setEditable(false);

        JPanel nickPanel = new JPanel();
        nickPanel.setLayout(new FlowLayout(FlowLayout.LEFT,5,2));
        JLabel nickLabelTitle = new JLabel(
                Launcher.LaucherConfMapUtil.getValue("dapp.initStepBase.frame.nickname.label","Nickname :"));
        nickLabelTitle.setHorizontalAlignment(JLabel.RIGHT);
        nickLabelTitle.setFont(FontUtil.getDefaultFont(15));
        nickLabelTitle.setPreferredSize(new Dimension(60,30));

        nickField = new JTextField();
        nickField.setForeground(ColorCnst.FONT_GRAY_DARKER);
        nickField.setHorizontalAlignment(JLabel.LEFT);
        nickField.setFont(FontUtil.getDefaultFont(15));
        nickField.setPreferredSize(new Dimension(245,30));

        nickPanel.add(nickLabelTitle);
        nickPanel.add(nickField);

        peerLabelPanel.add(peerIdText);
        editRight.add(peerLabelPanel);

        editRight.add(nickPanel);



        /**
         * 放置编辑
         */
        editPanel.add(editLeft,
                new GBC(0,0).setWeight(1,1).setFill(GBC.BOTH).setInsets(0,10,0,0));

        editPanel.add(editRight,
                new GBC(1,0).setWeight(7,1).setFill(GBC.BOTH).setInsets(0,0,0,10));

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,5));
        prevButton = new NBSButton(
                Launcher.LaucherConfMapUtil.getValue("dapp.initStepBase.frame.button.previous.label","上一步")
                ,ColorCnst.MAIN_COLOR,ColorCnst.MAIN_COLOR_DARKER);
        prevButton.setFont(FontUtil.getDefaultFont(14));
        prevButton.setPreferredSize(InitialDappFrame.buttonDimesion);

        saveButton = new NBSButton(
                Launcher.LaucherConfMapUtil.getValue("dapp.initStepBase.frame.button.save.label","保存")
                ,ColorCnst.MAIN_COLOR,ColorCnst.MAIN_COLOR_DARKER);
        saveButton.setFont(FontUtil.getDefaultFont(14));
        saveButton.setPreferredSize(InitialDappFrame.buttonDimesion);

        cancelButton =  new NBSButton(
                Launcher.LaucherConfMapUtil.getValue("dapp.initStepBase.frame.button.cancel.label","取消"),
                ColorCnst.FONT_GRAY,ColorCnst.FONT_GRAY_DARKER);
        cancelButton.setFont(FontUtil.getDefaultFont(14));
        cancelButton.setPreferredSize(InitialDappFrame.buttonDimesion);

        buttonPanel.add(prevButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
    }
    private void initView(){
        setLayout(new BorderLayout());

        add(editPanel,BorderLayout.CENTER);
        add(buttonPanel,BorderLayout.SOUTH);
    }

    private void setListeners(){
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InitialDappFrame.getContext().showStep(InitialDappFrame.InitDappSteps.setIpfs);
            }
        });
    }
}
