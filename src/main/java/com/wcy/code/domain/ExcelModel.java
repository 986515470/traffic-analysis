package com.wcy.code.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author Wcy
 * @Date 2022/5/27 15:09
 */
@Data
public class ExcelModel {

    /**
     * trip_id
     * 行程id
     * arrival_time
     * 到达站点时间
     * departure_time
     * 出发站点时间
     * stop_id
     * 停靠站台id
     * stop_sequence
     * 行程站点序号
     * route_id
     * 路线id 通常包含多个不同的行程
     * shape_dist_traveled
     * NA
     * stop_name
     * 站台名称
     * parent_station
     * 车站id
     * service_id
     * 服务id
     * trip_headsign
     * 行程的全名
     * trip_short_name
     * NA
     * route_short_name
     * 路线简写
     * route_long_name
     * 路线全名
     * monday  tuesday	wednesday	thursday	friday	saturday	sunday
     * // 1为有调度信息 0为没有调度信息
     * start_date
     * 开始日期
     * end_date
     * 结束日期
     * index_number
     * 索引号
     * fetch_time
     * 抓取时间
     * arrival_delay
     * 到达延迟
     * actual_arrival_time
     * 实际到达时间,默认为‘0’
     * departure_delay
     * 出发延迟
     * actual_departure_time
     * 实际出发时间,默认为‘0’
     * date
     * 日期
     */
    @ExcelProperty(value = "行程", index = 0)
    private String trip_id;

    @ExcelProperty(value = "到达站点时间", index = 1)
    private String arrival_time;

    @ExcelProperty(value = "出发站点时间", index = 2)
    private String departure_time;

    @ExcelProperty(value = "停靠站台序号", index = 3)
    private String stop_id;

    @ExcelProperty(value = "行程站点序号", index = 4)
    private String stop_sequence;

    @ExcelProperty(value = "路线id", index = 5)
    private String route_id;

    @ExcelProperty(value = "", index = 6)
    private String shape_dist_traveled;

    @ExcelProperty(value = "站台名称", index = 7)
    private String stop_name;

    @ExcelProperty(value = "车站id", index = 8)
    private String parent_station;

    @ExcelProperty(value = "服务id", index = 9)
    private String service_id;

    @ExcelProperty(value = "行程全名", index = 10)
    private String trip_headsign;

    @ExcelProperty(value = "", index = 11)
    private String trip_short_name;

    @ExcelProperty(value = "路线简写", index = 12)
    private String route_short_name;

    @ExcelProperty(value = "路线全名", index = 13)
    private String route_long_name;

    @ExcelProperty(value = "星期一", index = 14)
    private Integer monday;
    @ExcelProperty(value = "星期二", index = 15)
    private Integer tuesday;
    @ExcelProperty(value = "星期三", index = 16)
    private Integer wednesday;
    @ExcelProperty(value = "星期四", index = 17)
    private Integer thursday;
    @ExcelProperty(value = "星期五", index = 18)
    private Integer friday;
    @ExcelProperty(value = "星期六", index = 19)
    private Integer saturday;
    @ExcelProperty(value = "星期日", index = 20)
    private Integer sunday;

    @ExcelProperty(value = "开始日期", index = 21)
    private String start_date;
    @ExcelProperty(value = "结束日期", index = 22)
    private String end_date;

    @ExcelProperty(value = "索引号", index = 23)
    private String index_number;

    @ExcelProperty(value = "抓取时间", index = 24)
    private String fetch_time;

    @ExcelProperty(value = "到达延迟", index = 25)
    private String arrival_delay;

    @ExcelProperty(value = "实际到达时间", index = 26)
    private String actual_arrival_time;

    @ExcelProperty(value = "出发延迟", index = 27)
    private String departure_delay;

    @ExcelProperty(value = "实际出发时间", index = 28)
    private String actual_departure_time;

    @ExcelProperty(value = "日期", index = 29)
    private String date;


}
