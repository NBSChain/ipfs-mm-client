package io.nbs.ipfs.mm.ui.panels.info;

import io.ipfs.api.IPFS;
import io.nbs.ipfs.mm.Launcher;
import io.nbs.ipfs.mm.cnsts.ColorCnst;
import io.nbs.ipfs.mm.cnsts.DappCnsts;
import io.nbs.ipfs.mm.ui.components.GBC;
import io.nbs.ipfs.mm.ui.components.LCJlabel;
import io.nbs.ipfs.mm.ui.panels.ParentAvailablePanel;
import io.nbs.ipfs.mm.util.AvatarImageHandler;
import io.nbs.ipfs.mm.util.FontUtil;
import net.nbsio.ipfs.beans.PeerInfo;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : io.ipfs.nbs.ui.panels.info
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/1-10:58
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class InfoHeaderPanel extends ParentAvailablePanel {

    private JLabel avatarLabel;

    private LCJlabel nickLabel;
    private LCJlabel locationLabel;
    private LCJlabel peerIDPanel;
    private LCJlabel peerIDLabel;
    private JTextField peerIdField;

    private PeerInfo self;
    private static InfoHeaderPanel context;
    private JFileChooser fileChooser;
    private AvatarImageHandler imageHandler ;
    private IPFS ipfs;

    public InfoHeaderPanel(JPanel parent) {
        super(parent);
        context =this;
        ipfs = Launcher.getContext().getIpfs();
        self = Launcher.getContext().getCurrentPeer();
        imageHandler = AvatarImageHandler.getInstance();
        initComponents();
        initView();

        setListeners();

    }

    /**
     *
     */
    private void initComponents(){
        avatarLabel = new JLabel();
        nickLabel = new LCJlabel(ColorCnst.FONT_GRAY_DARKER);
        nickLabel.setFont(FontUtil.getDefaultFont(24));

        locationLabel = new LCJlabel();
        locationLabel.setFont(FontUtil.getDefaultFont(14));
        peerIDPanel = new LCJlabel();
        peerIDLabel = new LCJlabel();
        peerIDLabel.setFont(FontUtil.getDefaultFont(12));
        peerIdField = new JTextField();
        peerIdField.setHorizontalAlignment(JTextField.LEFT);
        peerIdField.setFont(FontUtil.getDefaultFont(12));
        peerIdField.setEditable(false);
        peerIdField.setBorder(null);
    }

    /**
     *
     */
    private void initView(){
        setLayout(new BorderLayout());
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();


        /**
         * avatar
         */
        String avatarPath = DappCnsts.consturactPath(AvatarImageHandler.getAvatarProfileHome(),self.getAvatar()+self.getAvatarSuffix());
        ImageIcon avatar = AvatarImageHandler.getInstance().getImageIconFromOrigin(avatarPath,200);
        avatarLabel.setIcon(avatar);

        //leftPanel.setPreferredSize(new Dimension(150,150));
        avatarLabel.setHorizontalAlignment(JLabel.CENTER);
        leftPanel.add(avatarLabel);

        //right begin
        rightPanel.setLayout(new GridBagLayout());

        if(self!=null&& StringUtils.isNotBlank(self.getNick()))nickLabel.setText(self.getNick());

        nickLabel.setHorizontalAlignment(JLabel.LEFT);

        locationLabel.setText(self.getLocations()!=null?self.getLocations():"");
        locationLabel.setHorizontalAlignment(JLabel.LEFT);

        JLabel peerIDTtile = new LCJlabel("Peer ID :");
        peerIDTtile.setHorizontalAlignment(JLabel.LEFT);

        peerIDTtile.setFont(FontUtil.getDefaultFont(12));

        //QmVJECTorWRbZAVnHeB2jpNnyUhfNAFJtWg8NSVRiAnrr5  测试用
        if(self!=null){
            peerIDLabel.setText(self.getId());
            peerIdField.setText(self.getId());
        }

        peerIDPanel.setLayout(new FlowLayout(FlowLayout.LEFT,2,2));
        peerIDPanel.add(peerIDTtile);
        peerIDPanel.add(peerIdField);

        rightPanel.add(nickLabel,
                new GBC(0,0).setFill(GBC.HORIZONTAL).setWeight(1,10).setInsets(0,15,0,0)
        );
        rightPanel.add(locationLabel,
                new GBC(0,1).setFill(GBC.HORIZONTAL).setWeight(1,9).setInsets(0,15,0,0)
        );
        rightPanel.add(peerIDPanel,
                new GBC(0,2).setFill(GBC.BOTH).setWeight(1,8).setInsets(0,15,5,0)
        );

        add(leftPanel,BorderLayout.WEST);
        add(rightPanel,BorderLayout.CENTER);

    }

    private void setListeners(){

    }



    /**
     * 更新数据库
     */
    private void updatePeers(){

    }
}
