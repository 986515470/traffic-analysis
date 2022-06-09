package com.wcy.code.PlatformAnalysis;

import com.alibaba.excel.EasyExcel;
import com.wcy.code.domain.ExcelModel;
import com.wcy.code.domain.MapModel;
import com.wcy.code.listener.DataEasyExcelListener;
import com.wcy.code.utils.DateUtils;
import com.wcy.code.utils.HashMapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Wcy
 * @Date 2022/5/27 15:48
 */
@Service
public class ExcelOptionsService {
    /*一次读取，到处获取*/
    public static DataEasyExcelListener listener = new DataEasyExcelListener();

    public static Logger logger = LoggerFactory.getLogger(ExcelOptionsService.class);

    /**
     * getListener
     */
    public DataEasyExcelListener getListener() {
        return listener;
    }

    /**
     * 根据excel输入流，读取excel文件
     *
     * @return 返回list的集合
     **/
    public static List<ExcelModel> ReadExcel() {
        String startTime = DateUtils.getCurrentTime();
        logger.info("--------------------------------------------------------------------------------");
        logger.info("开始读取excel文件，开始时间：{}", startTime);
        String readPath = "C:\\Users\\wcy\\Desktop\\Data.xlsx";
        //使用EasyExcel读取文件
        EasyExcel.read(readPath, ExcelModel.class, listener).sheet(0).doRead();
        String endTime = DateUtils.getCurrentTime();
        logger.info("结束读取excel文件，结束时间：{}", endTime);
        logger.info("读取excel文件，耗时：{} S", DateUtils.getTimeDifference(startTime, endTime));
        logger.info("--------------------------------------------------------------------------------");
        return listener.getList();
    }


    /**
     * 获取路线id HashMap
     * 分析具有相同前缀得路线id
     */
    public HashMap<String, List<String>> getRouteId() {
        List<ExcelModel> list = listener.getList();
        //数据预载入结束
        //获取route_id ,前缀 hashSet集合
        HashSet<String> set = new HashSet<>();
        HashSet<String> pre_set = new HashSet<>();
        for (ExcelModel excelModel : list) {
            set.add(excelModel.getRoute_id());   //路由id
            pre_set.add(excelModel.getRoute_id().split("_")[0]); //前缀
        }
        HashMap<String, List<String>> map = new HashMap<>();
        for (String pre_route_id : pre_set) {
            map.put(pre_route_id, new ArrayList<>());
        }
        for (String route_id : set) {
            List<String> route_id_list = map.get(route_id.split("_")[0]);
            route_id_list.add(route_id);
        }
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("此处分析:具有相同路由前缀/route_id_prefix的路线id/route_id集合 :");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("route_id (size) :" + set.size());
        for (String key : map.keySet()) {
            System.out.println(key + " : " + map.get(key));
        }
        System.out.println("route_id_prefix (size) :" + map.size());
        System.out.println("route_id_prefix = List<route_id> (size):" + pre_set.toString());
        System.out.println("--------------------------------------------------------------------------------");
        return map;
    }


