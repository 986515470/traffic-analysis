package com.wcy.code.controller;

import com.wcy.code.PlatformAnalysis.ExcelOptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @author Wcy
 * @Date 2022/5/27 15:46
 */
@RestController
public class OptionsController {
    @Autowired
    private ExcelOptionsService excelOptionsService;

    /**
     * 数据总数
     *
     * @return
     */
    @GetMapping("/count")
    public int test() {
        return excelOptionsService.ReadExcel().size();
    }


    /**
     * 路由id的数量
     */
    @GetMapping("/routeId")
    public int test3() {
        return excelOptionsService.getRouteId().size();
    }


    /**
     * gatRouteMap
     */
    @GetMapping("/routeMap")
    public HashMap<String, List<String>> test5() {
        return excelOptionsService.gatRouteMap();
    }

    /**
     * getRouteLongNameAndWeek
     */
    @GetMapping("/getTripIdAndWeek")
    public HashMap<String, List<Integer>> test6() {
        return excelOptionsService.getTripIdAndWeek();
    }

    /**
     * getRouteIdAndWeek
     */
    @GetMapping("/getRouteIdAndWeek")
    public HashMap<String, List<Integer>> test7() {
        return excelOptionsService.getRouteIdAndWeek();
    }

    /**
     * 打印分析报告
     */
    @GetMapping("/result")
    public void result() {
        excelOptionsService.getRouteId();
        excelOptionsService.gatRouteMap();
        excelOptionsService.getTripIdAndWeek();
        excelOptionsService.getRouteIdAndWeek();
    }


}
