package org.jxch.capital.breath.a.service.task;

import cn.hutool.core.date.DateUtil;
import org.jxch.capital.breath.a.mapper.BreathADao;
import org.jxch.capital.breath.a.mapstruct.BreathAConvert;
import org.jxch.capital.breath.a.model.StockSectorScore;
import org.jxch.capital.breath.a.service.breath.BreathService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Service
public class BreathTask {
    private final BreathService breathService;
    @Resource
    private BreathADao dao;

    public BreathTask(BreathService breathService) {
        this.breathService = breathService;
    }

    @PostConstruct
    private void init() {
//        breathToDatabaseTask();
    }

    @Scheduled(cron = "0 0 8 * * ?")
    public void breathToDatabaseTask() {
        List<List<StockSectorScore>> breath = breathService.breathByMA(20,
                DateUtil.parse("2020-01-01"),
                DateUtil.parse(DateUtil.today()));

        dao.truncate();
        breath.forEach(scores -> dao.inserts(BreathAConvert.INSTANCE.toBreathA(scores)));
    }

}
