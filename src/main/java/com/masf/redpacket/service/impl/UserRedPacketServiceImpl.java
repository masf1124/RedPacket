package com.masf.redpacket.service.impl;

import com.masf.redpacket.mapper.RedPacketMapper;
import com.masf.redpacket.mapper.UserRedPacketMapper;
import com.masf.redpacket.pojo.RedPacket;
import com.masf.redpacket.pojo.UserRedPacket;
import com.masf.redpacket.service.RedisRedPacketService;
import com.masf.redpacket.service.UserRedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

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

    @Autowired
    private RedisTemplate redisTemplate = null;

    @Autowired
    private RedisRedPacketService redisRedPacketService;

    //失败
    private static final int FAILED = 0;

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
        return FAILED;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int grabRedPacketForVersion(Long redPacketId, Long userId) {
        //记录开始时间
        long start = System.currentTimeMillis();
        //增加重入机制
        while(true){
            //获取本次循环时间
            long end = System.currentTimeMillis();
            if(end-start>100){
                return FAILED;
            }

            //获取红包信息
            RedPacket redPacket = redPacketMapper.getRedPacket(redPacketId);

            //当前小红包库存小于0
            if(redPacket.getStock()>0){
                //再次传入线程保存的version旧值给SQL判断，是否有其他线程修改过数据
                int update = redPacketMapper.decreaseRedPacketForVersion(redPacketId,redPacket.getVersion());

                //如果没有数据更新，则说明其他线程已经修改过数据，本次抢红包失败
                if(update==0){
                    //System.out.println("failed!");
                    continue;
                }
                //生成抢红包信息
                UserRedPacket userRedPacket = new UserRedPacket();
                userRedPacket.setRedPacketId(redPacketId);
                userRedPacket.setUserId(userId);
                userRedPacket.setAmount(redPacket.getUnitAmount());
                userRedPacket.setNote("抢红包"+redPacketId);
                //System.out.println("success!");
                //插入抢红包信息
                int result = userRedPacketMapper.grabRedPacket(userRedPacket);
                return result;
            }
            //失败返回
            return FAILED;
        }
    }

    //Lua 脚本
    String script = "local listKey = 'red_packet_list_'..KEYS[1] \n"
                    +"local redPacket = 'red_packet_'..KEYS[1] \n"
                    +"if stock <=0 then return 0 end \n"
                    +"stock = stock -1 \n"
                    +"redis.call('hset',redPacket,'stock',toString(stock)) \n"
                    +"redis.call('rpush',listKey,ARGV[1]) \n"
                    +"if stock == 0 then return 2 end \n"
                    +"return 1 \n";

    //在缓存Lua脚本后，使用该变量保存Redis返回后的32位的SHA1编码，使用它去执行缓存的Lua脚本
    String sha1 = null;

    @Override
    public Long grabRedPacketByRedis(Long redPacketId, Long userId) {
        //当前抢红包用户和日期信息
        String args = userId +"-"+System.currentTimeMillis();
        Long result = null;

        //获取底层Redis操作对象
        Jedis jedis = (Jedis)redisTemplate.getConnectionFactory()
                                    .getConnection()
                                    .getNativeConnection();

        try{
            //如果脚本没有加载过，那么进行加载，这样就可以返回一个sha1编码
            if(sha1 == null){
                sha1 = jedis.scriptLoad(script);
            }
            //执行脚本，返回结果
            Object res = jedis.evalsha(sha1,1,redPacketId+"",args);
            result = (Long)res;

            //返回为2时，为最后一个红包，此时将抢红包信息通过异步保存到数据库中
            if(result ==2 ){
                //获取单个小金额
                String unitAmountStr = jedis.hget("red_packet_"+redPacketId,"unit_amount");
                //触发保存数据库操作
                Double unitAmount = Double.parseDouble(unitAmountStr);
                System.err.println("thread_name="+Thread.currentThread().getName());

                redisRedPacketService.saveUserRedPacketByRedis(redPacketId,unitAmount);
            }
        }finally {
           //确保Redis顺利关闭
            if(jedis!=null && jedis.isConnected()){
                jedis.close();
            }
        }
        return result;
    }
}
