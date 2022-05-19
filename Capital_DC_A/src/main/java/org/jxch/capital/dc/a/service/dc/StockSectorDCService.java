package org.jxch.capital.dc.a.service.dc;

import org.jxch.capital.dc.a.service.dc.model.StockSector;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("stockSectorDC")
public class StockSectorDCService implements StockSectorDC{
    @Override
    public ArrayList<StockSector> getStockSectors() {
        return null;
    }
}
