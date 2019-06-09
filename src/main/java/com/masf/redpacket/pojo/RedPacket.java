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
@Setter
@Getter
public class RedPacket implements Serializable {

    private Long id;
    private Long userId;
    private Double amount;
    private Timestamp sendDate;
    private Integer total;
    private Double unitAmount;
    private Integer stock;
    private Integer version;
    private String note;
}
