package io.nbs.ipfs.mm.ui.panels.initialize;

import javax.swing.*;

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

    public DappIPFSStepPanel(){
        initComponents();
        initView();
        setListeners();
    }

    private void initComponents(){
        JLabel label = new JLabel("IPFS");

        add(label);
    }
    private void initView(){

    }
    private void setListeners(){

    }
}
