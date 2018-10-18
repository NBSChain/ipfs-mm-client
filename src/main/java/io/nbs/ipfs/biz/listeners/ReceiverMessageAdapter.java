package io.nbs.ipfs.biz.listeners;

import io.nbs.ipfs.biz.data.entity.PeerMessageEntity;
import io.nbs.ipfs.biz.services.IpfsMessageSender;
import io.nbs.ipfs.biz.services.PeerMessageService;
import io.nbs.ipfs.biz.utils.DataBaseUtil;
import io.nbs.ipfs.mm.Launcher;
import io.nbs.ipfs.mm.ui.components.NbsListView;
import io.nbs.ipfs.mm.ui.frames.MainFrame;
import io.nbs.ipfs.mm.ui.panels.im.IMPeersPanel;
import net.nbsio.ipfs.beans.MessageItem;
import net.nbsio.ipfs.beans.OnlineMessage;
import net.nbsio.ipfs.beans.PeerInfo;
import net.nbsio.ipfs.common.UUIDGenerator;
import net.nbsio.ipfs.vo.ContactsItem;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Package : io.nbs.client.adapter
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/4-2:51
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ReceiverMessageAdapter implements IPFSSubscribeListener {
    private static Logger logger = LoggerFactory.getLogger(ReceiverMessageAdapter.class);

    private List<MessageItem> items;
    private NbsListView listView;

    private PeerMessageService peerMessageService;

    public ReceiverMessageAdapter(List<MessageItem> items, NbsListView listView) {
        this.items = items;
        this.listView = listView;
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        peerMessageService = new PeerMessageService(sqlSession);
    }

    @Override
    public void notifyRecvMessage(MessageItem item) {
        PeerInfo peerInfo = Launcher.getContext().getCurrentPeer();

        if(item.getFrom().equals(peerInfo.getFrom())){
            // 未来忽略自己发的
        }else {
            item.setMessageType(MessageItem.LEFT_TEXT);
            item = findInPeerList(item);
            items.add(item);
            int postion = items.size()<=0 ? 0:items.size()-1;
            listView.notifyItemInserted(postion,true);
            listView.setAutoScrollToBottom();
            //
           saveWorldMessage(item);
            /**
             * AutoReplay enabled
             */
           if(Launcher.LaucherConfMapUtil.getValue("nbs.client.im.replay-open","false").equals("true")){
               autoRepaly(item.getSenderUsername());
           }
           //
        }
    }

    /**
     *
     * @param message
     */
    @Override
    public void notifyOnlineMessage(OnlineMessage message) {
        MessageItem item = new MessageItem();
        item.setId(UUIDGenerator.getUUID());
        item.setMessageType(MessageItem.SYSTEM_MESSAGE);
        item.setSenderUsername(message.getNick());
        item.setSenderId(message.getId());
        String locations = StringUtils.isNotBlank(message.getLocations()) ? message.getLocations() : "";
        String content = StringUtils.join("用户",message.getNick(),locations);
        item.setMessageContent(content);
        item.setTimestamp(message.getTs());
        items.add(item);
        int postion = items.size()>0 ? items.size()-1 : 0;
        listView.notifyItemInserted(postion,true);
        listView.setAutoScrollToBottom();
    }

    private void saveWorldMessage(MessageItem item){
        if(item==null)return;
        PeerMessageEntity entity = new PeerMessageEntity();
        entity.setId(item.getId());
        entity.setRecvtime(item.getTimestamp());
        entity.setSeqno(item.getSeqno());
        entity.setFromid(item.getFrom());
        entity.setFromhash(item.getSenderId());
        entity.setRoomid(item.getRoomId());
        entity.setMsg(item.getMessageContent());
        entity.setMtype(item.getMessageType());
        if(item.getMessageType()<0){
            entity.setSelf(1);
        }else {
            entity.setSelf(0);
        }
        peerMessageService.insert(entity);
    }

    /**
     *
     * @param name
     */
    private void autoRepaly(final String name){
        IpfsMessageSender sender = MainFrame.getContext().getMessageSender();
        String text = "你好"+name+"收到！";
        try {
            sender.ipfsSendMessage(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param item
     * @return
     */
    private MessageItem findInPeerList(MessageItem item){
        List<ContactsItem> contactsItems = IMPeersPanel.getContext().getContactItems();

        if(contactsItems==null||contactsItems.size()==0)return item;
        for(ContactsItem contactsItem : contactsItems ){
            if(contactsItem.getFormid()!=null&&contactsItem.getFormid().equals(item.getFrom())){
                item.setSenderId(contactsItem.getId());
                item.setSenderUsername(contactsItem.getName());
                item.setAvatar(contactsItem.getAvatar());
                return item;
            }
        }
        item.setSenderUsername("unkonw_peerid");
        return item;
    }
}
