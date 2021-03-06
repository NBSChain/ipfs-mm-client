package io.nbs.ipfs.biz.services;

import io.nbs.ipfs.biz.data.dao.PeerContactsDao;
import io.nbs.ipfs.biz.data.entity.PeerContactsEntity;
import org.apache.ibatis.session.SqlSession;

/**
 * @Package : com.nbs.biz.service
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/30-14:12
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class PeerContactsService extends BasicService<PeerContactsDao, PeerContactsEntity> {
    public PeerContactsService(SqlSession sqlSession) {
        dao = new PeerContactsDao(sqlSession);
        setDao(dao);
    }
}
