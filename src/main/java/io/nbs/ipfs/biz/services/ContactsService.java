package io.nbs.ipfs.biz.services;

import io.nbs.ipfs.biz.data.dao.ContactsDao;
import io.nbs.ipfs.biz.data.entity.ContactsEntity;
import org.apache.ibatis.session.SqlSession;
import java.util.List;

/**
 * @Package : com.nbs.biz.service
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/27-17:42
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ContactsService  extends BasicService<ContactsDao, ContactsEntity> {
    /**
     *
     * @param session
     */
    public ContactsService(SqlSession session) {
        dao = new ContactsDao(session);
        setDao(dao);
    }

    public int insertOrUpdate(ContactsEntity entity){
        if(exist(entity.getId())){
            return update(entity);
        }else {
            return insert(entity);
        }
    }

    public List<ContactsEntity> getAllExcludeSelf(String peerid){
        return dao.findAllExcludeId(peerid);
    }
}
