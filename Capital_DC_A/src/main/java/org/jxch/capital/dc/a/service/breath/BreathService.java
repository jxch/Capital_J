package org.jxch.capital.dc.a.service.breath;

import org.jetbrains.annotations.NotNull;
import org.jxch.capital.dc.a.service.breath.model.StockSectorScore;
import org.jxch.capital.dc.a.service.dc.StockDailyDC;
import org.jxch.capital.dc.a.service.dc.StockSectorDC;
import org.jxch.capital.dc.a.service.dc.model.StockKLine;
import org.jxch.capital.dc.a.service.dc.model.StockSector;
import org.jxch.capital.dc.a.service.index.MAIndex;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    public List<List<StockSectorScore>> breathByMA(int ma) {
        List<List<StockSectorScore>> breaths = new ArrayList<>();
        List<StockSector> stockSectors = sectorDC.getStockSectors();
        for (StockSector stockSector : stockSectors) {
            Map<String, List<StockKLine>> kLines = stockDailyDC.dailyKLineSortedByDate(stockSector.getCodes());
            Map<String, List<MAIndex.MAKLine>> mkLines = computeMA(kLines, ma);
            List<StockSectorScore> sectorScores = sectorScoreSortedByDate(stockSector.getName(), mkLines);
            breaths.add(sectorScores);
        }
        return breaths;
    }

    private Map<String, List<MAIndex.MAKLine>> computeMA(@NotNull Map<String, List<StockKLine>> kLines, int ma) {
        return kLines.keySet().stream().map(key ->
                        new MAIndex.StockMAKLine(key, MAIndex.getMAKLinesSortedByDate(kLines.get(key), ma)))
                .collect(Collectors.toMap(MAIndex.StockMAKLine::getCode, MAIndex.StockMAKLine::getMakLines));
    }

    @NotNull
    private List<StockSectorScore> sectorScoreSortedByDate(String sector, @NotNull Map<String, List<MAIndex.MAKLine>> mkLines) {
        Map<Date, Integer> scores = new HashMap<>();
        mkLines.values().forEach(makLines -> makLines.forEach(makLine -> {
            Date date = makLine.getKLine().getDate();
            scores.putIfAbsent(date, 0);
            if (makLine.getMa().compareTo(makLine.getKLine().getClose()) > 0) {
                scores.put(date, scores.get(date) + 1);
            }
        }));

        return scores.keySet().stream().map(date -> new StockSectorScore(sector, date, scores.get(date)))
                .sorted(Comparator.comparing(StockSectorScore::getDate).reversed())
                .collect(Collectors.toList());
    }

}
