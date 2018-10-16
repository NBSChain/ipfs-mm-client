package io.nbs.ipfs.mm;

import io.ipfs.api.IPFS;
import io.nbs.ipfs.mm.cnsts.ColorCnst;
import io.nbs.ipfs.mm.cnsts.DappCnsts;
import io.nbs.ipfs.mm.cnsts.IPFSCnsts;
import io.nbs.ipfs.mm.ui.frames.MainFrame;
import io.nbs.ipfs.mm.util.AppPropsUtil;
import io.nbs.ipfs.mm.util.IconUtil;
import io.nbs.ipfs.mm.util.OSUtil;
import net.nbsio.ipfs.beans.PeerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
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

    private static Logger logger = LoggerFactory.getLogger(Launcher.class);

    private static Launcher context;

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

    private IPFS ipfs = null;

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

        //读取 properties 初始化配置
        processDappConf(agrs);
        initialStartup();

        String apiUrl;
        try{
            apiUrl = LaucherConfMapUtil.getIpfsAddressApi();
        }catch (Exception e){

        }


        currentFrame = new MainFrame(peerInfo);

        currentFrame.setBackground(ColorCnst.WINDOW_BACKGROUND);
        currentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if(OSUtil.getOsType()!=OSUtil.Mac_OS){
            currentFrame.setIconImage(logo.getImage());
        }
        currentFrame.setVisible(true);
    }

    private void processDappConf(String[] args){
        /* 1.初始化Dapp 配置 */
        Properties props = AppPropsUtil.getAppProps();
        if(props!=null){
            Enumeration enumeration = props.propertyNames();
            while (enumeration.hasMoreElements()){
                String key = (String)enumeration.nextElement();
                String val = AppPropsUtil.getProperty(key);
                LaucherConfMapUtil.put(key,val);
            }
            logger.info("Dapp 配置加载中...");
        }

        /* 2.处理启动参数 */
        for(String arg : args){
            if(arg.equalsIgnoreCase("--wrap-with-directory")||arg.equalsIgnoreCase("-w")){
                LaucherConfMapUtil.put(IPFSCnsts.WRAP_WITH_DIRECTORY_KEY,"true");
            }
        }

        /* 加载 i18n */
        String i18nConfName ;
        if(!LaucherConfMapUtil.containsKey(DappCnsts.KEY_I18N_PROPS_FILE)){
            i18nConfName = "zh-cn.properties";
        }else {
            i18nConfName = LaucherConfMapUtil.getValue(DappCnsts.KEY_I18N_PROPS_FILE)+ ".properties";
        }
        loadingI18n(i18nConfName);

        logger.info("Dapp 配置加载完成...");
        LaucherConfMapUtil.show();
    }

    private boolean loadingI18n(String i18nName){
        String file = DappCnsts.consturactPath(CURRENT_DIR,"conf",i18nName);
        try{
            Properties i18nProps = AppPropsUtil.loadExtProps(file);
            if(i18nProps!=null){
                Iterator<Map.Entry<Object, Object>> iterator = i18nProps.entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry entry = iterator.next();
                    String k = entry.getKey().toString();
                    String v = entry.getValue().toString();
                    LaucherConfMapUtil.put(k,v);
                }
            }
            return true;
        }catch (Exception e){
            return false;
        }
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

    
    /**
     * @author      : lanbery
     * @Datetime    : 2018/10/15
     * @Description  :
     * 
     */
    public static Launcher getContext(){
        return context;
    }

    public static class LaucherConfMapUtil {
        private static ConcurrentHashMap<String,String> DAPP_CONFIG_MAP = new ConcurrentHashMap();

        /**
         * @author      : lanbery
         * @Datetime    : 2018/10/15
         * @Description  :
         * 
         */
        public static void put(String key,String val){
            DAPP_CONFIG_MAP.put(key,val);
        }

        /**
         * @author      : lanbery
         * @Datetime    : 2018/10/15
         * @Description  :
         * 
         */
        public static String getValue(String key){
            return DAPP_CONFIG_MAP.get(key);
        }

        /**
         * @author      : lanbery
         * @Datetime    : 2018/10/15
         * @Description  :
         * 
         */
        public static boolean containsKey(String key){
            return DAPP_CONFIG_MAP.containsKey(key);
        }
        /**
         * @author      : lanbery
         * @Datetime    : 2018/10/15
         * @Description  :
         * 
         */
        public static String getValue(String key,String defVal){
            return DAPP_CONFIG_MAP.getOrDefault(key,defVal);
        }

        public static void show(){
            Iterator<Map.Entry<String,String>> it = DAPP_CONFIG_MAP.entrySet().iterator();
            while (it.hasNext()){
                Map.Entry<String,String> entry = it.next();
                logger.info("{} = {}",entry.getKey(),entry.getValue());
            }
        }

        /**
         * @author      : lanbery
         * @Datetime    : 2018/10/16
         * @Description  :
         * 
         */
        public static String getIpfsAddressApi() throws Exception {
            if(!DAPP_CONFIG_MAP.containsKey(IPFSCnsts.MM_HOST_KEY)||
            !DAPP_CONFIG_MAP.containsKey(IPFSCnsts.MM_API_PORT_KEY)){
                throw new Exception("no Host API config.");
            }

            if(!DAPP_CONFIG_MAP.containsKey(IPFSCnsts.MM_ADDRESS_API_KEY)){
                StringBuffer valBuf = new StringBuffer();
                valBuf.append("/ip4/")
                        .append(LaucherConfMapUtil.getValue(IPFSCnsts.MM_HOST_KEY))
                        .append("/tcp/")
                        .append(LaucherConfMapUtil.getValue(IPFSCnsts.MM_API_PORT_KEY));
                DAPP_CONFIG_MAP.put(IPFSCnsts.MM_ADDRESS_API_KEY,valBuf.toString());
                buildAddressValue(true);
            }
            return DAPP_CONFIG_MAP.get(IPFSCnsts.MM_ADDRESS_API_KEY);
        }

        /**
         * @author      : lanbery
         * @Datetime    : 2018/10/16
         * @Description  :
         * 
         */
        public static String getIpfsAddressGateway() throws Exception {
            if(!DAPP_CONFIG_MAP.containsKey(IPFSCnsts.MM_HOST_KEY)||
                    !DAPP_CONFIG_MAP.containsKey(IPFSCnsts.MM_API_PORT_KEY)){
                throw new Exception("no Host Gateway config.");
            }
            if(!DAPP_CONFIG_MAP.containsKey(IPFSCnsts.MM_ADDRESS_GATEWAY_KEY)){
                StringBuffer valBuf = new StringBuffer();
                valBuf.append(LaucherConfMapUtil.getValue(IPFSCnsts.MM_GATEWAY_PROTOCOL_KEY,"http"))
                        .append("://")
                        .append(LaucherConfMapUtil.getValue(IPFSCnsts.MM_HOST_KEY))
                        .append(":")
                        .append(LaucherConfMapUtil.getValue(IPFSCnsts.MM_GATEWAY_PORT_KEY));
                DAPP_CONFIG_MAP.put(IPFSCnsts.MM_ADDRESS_GATEWAY_KEY,valBuf.toString());
                buildAddressValue(false);
            }
            return DAPP_CONFIG_MAP.get(IPFSCnsts.MM_ADDRESS_GATEWAY_KEY);
        }

        /**
         * @author      : lanbery
         * @Datetime    : 2018/10/16
         * @Description  :
         *
         */
        private static void buildAddressValue(boolean apiAddress){
            String tVal;
            String tKey;
            if(apiAddress){
                tVal = "/ip4/${ipfs.mm.host}/tcp/${ipfs.mm.api-port}";
                tKey = IPFSCnsts.MM_ADDRESS_API_KEY;
            }else {
                tVal = "${ipfs.mm.gateway.protocol}://${ipfs.mm.host}:${ipfs.mm.gateway-port}";
                tKey = IPFSCnsts.MM_ADDRESS_GATEWAY_KEY;

            }
            try {
                AppPropsUtil.setProperty(tKey,tVal,"update Address");
            }catch (IOException e){
                logger.warn("update Address configuration failed.",e.getCause());
            }
        }
    }
}
