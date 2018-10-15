package io.nbs.ipfs.mm;

import io.nbs.ipfs.mm.cnsts.ColorCnst;
import io.nbs.ipfs.mm.cnsts.DappCnsts;
import io.nbs.ipfs.mm.ui.frames.MainFrame;
import io.nbs.ipfs.mm.util.IconUtil;
import io.nbs.ipfs.mm.util.OSUtil;
import net.nbsio.ipfs.beans.PeerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Copyright © 2015-2020 NBSChain Holdings Limited.
 * All rights reserved.
 *
 * @project workspace
 * <p>
 * Author   : lanbery
 * Created  : 2018/10/15
 */
public class Launcher {

    private Logger logger = LoggerFactory.getLogger(Launcher.class);

    private static Launcher context;

    private static ConcurrentHashMap DAPP_CONFIG_MAP = new ConcurrentHashMap();
    
    private JFrame currentFrame;
    public static ImageIcon logo ;

    /**
     * 文件基础路径
     * ${basedir}/.nbs/
     * .nbs/download/cache
     * files
     * music
     * videos
     * profiles
     */
    public static String appBasePath;
    public static String CURRENT_DIR;

    static {
        CURRENT_DIR = System.getProperty("user.dir");
    }

    public Launcher(){
        context = this;
        logo = IconUtil.getIcon(this,"/icons/nbs.png");
    }

    /**
     * @author      : lanbery
     * @Datetime    : 2018/10/15
     * @Description  :
     * 启动
     */
    protected void launch(String[] agrs){
        PeerInfo peerInfo = null;
        /* 1.初始化Dapp 配置 */

        /* 2.处理启动参数 */

        initialStartup();


        currentFrame = new MainFrame(peerInfo);


        currentFrame.setBackground(ColorCnst.WINDOW_BACKGROUND);
        currentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if(OSUtil.getOsType()!=OSUtil.Mac_OS){
            currentFrame.setIconImage(logo.getImage());
        }
        currentFrame.setVisible(true);
    }

    /**
     * @author      : lanbery
     * @Datetime    : 2018/10/15
     * @Description  :
     * 启动初始化内容
     * 配置加载顺序：agrs 优先 于 dapp-conf.properties
     */
    private void initialStartup(){
        appBasePath = DappCnsts.consturactPath(CURRENT_DIR,DappCnsts.NBS_ROOT);
        File appBaseFile = new File(appBasePath);
        if(!appBaseFile.exists()){
            appBaseFile.mkdirs();
        }

        //TODO
    }

    public static void initConfByKey(String k,Object v){
        DAPP_CONFIG_MAP.put(k,v);
    }

    public static Object updateByKey(String k,Object v){
        if(!DAPP_CONFIG_MAP.containsKey(k))throw new RuntimeException("the DAPP_KEY :"+k+"not exist.");
        DAPP_CONFIG_MAP.put(k,v);
        return v;
    }

    public static Object getDappConfigByKey(String k){
        return DAPP_CONFIG_MAP.get(k);
    }
    
    /**
     * @author      : lanbery
     * @Datetime    : 2018/10/15
     * @Description  :
     * 
     */
    public static String getStringDappConfigByKey(String key){
        if(!DAPP_CONFIG_MAP.containsKey(key))return null;
        
        Object result = DAPP_CONFIG_MAP.get(key);
        return result == null ? null : result.toString();
    }
    
    /**
     * @author      : lanbery
     * @Datetime    : 2018/10/15
     * @Description  :
     * 
     */
    public static Launcher getContext(){
        return context;
    }
}
