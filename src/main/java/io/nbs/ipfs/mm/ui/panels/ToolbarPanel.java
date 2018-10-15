package io.nbs.ipfs.mm.ui.panels;

import io.nbs.ipfs.mm.cnsts.DappCnsts;
import io.nbs.ipfs.mm.ui.components.NBSIconButton;
import io.nbs.ipfs.mm.ui.components.VerticalFlowLayout;
import io.nbs.ipfs.mm.ui.frames.MainFrame;
import io.nbs.ipfs.mm.util.AvatarImageHandler;
import io.nbs.ipfs.mm.util.IconUtil;
import net.nbsio.ipfs.beans.PeerInfo;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Copyright © 2015-2020 NBSChain Holdings Limited.
 * All rights reserved.
 *
 * @project ipfs-mm
 * <p>
 * Author   : lanbery
 * Created  : 2018/10/15
 */
public class ToolbarPanel extends JPanel {

    private static ToolbarPanel context;
    private JPanel upButtonPanel;
    private JPanel bottomPanel;

    /* 按钮 */
    private static NBSIconButton infoBTN;
    /**
     *
     */
    private static NBSIconButton imBTN;
    /**
     *
     */
    private static NBSIconButton dataBTN;
    /**
     *
     */
    private static NBSIconButton musicBTN;

    private static NBSIconButton aboutBTN;

    /**
     * 头像
     */
    private JLabel avatarLabel;

    /* ToolbarPanel comments :  构造函数*/
    public ToolbarPanel() {
        initComponents();
        initView();
        setListeners();
    }

    private void initComponents(){
        upButtonPanel = new JPanel();
        upButtonPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP,0,30,false,false));

        /* currentPeer avatar */
        PeerInfo peerInfo = MainFrame.getContext().getCurrentPeer();
        avatarLabel = new JLabel();
        ImageIcon icon = getAvatarIcon(peerInfo);
        avatarLabel.setIcon(icon);
        avatarLabel.setHorizontalAlignment(JLabel.CENTER);

        initialButton();

        bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new BorderLayout());
    }

    private void initView(){

    }

    private void setListeners(){

    }

    /*  comments : 初始化按钮 */
    private void initialButton(){

    }

    public static ToolbarPanel getContext() {
        return context;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(!isOpaque())return;
        int width       = getWidth();
        int height      = getHeight();
        Graphics2D g2   = (Graphics2D) g;
        GradientPaint gradientPaint = new GradientPaint(0,0,new Color(37,32,72),width,height,new Color(59,54,98));
        g2.setPaint(gradientPaint);
        g2.fillRect(0,0,width,height);
    }

    /*  comments : 获取头像 */
    private ImageIcon getAvatarIcon(PeerInfo peer){
        ImageIcon icon;
        if(peer!=null&& StringUtils.isNotBlank(peer.getId())
                &&StringUtils.isNotBlank(peer.getAvatarName())){
            String a48Path = DappCnsts.consturactPath(AvatarImageHandler.getAvatarProfileHome(),peer.getAvatarName());
            //System.out.println(a48Path);
            if((new File(a48Path)).exists()){
                icon = new ImageIcon(a48Path);
                Image image = icon.getImage().getScaledInstance(48,48,Image.SCALE_SMOOTH);
                icon.setImage(image);
            }else {
                icon = IconUtil.getIcon(this,"/icons/logo48.png");
            }
        }else {
            icon = IconUtil.getIcon(this,"/icons/logo48.png");
        }
        return icon;
    }
}
