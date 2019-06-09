package com.masf.redpacket.service;

import com.masf.redpacket.pojo.RedPacket;

/**
 * @program: com.masf.redpacket.service
 * @author: mashifei
 * @create: 2019-06-05-15
 */
public interface RedPacketService {

    /**
     * 获取红包信息
     * @param id
     * @return
     */
    RedPacket getRedPacket(Long id);

    /**
     * 扣减红包
     * @param id
     * @return
     */
    int decreaseRedPacket(Long id);

    /**
     * 使用for update语句加锁（行锁）
     * @param id
     * @return
     */
    RedPacket getRedPacketForUpdate(Long id);

    /**
     * 通过版本号扣减抢红包，没更新一次，版本号新增1
     * 其次增减对版本号的判断
     * @param id
     * @param version
     * @return
     */
    int decreaseRedPacketForVersion(Long id,Long version);
}
