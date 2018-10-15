package io.nbs.ipfs.mm.cnsts;

/**
 * Copyright © 2015-2020 NBSChain Holdings Limited.
 * All rights reserved.
 *
 * @project ipfs-mm
 *
 * Author   : lanbery
 * Created  : 2018/10/15
 */
public class DappCnsts {

    /**
     * @author      : lanbery
     * @Datetime    : 2018/10/15
     * @Description  :
     * 下载根路径
     */
    public static final String DAPP_DOWNLOAD_ROOTPATH_KEY = "dapp.download.rootpath.key";

    /**
     * 客户端运行产生文件根目录
     */
    public static final String NBS_ROOT = ".nbs";
    /**
     * 临时文件目录
     */
    public static final String TEMP_FILE = "tmp";

    /**
     * 图标根
     */
    public static String TOOL_ICON_PATH = "/icons/tools/";




    /**
     * comments :
     * 构造路径
     */
    public static String consturactPath(String... args){
        if(args==null||args.length==0)return "";
        StringBuilder sb = new StringBuilder();
        String fileSeparator = System.getProperty("file.separator");

        int len = args.length;
        for(int i=0;i<len;i++ ){
            if(i==(len-1)){
                sb.append(args[i]);
            }else {
                sb.append(args[i]).append(fileSeparator);
            }
        }
        return sb.toString();
    }

}
