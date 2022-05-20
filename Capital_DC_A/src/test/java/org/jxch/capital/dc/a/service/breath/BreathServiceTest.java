package org.jxch.capital.dc.a.service.breath;

import cn.hutool.core.date.DateUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.jxch.capital.dc.a.service.breath.model.StockSectorScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class BreathServiceTest {
    @Autowired
    private BreathService breathService;

    @Test
    void breathByMA() {
        List<List<StockSectorScore>> lists = breathService.breathByMA(20,
                DateUtil.parse("2019-01-01"),
                DateUtil.parse("2022-05-20"));

    }
}