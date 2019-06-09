package com.masf.redpacket.mapper;

import com.masf.redpacket.pojo.UserRedPacket;
import org.springframework.stereotype.Repository;

/**
 * @program: com.masf.redpacket.dao
 * @author: mashifei
 * @create: 2019-06-05-15
 */

@Repository
public interface UserRedPacketMapper {

    /**
     * 插入抢红包信息
     * @param userRedpacket
     * @return
     */
    int grabRedPacket(UserRedPacket userRedpacket);
}
