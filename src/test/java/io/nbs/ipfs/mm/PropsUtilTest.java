package io.nbs.ipfs.mm;

import io.nbs.ipfs.mm.util.AppPropsUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Copyright © 2015-2020 NBSChain Holdings Limited.
 * All rights reserved.
 *
 * @project ipfs-mm
 * <p>
 * Author   : lanbery
 * Created  : 2018/10/15
 */
public class PropsUtilTest {

    HashMap<String,String> m = new HashMap<>();
    public static void main(String[] args) {

        PropsUtilTest test = new PropsUtilTest();
        test.ttProps();
    }

    public PropsUtilTest() {

    }

    public void ttProps(){
        Properties props = AppPropsUtil.getAppProps();
        listShowKeyValue(props);
        try {
            AppPropsUtil.setProperty("nbs","NBS");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listShowKeyValue(Properties props){
        if(props==null)return;
        Iterator<Map.Entry<Object,Object>> it = props.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<Object,Object> entry = it.next();

            System.out.println("KEY="+entry.getKey()+";Value="+AppPropsUtil.getProperty(entry.getKey().toString()));
        }
    }
}
