package com.wcy.code.domain;

import lombok.Data;

/**
 * @author Wcy
 * @Date 2022/5/31 20:21
 */
@Data
public class ProbabilityModel {

    private String routeId;

    private int count;//记录次数

    int arrive_delay_count; // 到达延误次数
    int actual_arrive_delay_count; // 实际到达延误次数
    int departure_delay_count; // 出发延误次数
    int actual_departure_delay_count; // 实际出发延误次数

    /**
     * 延误几率
     */
    double arrive_delay_probability; // 到达延误几率
    double actual_arrive_delay_probability; // 实际到达延误几率
    double departure_delay_probability; // 出发延误几率
    double actual_departure_delay_probability; // 实际出发延误几率

    /**
     * 延误几率之和
     */
    double probability; // 几率之和

}