    /**
     * 根据”trip_headsign“:"route_long_name"获取路由完整路径 存放于hashmap中
     *  key:route_long_name value:List<trip_headsign>
     * 获取该路线下具有得行程集合
     * 可根据该路线下的行程集合，进行相应的分析
     */
    public HashMap<String, List<String>> gatRouteMap() {

        List<ExcelModel> list = listener.getList();
        HashMap<String, List<String>> routeMap = new HashMap<String, List<String>>();

        for (ExcelModel key :list) {
            //获取value
            String routeLongName = key.getRoute_long_name(); //路线名称
            if (routeMap.containsKey(routeLongName)) {
                //如果routeMap中已经存在routeLongName，则将行程(trip_headsign)存入List<String>中
                if(!routeMap.get(routeLongName).contains(key.getTrip_headsign())){
                    routeMap.get(routeLongName).add(key.getTrip_headsign());
                }
            } else {
                //如果routeMap中不存在routeLongName，则将routeLongName作为key，List<String>作为value存入routeMap
                routeMap.put(routeLongName, new ArrayList<String>());
                routeMap.get(routeLongName).add(key.getTrip_headsign());
            }
        }
        System.out.println("此处分析:具有相同 (路线全名/route_long_name) 的 (行程全名/trip_headsign) 的集合数量 :");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("route_long_name = 最多行程全名的数量的10个路线全名 : (size)");
        List<MapModel>  MaxTopMap = HashMapUtils.getStringList(routeMap,1); //这里选择打印十个
        for (MapModel key : MaxTopMap) {
//            System.out.println(key.getKey() + " = " + key.getList().toString() + " : " + key.getSize());
            System.out.println(key.getKey() + " = "  + key.getSize());
        }
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("route_long_name = 最少行程全名的数量的10个路线全名 : (size)");
        List<MapModel>  MinTopMap = HashMapUtils.getStringList(routeMap,2); //这里选择打印十个
        for (MapModel key : MinTopMap) {
//            System.out.println(key.getKey() + " = " + key.getList().toString() + " : " + key.getSize());
            System.out.println(key.getKey() + " = "  + key.getSize());
        }
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("route_long_name = List<trip_headsign> (Total size):" + routeMap.size());
        System.out.println("--------------------------------------------------------------------------------");
        return routeMap;
    }


    /**
     * "trip_id"与"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"的总次数 hashmap
     * key:trip_id value:list<Integer>=[monday,tuesday,wednesday,thursday,friday,saturday,sunday]
     * 其中，list<Integer>中的每一个元素代表该行程在每一天的调度次数
     * 分析该行程在数据集中得一个调度信息
     */
    public HashMap<String, List<Integer>> getTripIdAndWeek() {

        List<ExcelModel> list = listener.getList();
        HashMap<String, List<Integer>> map = new HashMap<>();
        for (ExcelModel excelModel : list) {
            if (map.containsKey(excelModel.getTrip_id())) {
                map.get(excelModel.getTrip_id()).set(0, map.get(excelModel.getTrip_id()).get(0) + excelModel.getMonday());
                map.get(excelModel.getTrip_id()).set(1, map.get(excelModel.getTrip_id()).get(1) + excelModel.getTuesday());
                map.get(excelModel.getTrip_id()).set(2, map.get(excelModel.getTrip_id()).get(2) + excelModel.getWednesday());
                map.get(excelModel.getTrip_id()).set(3, map.get(excelModel.getTrip_id()).get(3) + excelModel.getThursday());
                map.get(excelModel.getTrip_id()).set(4, map.get(excelModel.getTrip_id()).get(4) + excelModel.getFriday());
                map.get(excelModel.getTrip_id()).set(5, map.get(excelModel.getTrip_id()).get(5) + excelModel.getSaturday());
                map.get(excelModel.getTrip_id()).set(6, map.get(excelModel.getTrip_id()).get(6) + excelModel.getSunday());
            } else {
                List<Integer> weekList = new ArrayList<>();
                weekList.add(excelModel.getMonday());
                weekList.add(excelModel.getTuesday());
                weekList.add(excelModel.getWednesday());
                weekList.add(excelModel.getThursday());
                weekList.add(excelModel.getFriday());
                weekList.add(excelModel.getSaturday());
                weekList.add(excelModel.getSunday());
                map.put(excelModel.getTrip_id(), weekList);
            }
        }
        System.out.println("此处分析: (行程id/trip_id) 与 (一周日期/(monday,tuesday,wednesday,thursday,friday,saturday,sunday) )调度信息的集合 :");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println(" (行程id/trip_id) 与 (一周日期/(monday,tuesday,wednesday,thursday,friday,saturday,sunday) )调度信息最多的10个集合");
        List<MapModel>  MaxTopMap = HashMapUtils.getIntegerList(map,1); //这里选择打印十个
        for (MapModel key : MaxTopMap) {
            System.out.println(key.getKey() + " = " + key.getList2().toString() + " : " + key.getSize());
        }
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println(" (行程id/trip_id) 与 (一周日期/(monday,tuesday,wednesday,thursday,friday,saturday,sunday) )调度信息最少的10个集合");
        List<MapModel>  MinTopMap = HashMapUtils.getIntegerList(map,2); //这里选择打印十个
        for (MapModel key : MinTopMap) {
            System.out.println(key.getKey() + " = " + key.getList2().toString() + " : " + key.getSize());
        }
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("trip_id = monday, tuesday, wednesday, thursday, friday, saturday, sunday (Total size):" + map.size());
        System.out.println("--------------------------------------------------------------------------------");
        return map;
    }

