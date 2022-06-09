package com.wcy.code.controller;

import com.wcy.code.PlatformAnalysis.DelayTimeAnalysis;
import com.wcy.code.PlatformAnalysis.ExcelOptionsService;
import com.wcy.code.PlatformAnalysis.RouteAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wcy
 * @Date 2022/5/31 23:38
 */
@RestController
public class MainController {
    @Autowired
    private ExcelOptionsService excelOptionsService;

    @Autowired
    private DelayTimeAnalysis delayTimeAnalysis;

    @Autowired
    private RouteAnalysis routeAnalysis;

    /**
     * analyze
     */
    @GetMapping("/analyze")
    public void analyze() {
        excelOptionsService.getRouteId();
        excelOptionsService.gatRouteMap();
        excelOptionsService.getTripIdAndWeek();
        excelOptionsService.getRouteIdAndWeek();

        delayTimeAnalysis.getAvgTime();
        delayTimeAnalysis.getAvgTimeByStopId();
        delayTimeAnalysis.getDelayProbability();

        routeAnalysis.gatRouteIdMap();
        routeAnalysis.gatRouteIdStopSequenceMap();
        routeAnalysis.gatRouteIdStopNameMap();
    }
}
