package io.nbs.ipfs.mm.ui.frames;

import io.ipfs.api.IPFS;
import io.nbs.ipfs.mm.Launcher;
import io.nbs.ipfs.mm.cnsts.ColorCnst;
import io.nbs.ipfs.mm.cnsts.DappCnsts;
import io.nbs.ipfs.mm.ui.components.GBC;
import io.nbs.ipfs.mm.ui.components.NBSButton;
import io.nbs.ipfs.mm.util.FontUtil;
import io.nbs.ipfs.mm.util.IconUtil;
import io.nbs.ipfs.mm.util.OSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

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

    /**
     *
     */
    private JPanel ctrlPanel;
    private JPanel editPanel;
    private JLabel closeLabel;
    private JTextArea peerIdText;
    private JTextField nickField;
    private JPanel buttonPanel;

    private NBSButton prevButton;
    private NBSButton initButton;
    private NBSButton cancleButton;

    private JLabel statusLabel;
    private JPanel statusPanel;
    private JLabel avatarLabel;

    private NBSButton avatarButton;

    /**
     * @author      : lanbery
     * @Datetime    : 2018/10/16
     * @Description  :
     * 构造函数
     */
    public InitStepDappFrame(IPFS ipfs){
        this.ipfs = ipfs;
        initComponents();
        initView();
        setListeners();
        centerScreen();
    }

    /**
     * @author      : lanbery
     * @Datetime    : 2018/10/16
     * @Description  :
     * 初始化控件
     */
    private void initComponents(){
        Dimension windowSize = new Dimension(W, H);
        setMinimumSize(windowSize);
        setMaximumSize(windowSize);

        ctrlPanel = new JPanel();
        ctrlPanel.setLayout(new GridBagLayout());

        JPanel closePanel = new JPanel();
        closePanel.setLayout(new FlowLayout(FlowLayout.RIGHT,10,5));
        closeLabel = new JLabel();
        closeLabel.setIcon(IconUtil.getIcon(this,"/icons/close.png"));
        closeLabel.setCursor(DappCnsts.HAND_CURSOR);
        closePanel.add(closeLabel);

        /**
         * 标题
         */
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,2));

        JLabel titleLabel = new JLabel(Launcher.LaucherConfMapUtil.getValue("dapp.initStepBase.frame.title","欢迎加入NBS Chain，请设置信息"));
        titleLabel.setHorizontalAlignment(JLabel.LEFT);
        titleLabel.setFont(FontUtil.getDefaultFont(18));
        titlePanel.add(titleLabel);

        ctrlPanel.add(titlePanel,
                new GBC(0,0).setWeight(6,1).setFill(GBC.HORIZONTAL).setInsets(0,0,0,0));
        ctrlPanel.add(closePanel,
                new GBC(1,0).setWeight(1,1).setFill(GBC.HORIZONTAL).setInsets(0,40,30,0));

        /**
         * 内容编辑区
         */
        editPanel = new JPanel();
        editPanel.setLayout(new GridBagLayout());

        /**
         * 按钮区
         * 2行
         */
        Dimension buttonDimension = new Dimension(115,35);
        statusPanel = new JPanel();
        statusLabel = new JLabel();
        statusLabel.setFont(FontUtil.getDefaultFont(14));
        statusLabel.setForeground(ColorCnst.RED);
        statusLabel.setVisible(true);
        statusPanel.add(statusLabel);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,5));
        prevButton = new NBSButton(Launcher.LaucherConfMapUtil.getValue("dapp.initStepBase.frame.button.cancel.label","上一步")
                , ColorCnst.MAIN_COLOR,ColorCnst.MAIN_COLOR_DARKER);
        prevButton.setFont(FontUtil.getDefaultFont(14));
        prevButton.setPreferredSize(buttonDimension);

        initButton = new NBSButton(Launcher.LaucherConfMapUtil.getValue("dapp.initStepBase.frame.button.previous.label","保 存")
                , ColorCnst.MAIN_COLOR,ColorCnst.MAIN_COLOR_DARKER);
        initButton.setFont(FontUtil.getDefaultFont(14));
        initButton.setPreferredSize(buttonDimension);

        cancleButton = new NBSButton(Launcher.LaucherConfMapUtil.getValue("dapp.initStepBase.frame.button.save.label","取 消")
                ,ColorCnst.FONT_GRAY_DARKER,ColorCnst.DARK);
        cancleButton.setFont(FontUtil.getDefaultFont(14));
        cancleButton.setPreferredSize(buttonDimension);

        buttonPanel.add(prevButton);
        buttonPanel.add(initButton);
        buttonPanel.add(cancleButton);
    }

    private void initView(){
        //Frame
        JPanel framePanel = new JPanel();
        framePanel.setBorder(new LineBorder(ColorCnst.LIGHT_GRAY));
        framePanel.setLayout(new GridBagLayout());
        //添加顶部操作
        if(OSUtil.getOsType() != OSUtil.Mac_OS){
            setUndecorated(true);
            framePanel.add(ctrlPanel,
                    new GBC(0,0).setFill(GBC.HORIZONTAL).setWeight(1,1).setInsets(0,0,5,0));
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
        }

        //
        framePanel.add(editPanel,
                new GBC(0,1).setWeight(1,6).setFill(GBC.BOTH).setInsets(0,0,0,0));
        framePanel.add(statusPanel,
                new GBC(0,2).setWeight(1,1).setFill(GBC.BOTH).setInsets(5,0,0,0));
        framePanel.add(buttonPanel,
                new GBC(0,3).setWeight(1,1).setFill(GBC.BOTH).setInsets(5,0,10,0));

        add(framePanel);
    }

    private void setListeners(){
        closeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(1);
                super.mouseClicked(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                closeLabel.setBackground(ColorCnst.LIGHT_GRAY);
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeLabel.setBackground(ColorCnst.WINDOW_BACKGROUND);
                super.mouseExited(e);
            }
        });

        if (OSUtil.getOsType() != OSUtil.Mac_OS) {
            addMouseListener(new MouseAdapter()
            {
                public void mousePressed(MouseEvent e)
                {
                    // 当鼠标按下的时候获得窗口当前的位置
                    origin.x = e.getX();
                    origin.y = e.getY();
                }
            });

            addMouseMotionListener(new MouseMotionAdapter()
            {
                public void mouseDragged(MouseEvent e)
                {
                    // 当鼠标拖动时获取窗口当前位置
                    Point p = InitStepDappFrame.this.getLocation();
                    // 设置窗口的位置
                    InitStepDappFrame.this.setLocation(p.x + e.getX() - origin.x, p.y + e.getY()
                            - origin.y);
                }
            });

            cancleButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(1);
                }
            });


        }
    }


    private void centerScreen() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        this.setLocation((tk.getScreenSize().width - W) / 2,
                (tk.getScreenSize().height - H) / 2);
    }
}
