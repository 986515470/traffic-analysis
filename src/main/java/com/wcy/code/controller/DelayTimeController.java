package com.wcy.code.controller;

import com.wcy.code.PlatformAnalysis.DelayTimeAnalysis;
import com.wcy.code.domain.ExcelModel;
import com.wcy.code.domain.ProbabilityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author Wcy
 * @Date 2022/5/29 16:10
 */
@RestController
public class DelayTimeController {


    @Resource
    private DelayTimeAnalysis delayTimeAnalysis;

    /**
     * getAvgTime
     */
    @GetMapping("/getAvgTime")
    public HashMap<String, String> getAvgTime() {
        return delayTimeAnalysis.getAvgTime();
    }



    /**
     * getAvgTimeByStopId
     */
    @GetMapping("/getAvgTimeByStopId")
    public HashMap<String, String> getAvgTimeByStopId() {
        return delayTimeAnalysis.getAvgTimeByStopId();
    }



    /**
     * getAvgDelay
     */
    @GetMapping("/getDelayProbability")
    public HashMap<String, ProbabilityModel> getDelayProbability() {
        return delayTimeAnalysis.getDelayProbability();
    }

    /**
     * result2
     */
    @GetMapping("/result2")
    public void result2() {
        delayTimeAnalysis.getAvgTime();
        delayTimeAnalysis.getAvgTimeByStopId();
        delayTimeAnalysis.getDelayProbability();
    }

}
