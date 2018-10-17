package io.nbs.ipfs.mm.ui.panels.initialize;

import io.nbs.ipfs.mm.Launcher;
import io.nbs.ipfs.mm.cnsts.ColorCnst;
import io.nbs.ipfs.mm.cnsts.IPFSCnsts;
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
public class DappIPFSStepPanel extends JPanel {

    private static final int LINE_H = 22;


    private JPanel editPanel;
    private JPanel statusPanel;
    private JLabel statusLabel;

    /* */
    private JTextField hostField;
    private JTextField apiPortFeild;
    private JTextField gatewayPortFeild;

    private JLabel addrApiContents;
    private JLabel addrGatewayContents;

    private JPanel buttonPanel;
    private NBSButton connectButton;
    private NBSButton nextButton;
    private NBSButton cancleButton;

    private Icon passIcon;
    private Icon warnIcon;

    private JLabel tipApiLabel;
    private JLabel tipGatewayLabel;

    private String protocolStr;
    private String hostStr;
    private String apiPort;
    private String gatewayPort;


    public DappIPFSStepPanel(){
        initIpfsDefaultConf();
        initComponents();
        initView();
        setListeners();
    }

    private void initIpfsDefaultConf(){
        protocolStr = Launcher.LaucherConfMapUtil.getValue(IPFSCnsts.MM_GATEWAY_PROTOCOL_KEY,"http");
        hostStr = Launcher.LaucherConfMapUtil.getValue(IPFSCnsts.MM_HOST_KEY,"127.0.0.1");
        apiPort = Launcher.LaucherConfMapUtil.getValue(IPFSCnsts.MM_API_PORT_KEY,"5001");
        gatewayPort = Launcher.LaucherConfMapUtil.getValue(IPFSCnsts.MM_GATEWAY_PORT_KEY,"8080");

        passIcon = IconUtil.getIcon(this,"/icons/pass18.png");
        warnIcon = IconUtil.getIcon(this,"/icons/warn18.png");
    }

