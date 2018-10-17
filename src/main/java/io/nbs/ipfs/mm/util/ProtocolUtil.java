package io.nbs.ipfs.mm.util;

import net.nbsio.ipfs.exceptions.IllegalFormatException;
import net.nbsio.ipfs.exceptions.NullArgumentException;

/**
 * Copyright © 2015-2020 NBSChain Holdings Limited.
 * All rights reserved.
 *
 * @project ipfs-mm
 * 协议工具类
 * Author   : lanbery
 * Created  : 2018/10/17
 */
public class ProtocolUtil {

    /**
     * @author      : lanbery
     * @Datetime    : 2018/10/17
     * @Description  :
     * /ip4/host/tcp/port
     */
    public static String spliceIPFSAddressAPI(String host,String port) throws NullArgumentException, IllegalFormatException {
        if(host==null||port==null)throw new NullArgumentException("参数不能为null");
        if(!RegexUtils.checkIPv4Address(host))throw new IllegalFormatException("Host 参数不正确,请输入ipv4 地址.");
        if(!RegexUtils.checkPort(port))throw new IllegalFormatException("Port 参数不正确.[80~65535]");
        StringBuffer buf = new StringBuffer();
        buf.append("/ip4/").append(host).append("/tcp/").append(port);
        return buf.toString();
    }

    /**
     * @author      : lanbery
     * @Datetime    : 2018/10/17
     * @Description  :
     * http://host:port
     */
    public static String spliceIPFSGatewayURL(String host,String port,String protocol) throws IllegalFormatException, NullArgumentException {
        if(host==null||port==null)throw new NullArgumentException("参数不能为null");
        if(!RegexUtils.checkIPv4Address(host))throw new IllegalFormatException("Host 参数不正确,请输入ipv4 地址.");
        if(!RegexUtils.checkPort(port))throw new IllegalFormatException("Port 参数不正确.[80~65535]");
        if(protocol==null || !protocol.equals("https"))protocol = "http";
        StringBuffer buf = new StringBuffer();
        buf.append(protocol).append("://").append(host).append(":").append(port);
        return buf.toString();
    }


}
