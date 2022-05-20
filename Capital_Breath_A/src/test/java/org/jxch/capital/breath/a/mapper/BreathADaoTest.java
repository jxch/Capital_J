package org.jxch.capital.breath.a.mapper;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.jxch.capital.breath.a.mapstruct.BreathAConvert;
import org.jxch.capital.breath.a.model.StockSectorScore;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
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
    @Transactional
    void inserts() {
        List<StockSectorScore> scores1 = new ArrayList<>();
        scores1.add(new StockSectorScore("test1", DateUtil.parse(DateUtil.today()), 50));
        scores1.add(new StockSectorScore("test1", DateUtil.parse("2020-01-01"), 25));

        List<StockSectorScore> scores2 = new ArrayList<>();
        scores1.add(new StockSectorScore("test2", DateUtil.parse(DateUtil.today()), 50));
        scores1.add(new StockSectorScore("test3", DateUtil.parse("2020-01-01"), 25));

        List<List<StockSectorScore>> lists = new ArrayList<>(Arrays.asList(scores1, scores2));

        List<StockSectorScore> sectorScores = lists.parallelStream().flatMap(Collection::stream).collect(Collectors.toList());

        dao.inserts(BreathAConvert.INSTANCE.toBreathA(sectorScores));
    }
}