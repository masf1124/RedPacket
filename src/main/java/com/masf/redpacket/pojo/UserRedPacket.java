package com.masf.redpacket.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @program: com.masf.redpacket.pojo
 * @author: mashifei
 * @create: 2019-06-05-14
 */
@Getter
@Setter
public class UserRedPacket implements Serializable {

    private Long id;
    private Long redPacketId;
    private Long userId;
    private Double amount;
    private Timestamp grabTime;
    private String note;
}
