package org.jxch.capital.breath.a.service.dc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.jxch.capital.breath.a.model.StockSector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
class SWStockSectorDCServiceTest {
    @Autowired
    private SWStockSectorDCService service;

    @Test
    void getStockSectors() {
        List<StockSector> stockSectors = service.getStockSectors();
    }

    @Test
    void con() {

    }



}