package io.nbs.ipfs.mm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
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

    static {

    }

    public Launcher(){
        context = this;
    }

    /**
     * @author      : lanbery
     * @Datetime    : 2018/10/15
     * @Description  :
     * 启动
     */
    protected void launch(String[] agrs){
        /* 1.初始化Dapp 配置 */

        /* 2.处理启动参数 */


    }

    /**
     * @author      : lanbery
     * @Datetime    : 2018/10/15
     * @Description  :
     * 启动初始化内容
     * 配置加载顺序：agrs 优先 于 dapp-conf.properties
     */
    private void initialStartup(String[] agrs){

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
