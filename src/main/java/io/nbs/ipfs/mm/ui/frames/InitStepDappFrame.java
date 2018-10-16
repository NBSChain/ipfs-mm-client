package io.nbs.ipfs.mm.ui.frames;

import io.ipfs.api.IPFS;
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
public class InitStepDappFrame extends JFrame {
    private Logger logger = LoggerFactory.getLogger(InitStepDappFrame.class);
    private static final int W = 550;
    private static final int H = 350;

    private IPFS ipfs;
    private static Point origin = new Point();

    public InitStepDappFrame(IPFS ipfs){
        this.ipfs = ipfs;


    }
}
