package org.jxch.capital.dc.a.service.dc;

import org.jxch.capital.dc.a.service.dc.model.StockSector;
import org.jxch.capital.dc.a.service.dc.sw.SWStockSectorDCService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("stockSectorDC")
public class StockSectorDCService implements StockSectorDC{
    private final StockSectorDC dc;

    public StockSectorDCService(SWStockSectorDCService sWStockSectorDCService) {
        this.dc = sWStockSectorDCService;
    }

    @Override
    public List<StockSector> getStockSectors() {
        return dc.getStockSectors();
    }
}
