package io.nbs.ipfs.mm.ui.frames;

import io.ipfs.api.IPFS;
import io.nbs.ipfs.mm.Launcher;
import io.nbs.ipfs.mm.cnsts.ColorCnst;
import io.nbs.ipfs.mm.cnsts.IPFSCnsts;
import io.nbs.ipfs.mm.ui.components.GBC;
import io.nbs.ipfs.mm.ui.components.NBSButton;
import io.nbs.ipfs.mm.ui.components.VerticalFlowLayout;
import io.nbs.ipfs.mm.util.FontUtil;
import io.nbs.ipfs.mm.util.IconUtil;
import io.nbs.ipfs.mm.util.OSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * Copyright © 2015-2020 NBSChain Holdings Limited.
 * All rights reserved.
 *
 * @project ipfs-mm
 * <p>
 * Author   : lanbery
 * Created  : 2018/10/16
 */
public class InitStepIpfsFrame extends JFrame {
    private Logger logger = LoggerFactory.getLogger(InitStepIpfsFrame.class);
    private final String TITLE_KEY = "dapp.initStepIpfs.frame.title";
    private static final int W = 550;
    private static final int H = 350;
    private static final int LINE_H = 22;


    private IPFS ipfs;
    private static Point origin = new Point();

    private JPanel ctrlPanel;
    private JLabel closeLabel;
    private JPanel editPanel;
    private JPanel statusPanel;
    private JLabel statusLabel;
    private JPanel buttonPanel;

    /* */
    private JTextField hostField;
    private JTextField apiPortFeild;
    private JTextField gatewayPortFeild;

    private JLabel addrApiContents;
    private JLabel addrGatewayContents;



    private NBSButton nextButton;
    private NBSButton cancleButton;
    private NBSButton connectButton;

    private String protocolStr;
    private String hostStr;
    private String apiPort;
    private String gatewayPort;

    public InitStepIpfsFrame (){
        initIpfsDefaultConf();//最先加载
        initComponents();
        initView();
        setListeners();
    }

    private void initIpfsDefaultConf(){
        protocolStr = Launcher.LaucherConfMapUtil.getValue(IPFSCnsts.MM_GATEWAY_PROTOCOL_KEY,"http");
        hostStr = Launcher.LaucherConfMapUtil.getValue(IPFSCnsts.MM_HOST_KEY,"127.0.0.1");
        apiPort = Launcher.LaucherConfMapUtil.getValue(IPFSCnsts.MM_API_PORT_KEY,"5001");
        gatewayPort = Launcher.LaucherConfMapUtil.getValue(IPFSCnsts.MM_GATEWAY_PORT_KEY,"8080");
    }

