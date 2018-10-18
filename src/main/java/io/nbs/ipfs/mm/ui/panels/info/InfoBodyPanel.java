package io.nbs.ipfs.mm.ui.panels.info;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;

import io.nbs.ipfs.mm.Launcher;
import io.nbs.ipfs.mm.cnsts.ColorCnst;
import io.nbs.ipfs.mm.cnsts.DappCnsts;
import io.nbs.ipfs.mm.ui.components.GBC;
import io.nbs.ipfs.mm.ui.components.LCJlabel;
import io.nbs.ipfs.mm.ui.components.VerticalFlowLayout;
import io.nbs.ipfs.mm.ui.filters.AvatarImageFileFilter;
import io.nbs.ipfs.mm.ui.frames.MainFrame;
import io.nbs.ipfs.mm.ui.listener.AbstractMouseListener;
import io.nbs.ipfs.mm.ui.panels.ParentAvailablePanel;
import io.nbs.ipfs.mm.util.AvatarImageHandler;
import io.nbs.ipfs.mm.util.FontUtil;
import net.nbsio.ipfs.beans.PeerInfo;
import net.nbsio.ipfs.cfg.ConfigCnsts;
import net.nbsio.ipfs.protocol.IPMParser;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * @Package : io.ipfs.nbs.ui.panels.info
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/1-11:00
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class InfoBodyPanel extends ParentAvailablePanel {

    private static InfoBodyPanel context;
    private JPanel avatarJPanel;
    private JLabel avatarLabel;
    private LCJlabel nickLabel;
    private LCJlabel locationsLabel;
    private JPanel peerPanel;
    private LCJlabel peerIDLabel;
    private JTextField peerIdField;
    private JFileChooser fileChooser;
    private IPFS ipfs;
    private AvatarImageHandler imageHandler ;

    public InfoBodyPanel(JPanel parent) {
        super(parent);
        context = this;
        ipfs = Launcher.getContext().getIpfs();
        imageHandler = AvatarImageHandler.getInstance();
        initComponents();
        initView();
        setListeners();
    }

    /**
     *
     */
    private void initComponents(){
        avatarJPanel = new JPanel();
        avatarLabel = new JLabel();
        PeerInfo current = getCurrent();

        ImageIcon avatarIcon = imageHandler.getAvatarImageIcon(current.getAvatar(),128,"/icons/nbs750.png");

        avatarLabel.setIcon(avatarIcon);
        avatarLabel.setBackground(ColorCnst.WINDOW_BACKGROUND_LIGHT);
        avatarLabel.setCursor(DappCnsts.HAND_CURSOR);

        //nick
        String nick = current.getNick()==null? current.getId() : current.getNick();
        nickLabel = new LCJlabel(nick);
        nickLabel.setFont(FontUtil.getDefaultFont(30));
        nickLabel.setHorizontalAlignment(JLabel.CENTER);
        //locations
        String locations = current.getLocations();
        if(StringUtils.isBlank(locations))locations = current.getIp()==null ? "" : current.getIp();
        locationsLabel = new LCJlabel(locations);
        locationsLabel.setFont(FontUtil.getDefaultFont(20));
        locationsLabel.setHorizontalAlignment(JLabel.CENTER);

        //peer
        peerPanel = new JPanel();
        peerIDLabel = new LCJlabel("Peer ID :");
        peerIDLabel.setHorizontalAlignment(JLabel.RIGHT);
        peerIDLabel.setFont(FontUtil.getDefaultFont(13));
        peerIdField = new JTextField(current.getId());
        peerIdField.setBorder(null);
        peerIdField.setHorizontalAlignment(JTextField.LEFT);
        peerIdField.setEditable(false);
        peerIdField.setBackground(ColorCnst.WINDOW_BACKGROUND);



    }

    private void initView(){
        setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP,0,20,true,false));
        /*=====================================================*/
        avatarLabel.setHorizontalAlignment(JLabel.CENTER);

        peerPanel.setLayout(new GridBagLayout());
        JLabel placeHolder = new JLabel();
        placeHolder.setMinimumSize(new Dimension(100,50));
        add(placeHolder);
        add(avatarLabel);
        peerPanel.add(peerIDLabel
        ,new GBC(0,0).setWeight(1,1).setFill(GBC.BOTH).setInsets(0,0,0,0));
        peerPanel.add(peerIdField
                ,new GBC(1,0).setWeight(1,1).setFill(GBC.BOTH).setInsets(0,10,0,0));

        add(avatarLabel);
               // new GBC(0,0).setWeight(1,5).setFill(GBC.HORIZONTAL).setInsets(0,0,0,0));
        add(nickLabel);
              //  new GBC(0,1).setWeight(1,2).setFill(GBC.HORIZONTAL).setInsets(0,0,0,0));

        add(locationsLabel);
        add(peerPanel);
    }
    private void setListeners(){
        //头像事件
        avatarLabel.addMouseListener(new AbstractMouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                uploadAvatar();
            }

            @Override
            public void mouseEntered(MouseEvent e) {

                avatarLabel.setToolTipText("点击修改头像");
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }
        });

        //修改昵称
        nickLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String oriNick = nickLabel.getText();
                String upText = JOptionPane.showInputDialog(context,"请输入新的昵称","修改昵称",JOptionPane.INFORMATION_MESSAGE);
                if(StringUtils.isBlank(upText)||upText.trim().equals(oriNick))return;

                upText = upText.trim();
                IPFS ipfs = Launcher.getContext().getIpfs();
                if(ipfs==null)return;
                try {
                    String enUpText = IPMParser.urlEncode(upText);
                    ipfs.config.set(ConfigCnsts.JSON_NICKNAME_KEY,enUpText);
                    MainFrame.getContext().getCurrentPeer().setNick(upText);
                    nickLabel.setText(upText);
                } catch (IOException ioe) {
                    logger.error("更新 IPFS config error :{}",ioe.getMessage());
                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                nickLabel.setToolTipText("点击修改昵称");
                nickLabel.setCursor(DappCnsts.HAND_CURSOR);
                super.mouseEntered(e);
            }
        });
    }

    private PeerInfo getCurrent(){
        return Launcher.getContext().getCurrentPeer();
    }

    /**
     *
     */
    private void uploadAvatar(){
        PeerInfo self = getCurrent();
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        AvatarImageFileFilter fileFilter = new AvatarImageFileFilter();
        fileChooser.addChoosableFileFilter(fileFilter);
        fileChooser.setFileFilter(fileFilter);
        fileChooser.showDialog(this,
                Launcher.LaucherConfMapUtil.getValue("dapp.initStepBase.frame.upload.avatar.title","Select Avatar Image"));
        File selectedFile = fileChooser.getSelectedFile();
        if(selectedFile != null) {
            new Thread(()->{
                File dlAvatar ;
                List<MerkleNode> nodes;
                String avatar;
                String avatarName = selectedFile.getName();
                try{
                    //上传前先压缩
                    File compressFile = imageHandler.createdAvatar4Profile(selectedFile,null);

                    NamedStreamable.FileWrapper fileWrapper = new NamedStreamable.FileWrapper(compressFile);
                    nodes = ipfs.add(fileWrapper);
                    avatar = nodes.get(0).hash.toBase58();

                    //下载头像
                    dlAvatar = downloadAvatar(avatar);
                    ImageIcon icon = imageHandler.getImageIconFromOrigin(dlAvatar,128);

                    //保存配置
                    ipfs.config.set(ConfigCnsts.JSON_AVATAR_KEY,avatar);
                    ipfs.config.set(ConfigCnsts.JSON_AVATAR_NAME_KEY,avatarName);
                    ipfs.config.set(ConfigCnsts.JSON_AVATAR_SUFFIX_KEY,ConfigCnsts.JSON_AVATAR_SUFFIX_PNG);
                    Launcher.getContext().getCurrentPeer().setAvatar(avatar);
                    Launcher.getContext().getCurrentPeer().setAvatarName(avatarName);
                    Launcher.getContext().getCurrentPeer().setAvatarSuffix(ConfigCnsts.JSON_AVATAR_SUFFIX_PNG);
                    //刷新显示
                    avatarLabel.setIcon(icon);
                    avatarLabel.updateUI();
                    updateToolbarAvatar();
                }catch (Exception e){
                    logger.error("上传失败：{}",e.getMessage());
                    JOptionPane.showMessageDialog(context,
                            Launcher.LaucherConfMapUtil.getValue("nbs.ui.panel.status.update.avatar.failure.tip","set avatar image failure."));
                }
            }).start();
        }
    }

    private File downloadAvatar(String hash) throws Exception{
        String path  = Launcher.LaucherConfMapUtil.getGatewayUrl(hash);
        URL url = new URL(path);
        String filePath = DappCnsts.consturactPath(AvatarImageHandler.getAvatarProfileHome(),hash+AvatarImageHandler.AVATAR_SUFFIX);
        File avatarFile = new File(filePath);
        boolean b = imageHandler.getFileFromIPFS(url,avatarFile);
        return avatarFile;
    }

    private void updateToolbarAvatar(){
        MainFrame.getContext().refreshToolbarAvatar();
    }
}
