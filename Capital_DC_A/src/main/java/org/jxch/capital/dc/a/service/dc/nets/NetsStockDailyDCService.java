package org.jxch.capital.dc.a.service.dc.nets;

import org.jxch.capital.dc.a.service.dc.model.StockKLine;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("netsStockDailyDC")
public class NetsStockDailyDCService implements NetsStockDailyDC {
    @Override
    public List<StockKLine> dailyKLineSortedByDate(String stock) {

        return null;
    }

    @Override
    public Map<String, List<StockKLine>> dailyKLineSortedByDate(List<String> stocks) {
        return null;
    }
}
