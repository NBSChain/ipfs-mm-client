package io.nbs.ipfs.mm.ui.frames;

import io.ipfs.api.IPFS;
import io.nbs.ipfs.mm.Launcher;
import io.nbs.ipfs.mm.ui.components.GBC;
import io.nbs.ipfs.mm.ui.components.NBSButton;
import io.nbs.ipfs.mm.ui.components.VerticalFlowLayout;
import io.nbs.ipfs.mm.util.FontUtil;
import io.nbs.ipfs.mm.util.IconUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

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


    private IPFS ipfs;
    private static Point origin = new Point();

    private JPanel ctrlPanel;
    private JLabel closeLabel;

    private JPanel editPanel;

    /* */
    private JTextField hostField;
    private JTextField apiPortFeild;
    private JTextField gatewayPortFeild;

    private JLabel addrApiContents;
    private JLabel addrGatewayContents;

    private JPanel buttonPanel;

    private NBSButton nextButton;
    private NBSButton cancleButton;

    public InitStepIpfsFrame (){

        initComponents();
        initView();
        setListeners();
    }

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
        //titlePanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel(Launcher.LaucherConfMapUtil.getValue(TITLE_KEY,"IPFS 链接设置"));
        titleLabel.setHorizontalAlignment(JLabel.LEFT);
        titleLabel.setFont(FontUtil.getDefaultFont(18));
        titlePanel.add(titleLabel);

        ctrlPanel.add(titlePanel,
                new GBC(0,0).setWeight(6,1).setFill(GBC.HORIZONTAL).setInsets(0,0,0,0));
        ctrlPanel.add(closePanel,
                new GBC(1,0).setWeight(1,1).setFill(GBC.HORIZONTAL).setInsets(0,40,30,0));

        /**
         * 内容编辑区
         */
        editPanel = new JPanel();
        editPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.LEADING,10,25,false,false));

        Dimension labelDimension = new Dimension(180,30);
        Dimension hostDimension = new Dimension(280,30);
        Dimension portDimension = new Dimension(100,30);
        /* host */
        JPanel hostLabelPanel = new JPanel();
        hostLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT,5,2));
        String hostLabelContent = Launcher.LaucherConfMapUtil.getValue("","Host :");
        JLabel hostLabel = new JLabel(hostLabelContent);
        hostLabel.setFont(FontUtil.getDefaultFont(15));
        hostLabel.setHorizontalAlignment(JLabel.RIGHT);
        hostLabel.setPreferredSize(labelDimension);

        hostField = new JTextField(Launcher.LaucherConfMapUtil.getValue("","127.0.0.1"));
        hostField.setFont(FontUtil.getDefaultFont(15));
        hostField.setHorizontalAlignment(JLabel.LEFT);
        hostField.setPreferredSize(hostDimension);

        /* api port */
        JPanel apiPortLabelPanel = new JPanel();
        apiPortLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT,5,2));
        String apiLabelContent = Launcher.LaucherConfMapUtil.getValue("","Api Port :");
        JLabel apiPortLabel = new JLabel(apiLabelContent);
        apiPortLabel.setFont(FontUtil.getDefaultFont(15));
        apiPortLabel.setHorizontalAlignment(JLabel.RIGHT);
        apiPortLabel.setPreferredSize(labelDimension);

        apiPortFeild = new JTextField(Launcher.LaucherConfMapUtil.getValue("","5001"));
        apiPortFeild.setFont(FontUtil.getDefaultFont(15));
        apiPortFeild.setHorizontalAlignment(JLabel.LEFT);
        apiPortFeild.setPreferredSize(portDimension);

        /* api port */
        JPanel gatewayPortLabelPanel = new JPanel();
        gatewayPortLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT,5,2));
        String gatewayPortLabelContent = Launcher.LaucherConfMapUtil.getValue("","Api Port :");
        JLabel gatewayPortLabel = new JLabel(gatewayPortLabelContent);
        gatewayPortLabel.setFont(FontUtil.getDefaultFont(15));
        gatewayPortLabel.setHorizontalAlignment(JLabel.RIGHT);
        gatewayPortLabel.setPreferredSize(labelDimension);

        gatewayPortFeild = new JTextField(Launcher.LaucherConfMapUtil.getValue("","8080"));
        gatewayPortFeild.setFont(FontUtil.getDefaultFont(15));
        gatewayPortFeild.setHorizontalAlignment(JLabel.LEFT);
        gatewayPortFeild.setPreferredSize(portDimension);

        /* show Panel */




    }

    private void initView(){

    }

    private void setListeners(){

    }
}
