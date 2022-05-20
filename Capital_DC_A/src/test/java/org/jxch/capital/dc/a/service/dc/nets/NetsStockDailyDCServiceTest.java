package org.jxch.capital.dc.a.service.dc.nets;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.jxch.capital.dc.a.service.dc.model.StockKLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class NetsStockDailyDCServiceTest {
    @Autowired
    private NetsStockDailyDCService service;

    @Test
    void dailyKLineSortedByDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        try {
            Date start = format.parse("20190101");
            Date end = format.parse("20220101");
            List<StockKLine> stockKLines = service.dailyKLineSortedByDate("0600756", start, end);

            System.out.println("SUCCESS");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testDailyKLineSortedByDate() {
    }

    @Test
    void testDailyKLineSortedByDate1() {
    }

    @Test
    void convert() {
    }

    @Test
    void testDailyKLineSortedByDate2() {
    }
}