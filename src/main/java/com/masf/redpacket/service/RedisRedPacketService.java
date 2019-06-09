package com.masf.redpacket.service;

/**
 * @program: com.masf.redpacket.service
 * @author: mashifei
 * @create: 2019-06-09-18
 */
public interface RedisRedPacketService {

    /**
     * 保存Redis抢红包的列表
     * @param redPacketId --抢红包的金额
     * @param unitAmount -- 红包金额
     */
    void saveUserRedPacketByRedis(Long redPacketId, Double unitAmount);
}
