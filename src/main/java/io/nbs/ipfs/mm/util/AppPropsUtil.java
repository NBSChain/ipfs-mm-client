package io.nbs.ipfs.mm.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright © 2015-2020 NBSChain Holdings Limited.
 * All rights reserved.
 *
 * @project ipfs-mm
 * <p>
 * Author   : lanbery
 * Created  : 2018/10/15
 */
public final class AppPropsUtil {

    private static final String PROPS_FILE_PATH = "/conf/dapp-conf.properties";
    private static final Logger logger = LoggerFactory.getLogger(AppPropsUtil.class);
    private static final Pattern PATTERN = Pattern.compile("\\$\\{([^\\}]+)\\}");

    private static Properties props = new Properties();
    static {
        loadProps();
    }

    /**
     * @author      : lanbery
     * @Datetime    : 2018/10/15
     * @Description  :
     *
     */
    private static Properties loadProps(){
        Reader reader = null;
        InputStream is = null;
        try{
            String file = System.getProperty("user.dir") + PROPS_FILE_PATH;
            is = new BufferedInputStream(new FileInputStream(file));
            reader = new InputStreamReader(is,"utf-8");
            props.load(reader);
        }catch (IOException e){
            logger.error("load dapp properties error!",e.getCause());
        }finally {
            if(null != reader){
                try{
                    reader.close();
                }catch (IOException ioe){
                    logger.error("reader close error!",ioe.getCause());
                }
            }
        }
        return props;
    }
    /**
     * @author      : lanbery
     * @Datetime    : 2018/10/16
     * @Description  :
     * 
     */
    public static Properties reloadProps(){
        return loadProps();
    }

    public static Properties getAppProps(){
        return props;
    }

    public static String getProperty(String key){
        if(!props.containsKey(key))return null;
        String vlaue = props.getProperty(key);
        Matcher matcher = PATTERN.matcher(vlaue);
        StringBuffer buf = new StringBuffer();
        while (matcher.find()){
            String matcherKey = matcher.group(1);
            String matcherValue = props.getProperty(matcherKey);
            if(matcherValue != null){
                matcher.appendReplacement(buf,matcherValue);
            }

        }
        matcher.appendTail(buf);
        return buf.toString();
    }

    /**
     * @author      : lanbery
     * @Datetime    : 2018/10/15
     * @Description  :
     * 会保存文件
     */
    public static boolean setProperty(String key,String value,String comments) throws IOException{
        props.setProperty(key,value);
        if(comments==null)comments = "set property : " + key + " = " + value;
        return writeProps(comments);
    }

    public static void setProperty(String key,String value) {
        props.setProperty(key,value);
    }

    /**
     * @author      : lanbery
     * @Datetime    : 2018/10/15
     * @Description  :存储配置
     *
     */
    public static boolean saveConfig(String comment) throws IOException {
        return writeProps(comment);
    }

    private static boolean writeProps(String comment) throws IOException {
        String file = System.getProperty("user.dir") + PROPS_FILE_PATH;
        OutputStream os = null;
        try{
            os = new FileOutputStream(file);
            props.store(os,comment);
        }catch (IOException e){
            logger.error("write props config file error. file="+file,e.getCause());
            throw e;
        }finally {
            try{
                if(os!=null){
                    os.close();
                }
            }catch (IOException ioe){
                logger.warn("write close error.",ioe.getCause());
            }
        }
        return true;
    }

    /**
     * @author      : lanbery
     * @Datetime    : 2018/10/15
     * @Description  :
     * 
     */
    public static Properties loadExtProps(String file) throws Exception {
        Properties extProps = null;
        Reader reader = null;
        InputStream is;
        try{
            extProps = new Properties();
            is = new BufferedInputStream(new FileInputStream(file));
            reader = new InputStreamReader(is,"utf-8");
            extProps.load(reader);
        }catch (IOException e){
            logger.error("load properties error! {}",file);
            throw new Exception(e.getCause());
        }finally {
            if(null != reader){
                try{
                    reader.close();
                }catch (IOException ioe){
                    logger.error("reader close error!",ioe.getCause());
                }
            }
        }
        return extProps;
    }
}
