package com.wcy.code;


import com.wcy.code.PlatformAnalysis.ExcelOptionsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Wcy
 * @Date 2022/5/26 22:38
 */
@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
        System.out.println("基于物联网的智能交通项目启动成功！");
//        String path="C:\\Java\\SpringBootExe\\TrafficAnalysis\\Data.xlsx";
        ExcelOptionsService.ReadExcel();
    }
}
