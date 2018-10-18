package io.nbs.ipfs.mm.ui.panels;

import io.nbs.ipfs.mm.Launcher;
import io.nbs.ipfs.mm.cnsts.DappCnsts;
import io.nbs.ipfs.mm.ui.components.GBC;
import io.nbs.ipfs.mm.ui.components.NBSIconButton;
import io.nbs.ipfs.mm.ui.components.VerticalFlowLayout;
import io.nbs.ipfs.mm.ui.frames.MainFrame;
import io.nbs.ipfs.mm.util.AvatarImageHandler;
import io.nbs.ipfs.mm.util.ButtonIconUtil;
import io.nbs.ipfs.mm.util.IconUtil;
import net.nbsio.ipfs.beans.PeerInfo;
import net.nbsio.ipfs.cfg.ConfigCnsts;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;

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

        syncLoadAvatar();
    }

    private void initComponents(){
        upButtonPanel = new JPanel();
        upButtonPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP,0,30,false,false));

        /* currentPeer avatar */
        PeerInfo peerInfo = MainFrame.getContext().getCurrentPeer();
        avatarLabel = new JLabel();
        //ImageIcon icon = getAvatarIcon(peerInfo);
        //avatarLabel.setIcon(icon);
        avatarLabel.setPreferredSize(new Dimension(48,48));
        avatarLabel.setHorizontalAlignment(JLabel.CENTER);

        initialButton();

        bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new BorderLayout());
    }

    private void initView(){
        setLayout(new GridBagLayout());
        upButtonPanel.setOpaque(false);

        upButtonPanel.add(avatarLabel);

        upButtonPanel.add(infoBTN);
        upButtonPanel.add(imBTN);
        upButtonPanel.add(dataBTN);
        upButtonPanel.add(musicBTN);


        bottomPanel.add(aboutBTN);

        add(upButtonPanel,
                new GBC(0,0).setWeight(1,7).setFill(GBC.VERTICAL).setInsets(2,0,0,0));

        add(bottomPanel,
                new GBC(0,1).setWeight(1,1).setFill(GBC.VERTICAL).setInsets(0,0,2,0));


    }

    private void setListeners(){

    }

    /*  comments : 初始化按钮 */
    private void initialButton(){
        infoBTN = ButtonIconUtil.infoBTN;
        imBTN = ButtonIconUtil.imBTN;
        dataBTN =ButtonIconUtil.dataBTN;
        musicBTN = ButtonIconUtil.musicBTN;
        aboutBTN = ButtonIconUtil.aboutBTN;
    }

    private void syncLoadAvatar(){
        PeerInfo info = Launcher.getContext().getCurrentPeer();
        if(info!=null && StringUtils.isNotBlank(info.getAvatar())){
            new Thread(()->{
                ImageIcon icon;
                try{
                    String avatarHash = info.getAvatar();
                    String path = Launcher.LaucherConfMapUtil.getGatewayUrl(avatarHash);
                    URL url = new URL(path);
                    String avatarFilePath = DappCnsts.consturactPath(AvatarImageHandler.getAvatarProfileHome(),avatarHash+AvatarImageHandler.AVATAR_SUFFIX);
                    File avatarFile = new File(avatarFilePath);
                    AvatarImageHandler.getInstance().getFileFromIPFS(url,avatarFile);
                    icon = AvatarImageHandler.getInstance().getImageIconFromOrigin(avatarFile,48);
                    avatarLabel.setIcon(icon);
                    avatarLabel.updateUI();
                }catch (Exception e){
                    icon = IconUtil.getIcon(this,"/icons/logo48.png");
                    avatarLabel.setIcon(icon);
                    avatarLabel.updateUI();
                }
            }).start();
        }else {
            ImageIcon iconDef = IconUtil.getIcon(this,"/icons/logo48.png");
            avatarLabel.setIcon(iconDef);
            avatarLabel.updateUI();
        }
    }
    /**
     * @author      : lanbery
     * @Datetime    : 2018/10/18
     * @Description  :
     * 刷新toolbar 头像
     */
    public void refreshAvatar(){
        PeerInfo info = Launcher.getContext().getCurrentPeer();
        if(info==null|| StringUtils.isBlank(info.getAvatar()))return;
        String baseFilePath = DappCnsts.consturactPath(AvatarImageHandler.getAvatarProfileHome(),info.getAvatar()+ ConfigCnsts.JSON_AVATAR_SUFFIX_PNG);
        File baseFile = new File(baseFilePath);
        if(baseFile.exists()){
            ImageIcon icon = AvatarImageHandler.getInstance().getImageIconFromOrigin(baseFile,48);
            avatarLabel.setIcon(icon);
            avatarLabel.updateUI();
        }
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
