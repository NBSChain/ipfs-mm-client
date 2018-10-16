package io.nbs.ipfs.mm.ui.frames;

import io.ipfs.api.IPFS;
import io.nbs.ipfs.mm.ui.components.NBSButton;
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


    }

    private void initView(){

    }

    private void setListeners(){

    }
}
