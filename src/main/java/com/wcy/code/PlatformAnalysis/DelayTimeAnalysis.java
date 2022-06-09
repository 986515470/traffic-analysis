package com.wcy.code.PlatformAnalysis;

import com.wcy.code.domain.ExcelModel;
import com.wcy.code.domain.MapModel;
import com.wcy.code.domain.ProbabilityModel;
import com.wcy.code.utils.DateUtils;
import com.wcy.code.utils.HashMapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author Wcy
 * @Date 2022/5/29 15:35
 */
@Service
public class DelayTimeAnalysis {

    /**
     * “route_id”，"departure_time"， "fetch_time", "departure_delay", "actual_departure_time", "arrival_delay", "actual_arrival_time"
     * 路线id,      出发时间，          抓取时间,        出发延迟,              实际出发时间,            到达延迟,            实际到达时间,
     * <p>
     * 这里主要分析路线与时间延迟的关系
     * 先抓取数据，然后计算route_id与时间差的关系
     * 然后计算时间差与延迟的关系
     */

    @Autowired
    private ExcelOptionsService excelOptionsService;


    /**
     * 将"fetch_time"十位时间戳转换为时间 获取出发时间与到达时间的平均时间差
     * 舍弃
     */
    public void setTime() {
        //将十位时间戳转换为时间
        List<ExcelModel> list = excelOptionsService.getListener().getList();
        if ((list.get(0).getFetch_time().length() < 11)) { //表示没有对数据进行初始化 时间戳为10位
            for (ExcelModel excelModel : list) {
                excelModel.setFetch_time(DateUtils.getTime(excelModel.getFetch_time()));
//                if (!Objects.equals(excelModel.getActual_arrival_time(), "0"))
//                    excelModel.setActual_arrival_time( DateUtils.getTime(Long.parseLong(excelModel.getActual_arrival_time())));
//                if (!Objects.equals(excelModel.getActual_departure_time(), "0"))
//                    excelModel.setActual_departure_time( DateUtils.getTime(Long.parseLong(excelModel.getActual_departure_time())));
            }
        }
    }

    /**
     * “route_id”，"arrive_time", "departure_time"
     * 路线id,      到达时间，         出发时间，
     * key:route_id, value:avg_time(到达时间-出发时间)
     * 计算出发时间与到达时间的平均时间差
     */
    public HashMap<String, String> getAvgTime() {

        HashMap<String, String> map = new HashMap<>();
        List<ExcelModel> list = excelOptionsService.getListener().getList();
        for (ExcelModel excelModel : list) {
            String arrivalTime = excelModel.getArrival_time();
            String departureTime = excelModel.getDeparture_time();
            String waitTime = DateUtils.getTime(DateUtils.getTimeDifferenceHour(arrivalTime, departureTime));
            if (map.containsKey(excelModel.getRoute_id())) {
                String avgTime = DateUtils.getAvgTime(map.get(excelModel.getRoute_id()), waitTime);
                map.put(excelModel.getRoute_id(), avgTime);
            } else {
                map.put(excelModel.getRoute_id(), waitTime);
            }
        }
        System.out.println("此处分析:行程id/route_id与平均等待时间差(到达时间-出发时间)/(departure_time-arrive_time)的关系 :");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("行程id/route_id = 平均等待时间(到达时间-出发时间)时间最长的5个站点");
        System.out.println("--------------------------------------------------------------------------------");
        List<MapModel> MaxWaitTime = HashMapUtils.getTimeList(map,1);
        for (MapModel mapModel : MaxWaitTime) {
            System.out.println(mapModel.getKey() + ":" + mapModel.getTime());
        }
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("行程id/route_id = 平均等待时间(到达时间-出发时间)时间最短的5个站点");
        System.out.println("--------------------------------------------------------------------------------");
        List<MapModel> MinWaitTime = HashMapUtils.getTimeList(map,2);
        for (MapModel mapModel : MinWaitTime) {
            System.out.println(mapModel.getKey() + ":" + mapModel.getTime());
        }
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("route_id = avg_time(departure_time-arrive_time) (size):" + map.size());
        System.out.println("--------------------------------------------------------------------------------");
        return map;
    }