    private void initComponents(){
        /**
         * 内容编辑区
         */
        editPanel = new JPanel();
        editPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.LEADING,10,10,true,true));
        Dimension labelDimension = new Dimension(160,LINE_H);
        Dimension hostDimension = new Dimension(280,LINE_H);
        Dimension portDimension = new Dimension(100,LINE_H);

        tipApiLabel = new JLabel();
        tipApiLabel.setPreferredSize(new Dimension(18,18));
        tipApiLabel.setIcon(passIcon);
        tipApiLabel.setVisible(false);

        tipGatewayLabel = new JLabel();
        tipGatewayLabel.setPreferredSize(new Dimension(18,18));
        tipGatewayLabel.setIcon(passIcon);
        tipGatewayLabel.setVisible(false);

        /* host */
        JPanel hostLabelPanel = new JPanel();
        hostLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT,5,2));
        String hostLabelContent = Launcher.LaucherConfMapUtil.getValue("dapp.initStepIpfs.frame.host.label","Host :");
        JLabel hostLabel = new JLabel(hostLabelContent);
        hostLabel.setFont(FontUtil.getDefaultFont(15));
        hostLabel.setHorizontalAlignment(JLabel.RIGHT);
        hostLabel.setPreferredSize(labelDimension);

        hostField = new JTextField(hostStr);
        hostField.setFont(FontUtil.getDefaultFont(15));
        hostField.setHorizontalAlignment(JLabel.LEFT);
        hostField.setPreferredSize(hostDimension);

        hostLabelPanel.add(hostLabel);
        hostLabelPanel.add(hostField);

        /* api port */
        JPanel apiPortLabelPanel = new JPanel();
        apiPortLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT,5,2));
        String apiLabelContent = Launcher.LaucherConfMapUtil.getValue("dapp.initStepIpfs.frame.apiPort.label","Api Port :");
        JLabel apiPortLabel = new JLabel(apiLabelContent);
        apiPortLabel.setFont(FontUtil.getDefaultFont(15));
        apiPortLabel.setHorizontalAlignment(JLabel.RIGHT);
        apiPortLabel.setPreferredSize(labelDimension);

        apiPortFeild = new JTextField(apiPort);
        apiPortFeild.setFont(FontUtil.getDefaultFont(15));
        apiPortFeild.setHorizontalAlignment(JLabel.LEFT);
        apiPortFeild.setPreferredSize(portDimension);

        apiPortLabelPanel.add(apiPortLabel);
        apiPortLabelPanel.add(apiPortFeild);

        /* api port */
        JPanel gatewayPortLabelPanel = new JPanel();
        gatewayPortLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT,5,2));
        String gatewayPortLabelContent = Launcher.LaucherConfMapUtil.getValue("dapp.initStepIpfs.frame.gatewayPort.label","Gateway Port :");
        JLabel gatewayPortLabel = new JLabel(gatewayPortLabelContent);
        gatewayPortLabel.setFont(FontUtil.getDefaultFont(15));
        gatewayPortLabel.setHorizontalAlignment(JLabel.RIGHT);
        gatewayPortLabel.setPreferredSize(labelDimension);

        gatewayPortFeild = new JTextField(gatewayPort);
        gatewayPortFeild.setFont(FontUtil.getDefaultFont(15));
        gatewayPortFeild.setHorizontalAlignment(JLabel.LEFT);
        gatewayPortFeild.setPreferredSize(portDimension);

        gatewayPortLabelPanel.add(gatewayPortLabel);
        gatewayPortLabelPanel.add(gatewayPortFeild);

        /* show Panel */
        Dimension showLabelDimesion = new Dimension(160,LINE_H);
        Dimension showTextDimesion = new Dimension(300,LINE_H);
        JPanel apiShowPanel = new JPanel();
        apiShowPanel.setLayout(new FlowLayout(FlowLayout.LEFT,5,2));
        JLabel apiShowLabel = new JLabel(Launcher.LaucherConfMapUtil.getValue("dapp.initStepIpfs.frame.address.api.label","Address.API :"));
        apiShowLabel.setFont(FontUtil.getDefaultFont(15));
        apiShowLabel.setHorizontalAlignment(JLabel.RIGHT);
        apiShowLabel.setPreferredSize(showLabelDimesion);
        addrApiContents = new JLabel();
        addrApiContents.setFont(FontUtil.getDefaultFont(15));
        addrApiContents.setHorizontalAlignment(JLabel.LEFT);
        //addrApiContents.setPreferredSize(showTextDimesion);
        addrApiContents.setForeground(ColorCnst.FONT_ABOUT_TITLE_BLUE);
        String apiURI = buildAddress(true);
        addrApiContents.setText(apiURI);
        apiShowPanel.add(apiShowLabel);
        apiShowPanel.add(addrApiContents);
        apiShowPanel.add(tipApiLabel);

        JPanel gatewayShowPanel = new JPanel();
        gatewayShowPanel.setLayout(new FlowLayout(FlowLayout.LEFT,5,2));
        JLabel gatewayShowLabel = new JLabel(Launcher.LaucherConfMapUtil.getValue("dapp.initStepIpfs.frame.address.gateway.label","Address.Gateway :"));
        gatewayShowLabel.setFont(FontUtil.getDefaultFont(15));
        gatewayShowLabel.setHorizontalAlignment(JLabel.RIGHT);
        gatewayShowLabel.setPreferredSize(showLabelDimesion);
        addrGatewayContents = new JLabel();
        addrGatewayContents.setFont(FontUtil.getDefaultFont(15));
        addrGatewayContents.setHorizontalAlignment(JLabel.LEFT);
        //addrGatewayContents.setPreferredSize(showTextDimesion);
        addrGatewayContents.setForeground(ColorCnst.FONT_ABOUT_TITLE_BLUE);
        String gwURI = buildAddress(false);
        addrGatewayContents.setText(gwURI);

        gatewayShowPanel.add(gatewayShowLabel);
        gatewayShowPanel.add(addrGatewayContents);
        gatewayShowPanel.add(tipGatewayLabel);

        /* edit Panel Layout */
        editPanel.add(hostLabelPanel);
        editPanel.add(apiPortLabelPanel);
        editPanel.add(gatewayPortLabelPanel);
        editPanel.add(apiShowPanel);
        editPanel.add(gatewayShowPanel);

        /* Status Panel */
        statusPanel = new JPanel();
        statusLabel = new JLabel();
        statusLabel.setFont(FontUtil.getDefaultFont(14));
        statusLabel.setForeground(ColorCnst.RED);
        statusPanel.setVisible(true);
        statusPanel.add(statusLabel);

        /* Operation Panel */
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,5));

        connectButton  = new NBSButton(
                Launcher.LaucherConfMapUtil.getValue("dapp.initStepIpfs.frame.button.connect.label","测试连接"),
                ColorCnst.MAIN_COLOR,ColorCnst.MAIN_COLOR_DARKER);
        connectButton.setFont(FontUtil.getDefaultFont(14));
        connectButton.setPreferredSize(InitialDappFrame.buttonDimesion);

        nextButton  = new NBSButton(
                Launcher.LaucherConfMapUtil.getValue("dapp.initStepIpfs.frame.button.next.label","下一步"),
                ColorCnst.MAIN_COLOR,ColorCnst.MAIN_COLOR_DARKER);
        nextButton.setFont(FontUtil.getDefaultFont(14));
        nextButton.setPreferredSize(InitialDappFrame.buttonDimesion);

        cancleButton = new NBSButton(
                Launcher.LaucherConfMapUtil.getValue("dapp.initStepIpfs.frame.button.cancel.label","取消"),
                ColorCnst.FONT_GRAY,ColorCnst.FONT_GRAY_DARKER);
        cancleButton.setFont(FontUtil.getDefaultFont(14));
        cancleButton.setPreferredSize(InitialDappFrame.buttonDimesion);

        buttonPanel.add(connectButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(cancleButton);
    }
    private void initView(){
        setLayout(new BorderLayout());
        add(editPanel,BorderLayout.CENTER);
        add(buttonPanel,BorderLayout.SOUTH);
    }

    /**
     * @author      : lanbery
     * @Datetime    : 2018/10/17
     * @Description  :
     * 事件设置
     */
    private void setListeners(){

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InitialDappFrame.getContext().showStep(InitialDappFrame.InitDappSteps.setDapp);
            }
        });
    }


    private String buildAddress(boolean isApi){
        StringBuffer buf = new StringBuffer();
        if(isApi){
            buf.append("/ip4/")
                    .append(hostStr).append("/tcp/").append(apiPort);
        }else {
            buf.append(protocolStr).append("://")
                    .append(hostStr).append(":").append(gatewayPort);
        }
        return buf.toString();
    }
}
