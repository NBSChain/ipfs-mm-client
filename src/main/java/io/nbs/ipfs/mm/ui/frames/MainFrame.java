package io.nbs.ipfs.mm.ui.frames;

import net.nbsio.ipfs.beans.PeerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

/**
 * Copyright © 2015-2020 NBSChain Holdings Limited.
 * All rights reserved.
 *
 * @project ipfs-mm
 * <p>
 * Author   : lanbery
 * Created  : 2018/10/15
 */
public class MainFrame extends JFrame {
    protected static Logger logger = LoggerFactory.getLogger(MainFrame.class);

    protected static MainFrame context;

    public static final int W_SIZE = 920;
    public static final int H_SIZE = 650;
    public int currentWindowWidth = W_SIZE;
    public int currentWindowHeight = H_SIZE;
    public static  int RIGHT_EIDTH = 540;

    public static final int TOOLBAR_WIDTH = 80;

    private PeerInfo currentPeer = null;

    /* 主窗口 GUI */
    private static JPanel mainPanel;

    public MainFrame(PeerInfo peerInfo){
        context = this;
        if (peerInfo==null){
            logger.error("initial peer info error.");
            throw new RuntimeException("initial peer info error");
        }
        currentPeer = peerInfo;

        initServices();

        initComponets();
        initView();
        setListeners();
    }

    /**
     * 加载服务：
     * 1.定时消息监听 ed.
     */
    private void initServices(){

    }

    /*  comments : */
    private void initComponets(){

    }

    /* initView comments : */
    private void initView(){

    }

    /*  comments : 设置监听器 */
    private void setListeners(){
        
    }


    public PeerInfo getCurrentPeer(){
        return this.currentPeer;
    }

    protected void updateCurrentPeerInfo(PeerInfo peerInfo){
        if(peerInfo==null)return;
        this.currentPeer = peerInfo;
    }
    
    /**
     * @author      : lanbery
     * @Datetime    : 2018/10/15
     * @Description  :
     * 
     */
    protected static MainFrame getContext(){
        return context;
    }
}
