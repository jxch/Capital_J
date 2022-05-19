package org.jxch.capital.dc.a.service.dc;

import org.jxch.capital.dc.a.service.dc.model.StockKLine;

import java.util.List;
import java.util.Map;

public interface StockDailyDC {
    List<StockKLine> dailyKLineSortedByDate(String stock);
    Map<String, List<StockKLine>> dailyKLineSortedByDate(List<String> stocks);
}
