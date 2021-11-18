package com.meehoo.biz.web.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定时器
 * @author zc
 * @date 2019-06-21
 */
//@Component
//@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class ScheduleHandler {
//    private final String HttpAddress = "http://localhost:8080/";
    private final Logger logger = LoggerFactory.getLogger(Scheduled.class);

    @Autowired
    public ScheduleHandler() {

    }

    /**
     * 每天审核设备的状态，并制定检查计划
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void getERPInfo(){
        try {

        } catch (Exception e) {
//            e.printStackTrace();
            logger.info(e.getMessage());
        }
    }


}
