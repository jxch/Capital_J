package org.jxch.capital.breath.a.mapper;

import cn.hutool.core.date.DateUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.jxch.capital.breath.a.mapstruct.BreathAConvert;
import org.jxch.capital.breath.a.model.StockSectorScore;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class BreathADaoTest {
    @Resource
    private BreathADao dao;

    @Test
    void truncate() {
        dao.truncate();
    }

    @Test
    void inserts() {
        List<List<StockSectorScore>> lists = new ArrayList<>();
        List<StockSectorScore> scores1 = new ArrayList<>();
        scores1.add(new StockSectorScore("test1", DateUtil.parse(DateUtil.today()), 50));
        scores1.add(new StockSectorScore("test1", DateUtil.parse("2020-01-01"), 25));

        List<StockSectorScore> scores2 = new ArrayList<>();
        scores1.add(new StockSectorScore("test2", DateUtil.parse(DateUtil.today()), 50));
        scores1.add(new StockSectorScore("test3", DateUtil.parse("2020-01-01"), 25));

        lists.add(scores1);
        lists.add(scores2);
        lists.forEach(scores -> dao.inserts(BreathAConvert.INSTANCE.toBreathA(scores)));
    }
}