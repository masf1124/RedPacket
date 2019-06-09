package com.masf.redpacket.service.impl;

import com.masf.redpacket.mapper.RedPacketMapper;
import com.masf.redpacket.pojo.RedPacket;
import com.masf.redpacket.service.RedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: com.masf.redpacket.service.impl
 * @author: mashifei
 * @create: 2019-06-05-15
 *
 * isolation = Isolation.READ_COMMITTED,
 * 采用读/写提交的隔离级别，之所以不采用更高的隔离级别，主要是提高数据库的并发能力
 *
 * propagation = Propagation.REQUIRED
 * 传播行为Propagation.REQUIRED，没有事务则会创建事务，有事务则沿用当前事务
 */

@Service
public class RedPacketServiceImpl implements RedPacketService {

    @Autowired
    private RedPacketMapper redPacketMapper;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public RedPacket getRedPacket(Long id) {
        return redPacketMapper.getRedPacket(id);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int decreaseRedPacket(Long id) {
        return redPacketMapper.decreaseRedPacket(id);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public RedPacket getRedPacketForUpdate(Long id) {
        return redPacketMapper.getRedPacketForUpdate(id);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int decreaseRedPacketForVersion(Long id, Long version) {
        return redPacketMapper.decreaseRedPacketForVersion(id,version);
    }
}