    /**
     * "route_id"与"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"的总次数 hashmap
     * key:route_id value:list<Integer>=[monday,tuesday,wednesday,thursday,friday,saturday,sunday]
     * 其中，list<Integer>中的每一个元素代表该路线在每一天的调度次数
     * 分析该路线得一个调度信息情况
     */
    public HashMap<String, List<Integer>> getRouteIdAndWeek() {

        List<ExcelModel> list = listener.getList();
        HashMap<String, List<Integer>> map = new HashMap<>();
        for (ExcelModel excelModel : list) {
            if (map.containsKey(excelModel.getRoute_id())) {
                map.get(excelModel.getRoute_id()).set(0, map.get(excelModel.getRoute_id()).get(0) + excelModel.getMonday());
                map.get(excelModel.getRoute_id()).set(1, map.get(excelModel.getRoute_id()).get(1) + excelModel.getTuesday());
                map.get(excelModel.getRoute_id()).set(2, map.get(excelModel.getRoute_id()).get(2) + excelModel.getWednesday());
                map.get(excelModel.getRoute_id()).set(3, map.get(excelModel.getRoute_id()).get(3) + excelModel.getThursday());
                map.get(excelModel.getRoute_id()).set(4, map.get(excelModel.getRoute_id()).get(4) + excelModel.getFriday());
                map.get(excelModel.getRoute_id()).set(5, map.get(excelModel.getRoute_id()).get(5) + excelModel.getSaturday());
                map.get(excelModel.getRoute_id()).set(6, map.get(excelModel.getRoute_id()).get(6) + excelModel.getSunday());
            } else {
                List<Integer> weekList = new ArrayList<>();
                weekList.add(excelModel.getMonday());
                weekList.add(excelModel.getTuesday());
                weekList.add(excelModel.getWednesday());
                weekList.add(excelModel.getThursday());
                weekList.add(excelModel.getFriday());
                weekList.add(excelModel.getSaturday());
                weekList.add(excelModel.getSunday());
                map.put(excelModel.getRoute_id(), weekList);
            }
        }
        System.out.println("此处分析: (路线id/route_id) 与 (一周日期/(monday,tuesday,wednesday,thursday,friday,saturday,sunday) )调度信息的集合 :");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("(路线id/route_id) 与 (一周日期/(monday,tuesday,wednesday,thursday,friday,saturday,sunday) )调度信息最多的10个集合");
        List<MapModel>  MaxTopMap = HashMapUtils.getIntegerList(map,1); //这里选择打印十个
        for (MapModel key : MaxTopMap) {
            System.out.println(key.getKey() + " = " + key.getList2().toString() + " : " + key.getSize());
        }
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("(路线id/route_id) 与 (一周日期/(monday,tuesday,wednesday,thursday,friday,saturday,sunday) )调度信息最少的10个集合");
        List<MapModel>  MinTopMap = HashMapUtils.getIntegerList(map,2); //这里选择打印十个
        for (MapModel key : MinTopMap) {
            System.out.println(key.getKey() + " = " + key.getList2().toString() + " : " + key.getSize());
        }
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("route_id = monday, tuesday, wednesday, thursday, friday, saturday, sunday (Total size):" + map.size());
        System.out.println("--------------------------------------------------------------------------------");
        return map;

    }


}

