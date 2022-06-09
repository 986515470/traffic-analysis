package com.wcy.code.PlatformAnalysis;

import com.wcy.code.domain.ExcelModel;
import com.wcy.code.domain.MapModel;
import com.wcy.code.utils.HashMapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Wcy
 * @Date 2022/5/31 19:06
 */
@Service
public class RouteAnalysis {

    @Autowired
    private ExcelOptionsService excelOptionsService;

    /**
     * hashMap 存放路线下包含的行程集合
     * key: "route_id"  value: List<trip_id>
     */
    public List<MapModel> gatRouteIdMap() {
        List<ExcelModel> list = excelOptionsService.getListener().getList();
        HashMap<String, List<String>> map = new HashMap<>();
        for (ExcelModel excelModel : list) {
            String routeId = excelModel.getRoute_id();
            String tripId = excelModel.getTrip_id();
            if (map.containsKey(routeId)) {
                List<String> list1 = map.get(routeId);
                if (!list1.contains(tripId)) {
                    list1.add(tripId);
                }
            } else {
                List<String> list1 = new ArrayList<>();
                list1.add(tripId);
                map.put(routeId, list1);
            }
        }
        System.out.println("此处分析:具有相同 (路线id/route_id) 的 (行程id/trip_id) 的集合 :");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("路线id/route_id = 具有最多行程的10个路线");
        System.out.println("--------------------------------------------------------------------------------");
        List<MapModel> MaxTopMap = HashMapUtils.getStringList(map, 1); //这里选择打印十个
        for (MapModel key : MaxTopMap) {
//            System.out.println(key.getKey() + " = " + key.getList().toString() + " : " + key.getSize());
            System.out.println(key.getKey() + " = " + key.getSize());
        }
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("路线id/route_id = 具有最少行程的10个路线");
        System.out.println("--------------------------------------------------------------------------------");
        List<MapModel> MinTopMap = HashMapUtils.getStringList(map, 2); //这里选择打印十个
        for (MapModel key : MinTopMap) {
            System.out.println(key.getKey() + " = " + key.getList().toString() + " : " + key.getSize());
//            System.out.println(key.getKey() + " = "  + key.getSize());
        }
        System.out.println("--------------------------------------------------------------------------------");
        return MaxTopMap;
    }

    /**
     * "route_id","stop_sequence"
     * key: "route_id"  value: List<stop_sequence>
     */
    public List<MapModel> gatRouteIdStopSequenceMap() {

        List<ExcelModel> list = excelOptionsService.getListener().getList();
        HashMap<String, List<Integer>> map = new HashMap<>();
        for (ExcelModel excelModel : list) {
            String routeId = excelModel.getRoute_id();
            String stopSequence = excelModel.getStop_sequence();
            if (map.containsKey(routeId)) {
                List<Integer> list1 = map.get(routeId);
                if (!list1.contains(Integer.valueOf(stopSequence))) {
                    list1.add(Integer.valueOf(stopSequence));
                    Collections.sort(list1);
                }
            } else {
                List<Integer> list1 = new ArrayList<>();
                list1.add(Integer.valueOf(stopSequence));
                map.put(routeId, list1);
            }
        }
        System.out.println("此处分析: (路线id/route_id) 下的 (站点序号/stop_sequence) 的集合 :");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("路线id/route_id = 具有最多站点的10个路线");
        System.out.println("--------------------------------------------------------------------------------");
        List<MapModel> MaxTopMap = HashMapUtils.getIntegerList(map, 1); //这里选择打印十个
        for (MapModel key : MaxTopMap) {
//            System.out.println(key.getKey() + " = " + key.getList2().toString() + " : " + key.getList2().size());
            System.out.println(key.getKey() + " = " + key.getList2().size());
        }
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("路线id/route_id = 具有最少站点的10个路线");
        System.out.println("--------------------------------------------------------------------------------");
        List<MapModel> MinTopMap = HashMapUtils.getIntegerList(map, 2); //这里选择打印十个
        for (MapModel key : MinTopMap) {
            System.out.println(key.getKey() + " = " + key.getList2().toString() + " : " + key.getList2().size());
//            System.out.println(key.getKey() + " = "  + key.getList2().size());
        }
        System.out.println("--------------------------------------------------------------------------------");
        return MaxTopMap;
    }

    /**
     * "route_id",List<"stop_name">
     * key: "route_id"  value: List<stop_name>
     * 构建行程站点网络
     */
    public HashMap<String, Integer> gatRouteIdStopNameMap() {

        List<ExcelModel> list = excelOptionsService.getListener().getList();
        HashMap<String, List<String>> map = new HashMap<>();
        for (ExcelModel excelModel : list) {
            String routeId = excelModel.getRoute_id();
            String stopName = excelModel.getStop_name();
            if (map.containsKey(routeId)) {
                List<String> list1 = map.get(routeId);
                if (!list1.contains(stopName)) {
                    list1.add(stopName);
                }
            } else {
                List<String> list1 = new ArrayList<>();
                list1.add(stopName);
                map.put(routeId, list1);
            }
        }
        System.out.println("此处分析: (路线id/route_id) 下的 (站点名称/stop_name) 的集合 :");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("路线id/route_id = 具有最多站点的10个站点");
        System.out.println("--------------------------------------------------------------------------------");
        List<MapModel> MaxTopMap = HashMapUtils.getStringList(map, 1); //这里选择打印十个
        for (MapModel key : MaxTopMap) {
//            System.out.println(key.getKey() + " = " + key.getList().toString() + " : " + key.getList().size());
            System.out.println(key.getKey() + " = " + key.getList().size());
        }
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("路线id/route_id = 具有最少站点的10个站点");
        System.out.println("--------------------------------------------------------------------------------");
        List<MapModel> MinTopMap = HashMapUtils.getStringList(map, 2); //这里选择打印十个
        for (MapModel key : MinTopMap) {
//            System.out.println(key.getKey() + " = " + key.getList().toString() + " : " + key.getList().size());
            System.out.println(key.getKey() + " = " + key.getList().size());
        }
        System.out.println("--------------------------------------------------------------------------------");
//        for(Map.Entry<String, List<String>> entry : map.entrySet()){
////            System.out.println(entry.getKey() + " = " + entry.getValue().toString());
//            System.out.println(entry.getKey() + ",");
//        }
        HashMap<String, Integer> all = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
//            System.out.println(entry.getKey() + " = " + entry.getValue().toString());
            all.put(entry.getKey(), entry.getValue().size());
        }
        return all;
    }
}
