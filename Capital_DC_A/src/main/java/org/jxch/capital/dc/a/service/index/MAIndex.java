package org.jxch.capital.dc.a.service.index;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.dc.a.service.dc.model.StockKLine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MAIndex {
    private final List<StockKLine> kLines;
    private final int ma;

    public MAIndex(@NotNull List<StockKLine> kLinesSortedByDate, int ma) {
        this.kLines = kLinesSortedByDate;
        this.ma = ma;
        if (kLinesSortedByDate.size() < ma) {
            throw new IllegalArgumentException(ma + "MA 大于K线数量");
        }
    }

    public List<MAKLine> getMAKLinesSortedByDate() {
        return MAIndex.getMAKLinesSortedByDate(this.kLines, this.ma);
    }

    public static List<MAKLine> getMAKLinesSortedByDate(@NotNull List<StockKLine> kLines, int ma) {
        return IntStream.range(0, kLines.size()).mapToObj(i ->
                new MAKLine(kLines.get(i), kLines.subList(i, i + ma).stream().map(StockKLine::getClose)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .divide(new BigDecimal(ma), RoundingMode.HALF_UP))
        ).collect(Collectors.toList());
    }

    @Data
    @AllArgsConstructor
    public static class MAKLine {
        private StockKLine kLine;
        private BigDecimal ma;
    }

    @Data
    @AllArgsConstructor
    public static class StockMAKLine{
        private String code;
        private List<MAKLine> makLines;
    }
}
