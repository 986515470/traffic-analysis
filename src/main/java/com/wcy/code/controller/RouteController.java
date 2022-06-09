package com.wcy.code.controller;

import com.wcy.code.PlatformAnalysis.RouteAnalysis;
import com.wcy.code.domain.MapModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @author Wcy
 * @Date 2022/5/31 19:07
 */
@RestController
public class RouteController {

    @Autowired
    private RouteAnalysis routeAnalysis;

    /**
     * gatRouteIdMap
     */
    @GetMapping("/gatRouteIdMap")
    public  List<MapModel> gatRouteIdMap() {
        return routeAnalysis.gatRouteIdMap();
    }

    /**
     * gatRouteIdStopSequenceMap
     */
    @GetMapping("/gatRouteIdStopSequenceMap")
    public  List<MapModel> gatRouteIdStopSequenceMap() {
        return routeAnalysis.gatRouteIdStopSequenceMap();
    }

    /**
     * gatRouteIdStopNameMap
     */
    @GetMapping("/gatRouteIdStopNameMap")
    public   HashMap<String, Integer>  gatRouteIdStopNameMap() {
        return routeAnalysis.gatRouteIdStopNameMap();
    }




    /**
     * result3
     */
    @GetMapping("/result3")
    public void result3() {
        routeAnalysis.gatRouteIdMap();
        routeAnalysis.gatRouteIdStopSequenceMap();
        routeAnalysis.gatRouteIdStopNameMap();
    }

}
