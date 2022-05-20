package org.jxch.capital.breath.a.service.dc;

import org.jetbrains.annotations.NotNull;
import org.jxch.capital.breath.a.model.StockKLine;
import org.jxch.capital.breath.a.model.StockKLines;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public interface StockDailyDC {
    List<StockKLine> dailyKLineSortedByDate(String code, Date start, Date end);

    default List<StockKLines> dailyKLineSortedByDate(@NotNull List<String> codes, Date start, Date end) {
        return codes.parallelStream()
                .map(code -> new StockKLines(code, dailyKLineSortedByDate(code, start, end)))
                .collect(Collectors.toList());
    }
}