    /**
     * @author      : lanbery
     * @Datetime    : 2018/10/16
     * @Description  :
     *
     */
    private void initComponents(){
        Dimension windowSize = new Dimension(W, H);
        setMinimumSize(windowSize);
        setMaximumSize(windowSize);

        /* title and close */
        ctrlPanel = new JPanel();
        ctrlPanel.setLayout(new GridBagLayout());
        JPanel closePanel = new JPanel();
        closePanel.setLayout(new FlowLayout(FlowLayout.RIGHT,10,5));
        closeLabel = new JLabel();
        closeLabel.setIcon(IconUtil.getIcon(this,"/icons/close.png"));
        closeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closePanel.add(closeLabel);

        /**
         * 标题
         */
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,2));

        JLabel titleLabel = new JLabel(Launcher.LaucherConfMapUtil.getValue(TITLE_KEY,"IPFS 链接设置"));
        titleLabel.setHorizontalAlignment(JLabel.LEFT);
        titleLabel.setFont(FontUtil.getDefaultFont(18));
        titlePanel.add(titleLabel);

        ctrlPanel.add(titlePanel,
                new GBC(0,0).setWeight(6,1).setFill(GBC.HORIZONTAL).setInsets(0,0,0,0));
        ctrlPanel.add(closePanel,
                new GBC(1,0).setWeight(1,1).setFill(GBC.HORIZONTAL).setInsets(0,40,15,0));

        /**
         * 内容编辑区
         */
        editPanel = new JPanel();
        editPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.LEADING,10,10,true,true));

        Dimension labelDimension = new Dimension(160,LINE_H);
        Dimension hostDimension = new Dimension(280,LINE_H);
        Dimension portDimension = new Dimension(100,LINE_H);
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
        Dimension buttonDimesion = new Dimension(115,35);
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,5));

        connectButton  = new NBSButton(
                Launcher.LaucherConfMapUtil.getValue("dapp.initStepIpfs.frame.button.connect.label","测试连接"),
                ColorCnst.MAIN_COLOR,ColorCnst.MAIN_COLOR_DARKER);
        connectButton.setFont(FontUtil.getDefaultFont(14));
        connectButton.setPreferredSize(buttonDimesion);

        nextButton  = new NBSButton(
                Launcher.LaucherConfMapUtil.getValue("dapp.initStepIpfs.frame.button.next.label","下一步"),
                ColorCnst.MAIN_COLOR,ColorCnst.MAIN_COLOR_DARKER);
        nextButton.setFont(FontUtil.getDefaultFont(14));
        nextButton.setPreferredSize(buttonDimesion);

        cancleButton = new NBSButton(
                Launcher.LaucherConfMapUtil.getValue("dapp.initStepIpfs.frame.button.cancel.label","取消"),
                ColorCnst.FONT_GRAY,ColorCnst.FONT_GRAY_DARKER);
        cancleButton.setFont(FontUtil.getDefaultFont(14));
        cancleButton.setPreferredSize(buttonDimesion);

        buttonPanel.add(connectButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(cancleButton);
    }

    /**
     * @author      : lanbery
     * @Datetime    : 2018/10/16
     * @Description  :
     *
     */
    private void initView(){
        //Frame
        JPanel framePanel = new JPanel();
        framePanel.setBorder(new LineBorder(ColorCnst.LIGHT_GRAY));
        framePanel.setLayout(new GridBagLayout());
        //添加顶部操作
        if(OSUtil.getOsType() != OSUtil.Mac_OS){
            setUndecorated(true);
            framePanel.add(ctrlPanel,
                    new GBC(0,0).setFill(GBC.HORIZONTAL).setWeight(1,1).setInsets(0,0,5,0));
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
        }

        //
        framePanel.add(editPanel,
                new GBC(0,1).setWeight(1,6).setFill(GBC.BOTH).setInsets(0,0,0,0));
        framePanel.add(statusPanel,
                new GBC(0,2).setWeight(1,1).setFill(GBC.BOTH).setInsets(5,0,0,0));
        framePanel.add(buttonPanel,
                new GBC(0,3).setWeight(1,1).setFill(GBC.BOTH).setInsets(5,0,10,0));

        add(framePanel);
        centerScreen();
    }

    /**
     * @author      : lanbery
     * @Datetime    : 2018/10/16
     * @Description  :
     * 
     */
    private void setListeners(){
        closeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(1);
                super.mouseClicked(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                closeLabel.setBackground(ColorCnst.LIGHT_GRAY);
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeLabel.setBackground(ColorCnst.WINDOW_BACKGROUND);
                super.mouseExited(e);
            }
        });

        if (OSUtil.getOsType() != OSUtil.Mac_OS)
        {
            addMouseListener(new MouseAdapter()
            {
                public void mousePressed(MouseEvent e)
                {
                    // 当鼠标按下的时候获得窗口当前的位置
                    origin.x = e.getX();
                    origin.y = e.getY();
                }
            });

            addMouseMotionListener(new MouseMotionAdapter()
            {
                public void mouseDragged(MouseEvent e)
                {
                    // 当鼠标拖动时获取窗口当前位置
                    Point p = InitStepIpfsFrame.this.getLocation();
                    // 设置窗口的位置
                    InitStepIpfsFrame.this.setLocation(p.x + e.getX() - origin.x, p.y + e.getY()
                            - origin.y);
                }
            });
        }

        cancleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
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

    private void showStatusPanel(String content,Color color){
        statusLabel.setText(content);
        if(color==null) color = ColorCnst.RED;
        statusLabel.setForeground(color);
        statusLabel.setVisible(true);
        statusLabel.updateUI();
    }

    private void hideStatusPanel(){
        statusLabel.setText("");
        statusLabel.setVisible(false);
        statusLabel.updateUI();
    }

    /**
     * 居中设置
     */
    private void centerScreen(){
        Toolkit tk = Toolkit.getDefaultToolkit();
        this.setLocation((tk.getScreenSize().width - W) / 2,
                (tk.getScreenSize().height - H) / 2);
    }
}