    /**
     * “stop_id(stop_name)”，"arrive_time", "departure_time"
     * 路线长名称,      到达时间，         出发时间，
     * key:stop_id(stop_name), value:avg_time(到达时间-出发时间)
     * 计算出发时间与到达时间的平均时间差
     */
    public HashMap<String, String> getAvgTimeByStopId() {

        HashMap<String, String> map = new HashMap<>();
        List<ExcelModel> list = excelOptionsService.getListener().getList();
        for (ExcelModel excelModel : list) {
            String arrivalTime = excelModel.getArrival_time();
            String departureTime = excelModel.getDeparture_time();
            String waitTime = DateUtils.getTime(DateUtils.getTimeDifferenceHour(arrivalTime, departureTime));
            if (map.containsKey(excelModel.getStop_id() + "(" + excelModel.getStop_name() + ")")) {
                String avgTime = DateUtils.getAvgTime(map.get(excelModel.getStop_id() + "(" + excelModel.getStop_name() + ")"), waitTime);
                map.put(excelModel.getStop_id() + "(" + excelModel.getStop_name() + ")", avgTime);
            } else {
                map.put(excelModel.getStop_id() + "(" + excelModel.getStop_name() + ")", waitTime);
            }
        }
        System.out.println("此处分析:站台id(站台名称)/stop_id(stop_name)与平均等待时间差(到达时间-出发时间)/(departure_time-arrive_time)的关系 :");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("站台id(站台名称)/stop_id(stop_name) = 平均等待时间(到达时间-出发时间)时间最长的5个站点");
        System.out.println("--------------------------------------------------------------------------------");
        List<MapModel> MaxMapModels = HashMapUtils.getTimeList(map, 1);
        for (MapModel mapModel : MaxMapModels) {
            System.out.println(mapModel.getKey() + " = " + mapModel.getTime());
        }
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("站台id(站台名称)/stop_id(stop_name) = 平均等待时间(到达时间-出发时间)时间最短的5个站点");
        System.out.println("--------------------------------------------------------------------------------");
        List<MapModel> MinMapModels = HashMapUtils.getTimeList(map, 2);
        for (MapModel mapModel : MinMapModels) {
            System.out.println(mapModel.getKey() + " = " + mapModel.getTime());
        }
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("stop_id(stop_name) = avg_time(departure_time-arrive_time) (size):" + map.size());
        System.out.println("--------------------------------------------------------------------------------");
        return map;
    }

    /**
     * “trip_id”, "actual_arrival_time",   "actual_departure_time"
     * 行程id,        实际到达时间,默认为‘0’ ,    实际出发时间,默认为‘0’
     * key:route_id, value:ExcelModel
     * 分析route_id 对应的 到达延迟 以及 出发延迟 情况
     * 计算平均延迟
     */
    public HashMap<String, ProbabilityModel> getDelayProbability() {
        HashMap<String, ProbabilityModel> map = new HashMap<>();
        List<ExcelModel> list = excelOptionsService.getListener().getList();
        //获取到数据
        for (ExcelModel excelModel : list) {
            String RouteId = excelModel.getRoute_id();
            if (map.containsKey(excelModel.getRoute_id())) {
                ProbabilityModel probabilityModel = getProbabilityModel(map, excelModel);
                map.put(excelModel.getRoute_id(), probabilityModel);
            } else {
                ProbabilityModel probabilityModel = getProbabilityModel(excelModel, RouteId);
                map.put(RouteId, probabilityModel);
            }
        }
        //计算几率
        for (String key : map.keySet()) {
            ProbabilityModel probabilityModel = map.get(key);
            probabilityModel.setArrive_delay_probability(Double.parseDouble(String.format("%.2f", (double) probabilityModel.getArrive_delay_count() / probabilityModel.getCount())));
            probabilityModel.setDeparture_delay_probability(Double.parseDouble(String.format("%.2f", (double) probabilityModel.getDeparture_delay_count() / probabilityModel.getCount())));
            probabilityModel.setActual_arrive_delay_probability(Double.parseDouble(String.format("%.2f", (double) probabilityModel.getActual_arrive_delay_count() / probabilityModel.getCount())));
            probabilityModel.setActual_departure_delay_probability(Double.parseDouble(String.format("%.2f", (double) probabilityModel.getActual_departure_delay_count() / probabilityModel.getCount())));
            probabilityModel.setProbability(probabilityModel.getArrive_delay_probability() + probabilityModel.getDeparture_delay_probability() + probabilityModel.getActual_arrive_delay_probability() + probabilityModel.getActual_departure_delay_probability());
        }
        //输出展示
        System.out.println("延迟几率最高的5个路线对应的 到达延迟几率 | 实际到达延迟几率 | 出发延迟几率 | 实际出发延迟几率");
        System.out.println("--------------------------------------------------------------------------------");
        List<MapModel> MaxMapModels = HashMapUtils.getProbabilityModelList(map, 1);
        for (MapModel mapModel : MaxMapModels) {
            System.out.println(mapModel.getKey() + " = \t" + mapModel.getProbabilityModel().getArrive_delay_probability() + " \t|\t " + mapModel.getProbabilityModel().getDeparture_delay_probability() + " \t|\t " + mapModel.getProbabilityModel().getActual_arrive_delay_probability() + "\t |\t " + mapModel.getProbabilityModel().getActual_departure_delay_probability());
        }
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("延迟几率最低的5个路线对应的 到达延迟几率 | 实际到达延迟几率 | 出发延迟几率 | 实际出发延迟几率");
        System.out.println("--------------------------------------------------------------------------------");
        List<MapModel> MinMapModels = HashMapUtils.getProbabilityModelList(map, 2);
        for (MapModel mapModel : MinMapModels) {
            System.out.println(mapModel.getKey() + " = \t" + mapModel.getProbabilityModel().getArrive_delay_probability() + " \t|\t " + mapModel.getProbabilityModel().getDeparture_delay_probability() + " \t|\t " + mapModel.getProbabilityModel().getActual_arrive_delay_probability() + "\t |\t " + mapModel.getProbabilityModel().getActual_departure_delay_probability());
        }
        System.out.println("--------------------------------------------------------------------------------");
        return map;
    }

