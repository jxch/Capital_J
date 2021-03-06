package org.jxch.capital.dc.a.service.breath;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.dc.a.service.breath.model.StockSectorScore;
import org.jxch.capital.dc.a.service.dc.StockDailyDC;
import org.jxch.capital.dc.a.service.dc.StockSectorDC;
import org.jxch.capital.dc.a.service.dc.model.StockKLines;
import org.jxch.capital.dc.a.service.dc.model.StockSector;
import org.jxch.capital.dc.a.service.index.MAIndex;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BreathService implements Breath {
    private final StockSectorDC sectorDC;
    private final StockDailyDC stockDailyDC;

    // todo StockDailyDC 改为调用链模式以防止一个接口的突然失效
    public BreathService(StockSectorDC stockSectorDC,
                         StockDailyDC netsStockDailyDC) {
        this.sectorDC = stockSectorDC;
        this.stockDailyDC = netsStockDailyDC;
    }

    @Override
    public List<List<StockSectorScore>> breathByMA(int ma, Date start, Date end) {
        List<List<StockSectorScore>> breaths = new ArrayList<>();
        List<StockSector> stockSectors = sectorDC.getStockSectors();
        for (StockSector stockSector : stockSectors) {
            List<StockKLines> kLines = stockDailyDC.dailyKLineSortedByDate(stockSector.getCodes(), start, end);
            List<MAIndex.MAKLines> mkLines = computeMA(kLines, ma);
            List<StockSectorScore> sectorScores = sectorScoreSortedByDate(stockSector.getName(), mkLines, stockSector.getCodes().size());
            breaths.add(sectorScores);
            log.info(stockSector.getName() + "完成");
        }
        return breaths;
    }

    private List<MAIndex.MAKLines> computeMA(@NotNull List<StockKLines> kLines, int ma) {
        return kLines.stream().map(stockKLines -> new MAIndex.MAKLines(stockKLines.getCode(),
                        MAIndex.getMAKLinesSortedByDate(stockKLines.getKLines(), ma)))
                .collect(Collectors.toList());
    }

    @NotNull
    private List<StockSectorScore> sectorScoreSortedByDate(String sector, @NotNull List<MAIndex.MAKLines> mkLines, int size) {
        Map<Date, Integer> scores = new HashMap<>();
        mkLines.forEach(makLines -> makLines.getMakLines().forEach(makLine -> {
            Date date = makLine.getStockKLine().getDate();
            scores.putIfAbsent(date, 0);
            if (makLine.getMa().compareTo(makLine.getStockKLine().getClose()) > 0) {
                scores.put(date, scores.get(date) + 1);
            }
        }));

        return scores.keySet().stream().map(date -> new StockSectorScore(sector, date, 100 * scores.get(date) / size))
                .sorted(Comparator.comparing(StockSectorScore::getDate).reversed())
                .collect(Collectors.toList());
    }

}
