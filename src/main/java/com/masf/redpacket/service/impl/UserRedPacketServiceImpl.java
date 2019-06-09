package com.masf.redpacket.service.impl;

import com.masf.redpacket.mapper.RedPacketMapper;
import com.masf.redpacket.mapper.UserRedPacketMapper;
import com.masf.redpacket.pojo.RedPacket;
import com.masf.redpacket.pojo.UserRedPacket;
import com.masf.redpacket.service.UserRedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: com.masf.redpacket.service.impl
 * @author: mashifei
 * @create: 2019-06-05-15
 */
@Service
public class UserRedPacketServiceImpl implements UserRedPacketService {

    @Autowired
    private UserRedPacketMapper userRedPacketMapper;

    @Autowired
    private RedPacketMapper redPacketMapper;

    //失败
    private static final int FIELD = 0;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int grabRedPacket(Long redPacketId, Long userId) {

        //获取红包信息
        RedPacket redPacket = redPacketMapper.getRedPacketForUpdate(redPacketId);

        //当前小红包库存小于0
        if(redPacket.getStock()>0){
            redPacketMapper.decreaseRedPacket(redPacketId);

            //生成抢红包信息
            UserRedPacket userRedPacket = new UserRedPacket();
            userRedPacket.setRedPacketId(redPacketId);
            userRedPacket.setUserId(userId);
            userRedPacket.setAmount(redPacket.getUnitAmount());
            userRedPacket.setNote("抢红包"+redPacketId);

            //插入抢红包信息
            int result = userRedPacketMapper.grabRedPacket(userRedPacket);
            return result;
        }
        //失败返回
        return FIELD;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int grabRedPacketForVersion(Long redPacketId, Long userId) {

        //获取红包信息
        RedPacket redPacket = redPacketMapper.getRedPacket(redPacketId);

        //当前小红包库存小于0
        if(redPacket.getStock()>0){
            //再次传入线程保存的version旧值给SQL判断，是否有其他线程修改过数据
            int update = redPacketMapper.decreaseRedPacketForVersion(redPacketId,redPacket.getVersion());

            //如果没有数据更新，则说明其他线程已经修改过数据，本次抢红包失败
            if(update==0){
                //System.out.println("failed!");
                return FIELD;
            }
            //生成抢红包信息
            UserRedPacket userRedPacket = new UserRedPacket();
            userRedPacket.setRedPacketId(redPacketId);
            userRedPacket.setUserId(userId);
            userRedPacket.setAmount(redPacket.getUnitAmount());
            userRedPacket.setNote("抢红包"+redPacketId);
            System.out.println("success!");
            //插入抢红包信息
            int result = userRedPacketMapper.grabRedPacket(userRedPacket);
            return result;
        }
        //失败返回
        return FIELD;
    }
}
