package com.masf.redpacket.mapper;

import com.masf.redpacket.pojo.RedPacket;
import org.apache.ibatis.annotations.Param;
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
    int decreaseRedPacketForVersion(@Param(value="id") Long id,
                                    @Param(value="version") Long version);
}
