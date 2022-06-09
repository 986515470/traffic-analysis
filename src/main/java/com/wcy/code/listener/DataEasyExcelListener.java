package com.wcy.code.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.wcy.code.domain.ExcelModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class DataEasyExcelListener extends AnalysisEventListener<ExcelModel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataEasyExcelListener.class);

    /** ExcelModel表格列属性模板 */
    public List<ExcelModel> list = new ArrayList<>();

//    private static  int BATCH_COUNT = 0;
    /**
     * 每解析到一条数据，执行一次invoke（）方法
     * @param data
     * @param context
     */
    @Override
    public void invoke(ExcelModel data, AnalysisContext context) {
//        BATCH_COUNT++;
//        LOGGER.info("解析到一条数据,数据总数:{}",BATCH_COUNT);
        list.add(data);
    }

   //创建方法，在数据解析开始前执行



    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        LOGGER.info("共有{}条Id数据存入list", list.size());
    }
    // 用于获取文件内容列表
    public List<ExcelModel> getList() {
        return list;
    }
}
