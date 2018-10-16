package io.nbs.ipfs.mm.ui.frames;

import io.ipfs.api.IPFS;
import io.nbs.ipfs.mm.Launcher;
import io.nbs.ipfs.mm.ui.components.GBC;
import io.nbs.ipfs.mm.ui.components.NBSButton;
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

    private JTextField hostField;
    private JTextField apiPortFeild;
    private JTextField gatewayPortFeild;
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
        editPanel.setLayout(new GridBagLayout());
    }

    private void initView(){

    }

    private void setListeners(){

    }
}