    private ProbabilityModel getProbabilityModel(HashMap<String, ProbabilityModel> map, ExcelModel excelModel) {
        ProbabilityModel probabilityModel = map.get(excelModel.getRoute_id());
        //到达延迟
        if (!Objects.equals(excelModel.getArrival_delay(), "0")) {
            probabilityModel.setArrive_delay_count(probabilityModel.getArrive_delay_count() + 1);
        }
        //出发延迟
        if (!Objects.equals(excelModel.getDeparture_delay(), "0")) {
            probabilityModel.setDeparture_delay_count(probabilityModel.getDeparture_delay_count() + 1);
        }
        //实际到达延迟
        if (!Objects.equals(excelModel.getActual_arrival_time(), "0")) {
            probabilityModel.setActual_arrive_delay_count(probabilityModel.getActual_arrive_delay_count() + 1);
        }
        //实际出发延迟
        if (!Objects.equals(excelModel.getActual_departure_time(), "0")) {
            probabilityModel.setActual_departure_delay_count(probabilityModel.getActual_departure_delay_count() + 1);
        }
        probabilityModel.setCount(probabilityModel.getCount() + 1);
        return probabilityModel;
    }

    private ProbabilityModel getProbabilityModel(ExcelModel excelModel, String RouteId) {
        ProbabilityModel probabilityModel = new ProbabilityModel();
        probabilityModel.setRouteId(RouteId);
        //到达延迟
        if (!Objects.equals(excelModel.getArrival_delay(), "0")) {
            probabilityModel.setArrive_delay_count(1);
        } else {
            probabilityModel.setArrive_delay_count(0);
        }
        //出发延迟
        if (!Objects.equals(excelModel.getDeparture_delay(), "0")) {
            probabilityModel.setDeparture_delay_count(1);
        } else {
            probabilityModel.setDeparture_delay_count(0);
        }
        //实际到达延迟
        if (!Objects.equals(excelModel.getActual_arrival_time(), "0")) {
            probabilityModel.setActual_arrive_delay_count(1);
        } else {
            probabilityModel.setActual_arrive_delay_count(0);
        }
        //实际出发延迟
        if (!Objects.equals(excelModel.getActual_departure_time(), "0")) {
            probabilityModel.setActual_departure_delay_count(1);
        } else {
            probabilityModel.setActual_departure_delay_count(0);
        }
        probabilityModel.setCount(1);
        return probabilityModel;
    }


}
