package io.nbs.ipfs.mm.ui.panels.info;

import com.alibaba.fastjson.JSON;
import io.ipfs.api.IPFS;
import io.nbs.ipfs.mm.Launcher;
import io.nbs.ipfs.mm.ui.components.GBC;
import io.nbs.ipfs.mm.ui.frames.MainFrame;
import io.nbs.ipfs.mm.ui.listener.AbstractMouseListener;
import io.nbs.ipfs.mm.ui.panels.ParentAvailablePanel;
import io.nbs.ipfs.mm.ui.panels.info.bitswap.InFoBitswapPanel;
import io.nbs.ipfs.mm.ui.panels.info.bitswap.InfoBwStatsPanel;
import io.nbs.ipfs.mm.ui.panels.info.bitswap.InfoRepoPanel;
import io.nbs.ipfs.services.bitswap.BitSwapService;
import net.nbsio.ipfs.beans.ResData;
import net.nbsio.ipfs.beans.bw.BitSwap;
import net.nbsio.ipfs.beans.bw.BwStats;
import net.nbsio.ipfs.beans.repo.RepoStat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Package : io.nbs.client.ui.panels.info
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/10-21:36
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class InfoFooterPanel extends ParentAvailablePanel {
    private static InfoFooterPanel context;

    private BitSwapService bitSwapService;

    private InFoBitswapPanel bitswapPanel;

    private InfoBwStatsPanel bwStatsPanel;

    private InfoRepoPanel repoPanel;

    private IPFS ipfs;


    /**
     * construction
     */
    public InfoFooterPanel(JPanel parent) {
        super(parent);
        ipfs = Launcher.getContext().getIpfs();
        initComponents();
        initView();
        setListeners();
    }

    /**
     * [initComponents description]
     *
     * @return {[type]} [description]
     */
    private void initComponents() {
        repoPanel = new InfoRepoPanel(this);

        bitswapPanel = new InFoBitswapPanel(this);
        //bitswapPanel.setBackground(ColorCnst.MAIN_COLOR);

        bwStatsPanel = new InfoBwStatsPanel(this);
    }

    /**
     * [initView description]
     *
     * @return {[type]} [description]
     */
    private void initView() {
        setLayout(new GridBagLayout());
        //setOpaque(false);
        add(repoPanel
                ,new GBC(0,0).setWeight(1,1).setFill(GBC.BOTH).setInsets(0,20,20,0));
        add(bitswapPanel
        ,new GBC(1,0).setWeight(1,1).setFill(GBC.BOTH).setInsets(0,10,20,0));
        add(bwStatsPanel
                ,new GBC(2,0).setWeight(1,1).setFill(GBC.BOTH).setInsets(0,10,20,20));

        //
        new Thread(()->{
           getBitswap();
           getBwStats();
           getRepoInfo();
        }).start();

    }

    private void setListeners() {
        bwStatsPanel.addMouseListener(new AbstractMouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                autoRefreshInfo();
            }
        });
    }

    /**
     * [getContext description]
     *
     * @return {[type]} [description]
     */
    public static InfoFooterPanel getContext() {
        return context;
    }

    public void autoRefreshInfo(){
        if(MainFrame.INFO_REFREHING)return;
        MainFrame.INFO_REFREHING = true;
        new Thread(()->{
            while (MainFrame.INFO_REFREHING){
                getBitswap();
                getBwStats();
                getRepoInfo();
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void getRepoInfo(){
        bitSwapService = BitSwapService.getInstance();
        ResData<RepoStat> resData = bitSwapService.getRepoStat(MainFrame.getContext().getHttpHelper());
        if(resData.getCode()==0){
            repoPanel.refreshVolume(resData.getData(),Launcher.DOWNLOAD_FILE_PATH);
        }
    }

    private void getBwStats(){
        try {
            Map m = ipfs.stats.bw();
            String json = JSON.toJSONString(m);
           // logger.info(json);
            BwStats bwStats = JSON.parseObject(json,BwStats.class);
            if(bwStats!=null){
                bwStatsPanel.refreshVolume(bwStats);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void getBitswap(){
        bitSwapService = BitSwapService.getInstance();
        ResData<BitSwap> resData = bitSwapService.getBitSwapStat(MainFrame.getContext().getHttpHelper());
        if(resData.getCode()==0){
            bitswapPanel.refreshData(resData.getData());
        }
    }
}