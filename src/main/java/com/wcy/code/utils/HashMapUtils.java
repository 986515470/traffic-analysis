package com.wcy.code.utils;

import com.wcy.code.domain.MapModel;
import com.wcy.code.domain.ProbabilityModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author Wcy
 * @Date 2022/5/31 15:15
 */
public class HashMapUtils {

    /**
     * 有这么一个HashMap value为List<String>
     * 返回List<String>长度最长的一些数据
     */
    public static List<MapModel> getStringList(HashMap<String, List<String>> map, int type) {
        List<MapModel> MapModelList = new ArrayList<>();
        for (String key : map.keySet()) {
            List<String> list = map.get(key);
            MapModel mapModel = new MapModel(key, list.size());
            mapModel.setList(list);
            MapModelList.add(mapModel);
        }
        //对MapModelList按照MapModel中的size值进行排序
        Collections.sort(MapModelList, (o1, o2) -> o2.getSize() - o1.getSize());
        if(type==1){
            return MapModelList.subList(0, 10);
        }
        else {
            return MapModelList.subList(MapModelList.size()-10, MapModelList.size());
        }
    }

    /**
     * 有这么一个HashMap value为List<Integer>
     * 返回List<Integer>数据总和最长的一些数据
     */
    public static List<MapModel> getIntegerList(HashMap<String, List<Integer>> map, int type) {
        List<MapModel> MapModelList = new ArrayList<>();
        for (String key : map.keySet()) {
            List<Integer> list = map.get(key);
            MapModel mapModel = new MapModel(key, getIntegerSum(list));
            mapModel.setList2(list);
            MapModelList.add(mapModel);
        }
        //对MapModelList按照MapModel中的size值进行排序
        Collections.sort(MapModelList, (o1, o2) -> o2.getSize() - o1.getSize());
        if(type==1){
            return MapModelList.subList(0, 10);
        }
        else {
            return MapModelList.subList(MapModelList.size()-10, MapModelList.size());
        }
    }

    /**
     * 有这么一个HashMap
     */
    public static List<MapModel> getProbabilityModelList(HashMap<String, ProbabilityModel> map, int type) {
        List<MapModel> MapModelList = new ArrayList<>();
        for (String key : map.keySet()) {
            ProbabilityModel probabilityModel = map.get(key);
            MapModel mapModel = new MapModel(key, probabilityModel);
            mapModel.setProbability(probabilityModel.getProbability());
            MapModelList.add(mapModel);
        }
        //对MapModelList按照MapModel中的probability值进行排序
        Collections.sort(MapModelList, (o1, o2) -> (int) (o2.getProbability() * 100 - o1.getProbability() * 100));
        if(type == 1){
            return MapModelList.subList(0, 5);
        }else{
            return MapModelList.subList(MapModelList.size() - 5, MapModelList.size());
        }
    }


    /**
     * 有这么一个HashMap value为HH:mm:ss
     */
    public static List<MapModel> getTimeList(HashMap<String, String> map, int type) {
        List<MapModel> MapModelList = new ArrayList<>();
        for (String key : map.keySet()) {
            String time = map.get(key);
            MapModel mapModel = new MapModel(key, time);
            mapModel.setSeconds(DateUtils.getSeconds(time));
            MapModelList.add(mapModel);
        }
        //对MapModelList按照MapModel中的time值进行排序
        Collections.sort(MapModelList, (o1, o2) -> o2.getSeconds() - o1.getSeconds());
        if (type == 1) {
            return MapModelList.subList(0, 5);
        } else {
            return MapModelList.subList(MapModelList.size() - 5, MapModelList.size());
        }
    }


    /**
     * 返回List<Integer>的数据总和
     */
    public static int getIntegerSum(List<Integer> list) {
        int sum = 0;
        for (Integer integer : list) {
            sum += integer;
        }
        return sum;
    }
}
