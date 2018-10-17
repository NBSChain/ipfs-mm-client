package io.nbs.ipfs.mm.ui.frames;

import io.nbs.ipfs.mm.cnsts.ColorCnst;
import io.nbs.ipfs.mm.ui.panels.MainContentPanel;
import io.nbs.ipfs.mm.ui.panels.ToolbarPanel;
import io.nbs.ipfs.mm.ui.panels.about.AboutMasterPanel;
import io.nbs.ipfs.mm.util.FontUtil;
import io.nbs.ipfs.mm.util.OSUtil;
import net.nbsio.ipfs.beans.PeerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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
    private static JPanel mainJPanel;

    /**
     * 右侧窗口
     */
    private ToolbarPanel toolbarPanel;
    private JPanel leftMenuPanle;


    private MainContentPanel mainCentetPanel;
    private CardLayout cardLayout;

    private AboutMasterPanel aboutMasterPanel;


    public MainFrame(PeerInfo peerInfo){
        context = this;
        currentPeer = peerInfo;

        mainJPanel = new JPanel(true);

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
        //设置全局字体

        UIManager.put("Label.font",FontUtil.getDefaultFont());
        UIManager.put("Panel.font", FontUtil.getDefaultFont());
        UIManager.put("TextArea.font", FontUtil.getDefaultFont());

        UIManager.put("Panel.background", ColorCnst.WINDOW_BACKGROUND);
        UIManager.put("CheckBox.background", ColorCnst.WINDOW_BACKGROUND);
        if(OSUtil.getOsType() != OSUtil.Mac_OS){
            setUndecorated(true);//隐藏标题栏
            String windows = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
            try {
                UIManager.setLookAndFeel(windows);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        cardLayout = new CardLayout();
        /**
         * 功能按钮
         */
        leftMenuPanle = new JPanel();

        leftMenuPanle.setOpaque(false);
        toolbarPanel = new ToolbarPanel();
        toolbarPanel.setPreferredSize(new Dimension(MainFrame.TOOLBAR_WIDTH,MainFrame.H_SIZE));
        leftMenuPanle.add(toolbarPanel);


        /**
         * 主窗口
         */
        mainCentetPanel = new MainContentPanel();
        mainCentetPanel.setBackground(ColorCnst.WINDOW_BACKGROUND);
        mainCentetPanel.setLayout(cardLayout);

        aboutMasterPanel = new AboutMasterPanel();

        //TODO

        mainCentetPanel.add(aboutMasterPanel,MainCardLayoutTypes.ABOUT.name());
    }

    /* initView comments : */
    private void initView(){
        Dimension winDimension = new Dimension(W_SIZE,H_SIZE);
        setSize(winDimension);
        setMinimumSize(winDimension);

        mainJPanel.setLayout(new BorderLayout());
        mainJPanel.add(toolbarPanel,BorderLayout.WEST);

        mainJPanel.add(mainCentetPanel,BorderLayout.CENTER);

        //TODO 中心内容

        add(mainJPanel);
        centerScreen();
    }

    /*  comments : 设置监听器 */
    private void setListeners(){

        this.addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e) {
                currentWindowWidth = (int)e.getComponent().getBounds().getWidth();
                currentWindowWidth = (int)e.getComponent().getBounds().getHeight();
            }
        });
    }


    public PeerInfo getCurrentPeer(){
        return this.currentPeer;
    }

    protected void updateCurrentPeerInfo(PeerInfo peerInfo){
        if(peerInfo==null)return;
        this.currentPeer = peerInfo;
    }

    /**
     * 居中设置
     */
    private void centerScreen(){
        Toolkit tk = Toolkit.getDefaultToolkit();
        this.setLocation((tk.getScreenSize().width - W_SIZE) / 2,
                (tk.getScreenSize().height - H_SIZE) / 2);
    }

    public static enum MainCardLayoutTypes{
        INFO,IM,DATD,MUSIC,ABOUT,MEDIA;
    }
    /**
     * @author      : lanbery
     * @Datetime    : 2018/10/15
     * @Description  :
     * 
     */
    public static MainFrame getContext(){
        return context;
    }
}
