package io.nbs.ipfs.mm.ui.panels.initialize;

import io.ipfs.api.IPFS;
import io.nbs.ipfs.mm.Launcher;
import io.nbs.ipfs.mm.cnsts.ColorCnst;
import io.nbs.ipfs.mm.cnsts.IPFSCnsts;
import io.nbs.ipfs.mm.ui.components.NBSButton;
import io.nbs.ipfs.mm.ui.components.VerticalFlowLayout;
import io.nbs.ipfs.mm.ui.frames.InitialDappFrame;
import io.nbs.ipfs.mm.util.*;
import net.nbsio.ipfs.exceptions.IPFSInitialException;
import net.nbsio.ipfs.exceptions.IllegalFormatException;
import net.nbsio.ipfs.exceptions.NullArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;

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
    private static final Logger logger = LoggerFactory.getLogger(DappIPFSStepPanel.class);

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

    /**
     * @author      : lanbery
     * @Datetime    : 2018/10/17
     * @Description  :
     *
     */
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


        /* Status Panel */
        statusPanel = new JPanel();
        statusLabel = new JLabel();
        statusLabel.setFont(FontUtil.getDefaultFont(14));
        statusLabel.setForeground(ColorCnst.RED);
        statusPanel.setVisible(true);
        statusPanel.add(statusLabel);

        /* edit Panel Layout */
        editPanel.add(hostLabelPanel);
        editPanel.add(apiPortLabelPanel);
        editPanel.add(gatewayPortLabelPanel);
        editPanel.add(apiShowPanel);
        editPanel.add(gatewayShowPanel);
        editPanel.add(statusPanel);

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
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = "";
                try{
                    connectIPFS();
                    setValidTip(3);
                    msg = Launcher.LaucherConfMapUtil.getValue("dapp.initStepIpfs.frame.button.connect.success","Connected Successful.");
                    showStatusPanel(msg,ColorCnst.PROGRESS_BAR_START);
                }catch ( IPFSInitialException exception){
                    msg = Launcher.LaucherConfMapUtil.getValue("dapp.initStepIpfs.frame.button.connect.fail","Connected failure..");
                    logger.warn("Connected IPFS {} failure,maybe cause by :{}",buildAddress(true),exception.getMessage());
                    setValidTip(0);
                    showStatusPanel(msg,null);
                }catch (IllegalFormatException ex){
                    msg = Launcher.LaucherConfMapUtil.getValue("dapp.initStepIpfs.frame.button.connect.illegal","Connected failure..");
                    logger.warn("Connected IPFS {} failure,maybe cause by :{}",buildAddress(true),"格式错误");
                    showStatusPanel(msg,null);
                }
            }
        });

        //下一步
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = "";
                try{
                    IPFS ipfs = connectIPFS();
                    updateDappConfMap();
                    DappBaseStepPanel.getContext().setIpfs(ipfs).loadNodeInfo();
                    InitialDappFrame.getContext().showStep(InitialDappFrame.InitDappSteps.setDapp);
                }catch ( IPFSInitialException exception){
                    msg = Launcher.LaucherConfMapUtil.getValue("dapp.initStepIpfs.frame.connected.fail","Connected failure..");
                    logger.warn("Connected IPFS {} failure,maybe cause by :{}",buildAddress(true),exception.getMessage());
                    setValidTip(0);
                    showStatusPanel(msg,null);
                }catch (IllegalFormatException ex){
                    msg = Launcher.LaucherConfMapUtil.getValue("dapp.initStepIpfs.frame.connected.illegal","Connected failure..");
                    logger.warn("Connected IPFS {} failure,maybe cause by :{}",buildAddress(true),"格式错误");
                    showStatusPanel(msg,null);
                }
            }
        });

        cancleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
        /* */
        hostField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                refreshAddressShow(e,1);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                refreshAddressShow(e,1);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                refreshAddressShow(e,1);
            }
        });

        apiPortFeild.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                refreshAddressShow(e,2);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                refreshAddressShow(e,2);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                refreshAddressShow(e,2);
            }
        });

        gatewayPortFeild.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                refreshAddressShow(e,3);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                refreshAddressShow(e,3);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                refreshAddressShow(e,3);
            }
        });
    }

    private IPFS connectIPFS() throws IPFSInitialException, IllegalFormatException {
        String apiURL,gatewayURL;
        try{
            apiURL = ProtocolUtil.spliceIPFSAddressAPI(hostStr,apiPort);
            gatewayURL = ProtocolUtil.spliceIPFSGatewayURL(hostStr,gatewayPort,protocolStr);
        }catch (IllegalFormatException | NullArgumentException e){
            showStatusPanel(e.getMessage(),null);
            throw new IllegalFormatException("API format error");
        }

        try{
            IPFS ipfs = new IPFS(apiURL);
            Map m = ipfs.id();

            return ipfs;
        }catch (IllegalStateException e){
            logger.warn(e.getMessage());
            throw new IllegalFormatException("API format error",e.getCause());
        }catch (IOException | RuntimeException  e){
            throw new IPFSInitialException(e.getMessage(),e.getCause());
        }
    }

    /**
     * @author      : lanbery
     * @Datetime    : 2018/10/17
     * @Description  :
     * 更新配置缓存
     */
    private void updateDappConfMap(){
        //update Dapp_Conf_Map
        AppPropsUtil.setProperty(IPFSCnsts.MM_HOST_KEY,hostStr);
        AppPropsUtil.setProperty(IPFSCnsts.MM_API_PORT_KEY,apiPort);
        AppPropsUtil.setProperty(IPFSCnsts.MM_GATEWAY_PORT_KEY,gatewayPort);
        AppPropsUtil.setProperty(IPFSCnsts.MM_GATEWAY_PROTOCOL_KEY,protocolStr);
        Launcher.LaucherConfMapUtil.put(IPFSCnsts.MM_HOST_KEY,hostStr);
        Launcher.LaucherConfMapUtil.put(IPFSCnsts.MM_API_PORT_KEY,apiPort);
        Launcher.LaucherConfMapUtil.put(IPFSCnsts.MM_GATEWAY_PORT_KEY,gatewayPort);
        Launcher.LaucherConfMapUtil.put(IPFSCnsts.MM_GATEWAY_PROTOCOL_KEY,protocolStr);
    }

    private void showStatusPanel(String content,Color color){
        statusLabel.setText(content);
        if(color==null) color = ColorCnst.RED;
        statusLabel.setForeground(color);
        statusLabel.setVisible(true);
        statusLabel.updateUI();
    }

    private void clearStatusPanel(){
        statusLabel.setText("");
        statusLabel.setVisible(false);
        statusLabel.updateUI();
    }

    private void refreshAddressShow(DocumentEvent e,int type){
        if(type <= 0 || type >3)return;
        Document doc = e.getDocument();
        try{
            switch (type){
                case 1 :
                    hostStr = doc.getText(0,doc.getLength());
                    break;
                case 2 :
                    apiPort = doc.getText(0,doc.getLength());
                    break;
                case 3 :
                    gatewayPort = doc.getText(0,doc.getLength());
                    break;
                default:
                    return;
            }
        }catch (BadLocationException ble){ }
        addrApiContents.setText(buildAddress(true));
        addrApiContents.updateUI();
        addrGatewayContents.setText(buildAddress(false));
        addrGatewayContents.updateUI();

        clearConnectedTip();

        if(RegexUtils.checkIPv4Address(hostStr)&&RegexUtils.checkPort(apiPort)&&RegexUtils.checkPort(gatewayPort))
            clearStatusPanel();
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

    /**
     * @author      : lanbery
     * @Datetime    : 2018/10/16
     * @Description  :
     * 1 - api ;2 - gateway 3: all
     */
    private void setValidTip(int type){
        switch (type){
            case 1 :
                tipApiLabel.setIcon(passIcon);
                tipGatewayLabel.setIcon(warnIcon);
                break;
            case 2 :
                tipApiLabel.setIcon(warnIcon);
                tipGatewayLabel.setIcon(passIcon);
                break;
            case 3 :
                tipApiLabel.setIcon(passIcon);
                tipGatewayLabel.setIcon(passIcon);
                break;
            default:
                tipApiLabel.setIcon(warnIcon);
                tipGatewayLabel.setIcon(warnIcon);
                break;
        }
        tipApiLabel.setVisible(true);
        tipGatewayLabel.setVisible(true);
        tipGatewayLabel.updateUI();
        tipApiLabel.updateUI();
    }

    private void clearConnectedTip(){
        tipApiLabel.setVisible(false);
        tipGatewayLabel.setVisible(false);
        tipGatewayLabel.updateUI();
        tipApiLabel.updateUI();
    }
}
