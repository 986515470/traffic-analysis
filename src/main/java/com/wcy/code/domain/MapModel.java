package com.wcy.code.domain;

import lombok.Data;

import java.util.List;

/**
 * @author Wcy
 * @Date 2022/5/31 15:48
 */
@Data
public class MapModel {

    String key ;

    List<String> list;

    List<Integer> list2;

    int size;

    public MapModel(String key, int size) {
        this.key = key;
        this.size = size;
    }

    ProbabilityModel probabilityModel;

    public MapModel(String key, ProbabilityModel probabilityModel) {
        this.key = key;
        this.probabilityModel = probabilityModel;
    }

    public MapModel(String key,String time) {
        this.key = key;
        this.time = time;
    }

    double probability;

    int seconds; //时间秒数总和

    String time;// HH:mm:ss

}
