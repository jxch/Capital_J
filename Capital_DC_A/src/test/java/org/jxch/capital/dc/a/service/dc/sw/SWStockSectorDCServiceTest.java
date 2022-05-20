package org.jxch.capital.dc.a.service.dc.sw;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.jxch.capital.dc.a.service.dc.model.StockSector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class SWStockSectorDCServiceTest {
    @Autowired
    private SWStockSectorDCService sectorDCService;

    @Test
    void getStockSectors() {
        List<StockSector> stockSectors = sectorDCService.getStockSectors();
        System.out.println("SUCCESS");
    }

    @Test
    void downloadStockSectors() {
        sectorDCService.downloadStockSectors();
        System.out.println("SUCCESS");
    }


}