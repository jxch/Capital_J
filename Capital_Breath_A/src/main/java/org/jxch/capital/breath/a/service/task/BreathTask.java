package org.jxch.capital.breath.a.service.task;

import cn.hutool.core.date.DateUtil;
import org.jxch.capital.breath.a.model.StockSectorScore;
import org.jxch.capital.breath.a.service.breath.BreathService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BreathTask {
    private final BreathService breathService;

    public BreathTask(BreathService breathService) {
        this.breathService = breathService;
    }

    @Scheduled(cron = "0 0 8 * * ?")
    public void breathToDatabaseTask() {
        List<List<StockSectorScore>> breath = breathService.breathByMA(20,
                DateUtil.parse("2020-01-01"),
                DateUtil.parse(DateUtil.today()));

    }

}
