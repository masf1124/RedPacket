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
}
