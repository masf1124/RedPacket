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

    /**
     * 新增版本号判断
     * 在扣减红包的同时，版本号增加1
     * @param redPacketId
     * @param userId
     * @return
     */
     int grabRedPacketForVersion(Long redPacketId, Long userId);

    /**
     * 通过Redis实现抢红包
     * @param redPacketId 红包编号
     * @param userId 用户编号
     * @return
     * 0 没有库存失败
     * 1 成功-且不是最后一个红包
     * 2 成功，是最后一个红包
     */
     Long grabRedPacketByRedis(Long redPacketId,Long userId);
}
