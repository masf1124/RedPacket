package com.masf.redpacket.controller;

import com.masf.redpacket.service.UserRedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: com.masf.redpacket.controller
 * @author: mashifei
 * @create: 2019-06-05-18
 */

@Controller
@RequestMapping("/userRedPacket")
public class UserRedPacketController {

    @Autowired
    private UserRedPacketService userRedPacketService;

    @RequestMapping("/grabRedPacket")
    @ResponseBody
    public Map<String,Object> grabRedPacket(Long redPacketId,Long userId){
        //抢红包
        int result = userRedPacketService.grabRedPacket(redPacketId,userId);
        //System.out.println("抢红包");
        Map<String,Object> resMap = new HashMap<>();
        boolean flag = result>0;
        resMap.put("success",flag);
        resMap.put("message",flag?"抢红包成功！":"抢红包失败！");
        return resMap;
    }

    @RequestMapping("/grabRedPacketForVersion")
    @ResponseBody
    public Map<String,Object> grabRedPacketForVersion(Long redPacketId,Long userId){
        //抢红包
        int result = userRedPacketService.grabRedPacketForVersion(redPacketId,userId);
        //System.out.println("抢红包");
        Map<String,Object> resMap = new HashMap<>();
        boolean flag = result>0;
        resMap.put("success",flag);
        resMap.put("message",flag?"抢红包成功！":"抢红包失败！");
        return resMap;
    }

    @RequestMapping("/grabRedPacketByRedis")
    @ResponseBody
    public Map<String,Object> grabRedPacketByRedis(Long redPacketId,Long userId){
        //抢红包
        //System.out.println("抢红包");
        Map<String,Object> resMap = new HashMap<>();
        Long result = userRedPacketService.grabRedPacketByRedis(redPacketId,userId);
        boolean flag = result>0;
        resMap.put("success",flag);
        resMap.put("message",flag?"抢红包成功！":"抢红包失败！");
        return resMap;
    }
}
