package com.masf.redpacket.service;

/**
 * @program: com.masf.redpacket.service
 * @author: mashifei
 * @create: 2019-06-05-15
 */
public interface UserRedPacketService {

    /**
     * 保存抢红包信息
     * @param redPacketId
     * @param userId
     * @return
     */
    int grabRedPacket(Long redPacketId,Long userId);
}
