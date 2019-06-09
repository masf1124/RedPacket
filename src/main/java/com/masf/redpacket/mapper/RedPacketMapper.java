package com.masf.redpacket.mapper;

import com.masf.redpacket.pojo.RedPacket;
import org.springframework.stereotype.Repository;

/**
 * @program: com.masf.redpacket.dao
 * @author: mashifei
 * @create: 2019-06-05-15
 */

@Repository
public interface RedPacketMapper {

    /**
     * 根据id获取红包信息
     * @param id
     * @return
     */
    RedPacket getRedPacket(Long id);

    /**
     * 根据ID扣减红包
     * @param id
     * @return
     */
    int decreaseRedPacket(Long id);
}
