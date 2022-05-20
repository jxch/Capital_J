package org.jxch.capital.breath.a.utils;

import org.jetbrains.annotations.NotNull;
import org.jxch.capital.breath.a.model.StockKLine;
import org.jxch.capital.breath.a.model.StockMAKLine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MAIndexUtil {
    public static List<StockMAKLine> getMAKLinesSortedByDate(@NotNull List<StockKLine> kLines, int ma) {
        return IntStream.range(0, kLines.size() - ma).mapToObj(i ->
                new StockMAKLine(kLines.get(i),
                        kLines.subList(i, i + ma).stream().map(StockKLine::getClose)
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                                .divide(new BigDecimal(ma), RoundingMode.HALF_UP))
        ).collect(Collectors.toList());
    }
}
