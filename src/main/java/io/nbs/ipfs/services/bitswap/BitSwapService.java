package io.nbs.ipfs.services.bitswap;

import com.alibaba.fastjson.JSON;
import net.nbsio.ipfs.beans.ResData;
import net.nbsio.ipfs.beans.bw.BitSwap;
import net.nbsio.ipfs.beans.repo.RepoStat;
import net.nbsio.ipfs.helper.OkHttpHelper;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @Package : io.ipfs.api.bitswap
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/10-22:59
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class BitSwapService {
    private static final Logger logger = LoggerFactory.getLogger(BitSwapService.class);
    private BitSwapService() {

    }

    public static BitSwapService getInstance(){
        return BitSwapServiceHolder.bitSwapService;
    }

    private static class BitSwapServiceHolder{
        protected static BitSwapService bitSwapService = new BitSwapService();
    }

    /**
     *
     * @return
     */
    public ResData<RepoStat> getRepoStat(OkHttpHelper helper){
        if(helper==null)return new ResData<>(1,"no baseUrl");
                //getApiUrl("repo/stat",null);
        ResData<RepoStat> resData = null;
        Response response = null;
        try {
            response =  helper.get("repo/stat");
            if(response.code()==200){
                String resJsonStr = response.body().string();
                //logger.info(resJsonStr);
                RepoStat repoStat = JSON.parseObject(resJsonStr,RepoStat.class);
                resData = new ResData(repoStat);
            }else {
                resData = new ResData<>(1,"no get data");
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            resData = new ResData<>(1,e.getMessage());
        }
        return resData;
    }

    /**
     * bitswap/stat
     * @return
     */
    public ResData<BitSwap> getBitSwapStat(OkHttpHelper helper){
        if(helper==null)return new ResData<>(1,"no baseUrl");
        String apiURL ="bitswap/stat";
        ResData<BitSwap> resData = null;
        Response response = null;
        try {
            response =  helper.get(apiURL);
            if(response.code()==200){
                String resJsonStr = response.body().string();
                //logger.info(resJsonStr);
                BitSwap bitSwap = JSON.parseObject(resJsonStr,BitSwap.class);
                resData = new ResData<>(bitSwap);
            }else {
                resData = new ResData<>(1,"no get data");
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            resData = new ResData<>(1,e.getMessage());
        }
        return resData;
    }

}
