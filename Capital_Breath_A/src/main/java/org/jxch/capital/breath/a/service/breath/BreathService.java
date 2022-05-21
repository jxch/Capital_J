package org.jxch.capital.breath.a.service.breath;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.breath.a.model.StockKLines;
import org.jxch.capital.breath.a.model.StockMAKLines;
import org.jxch.capital.breath.a.model.StockSector;
import org.jxch.capital.breath.a.model.StockSectorScore;
import org.jxch.capital.breath.a.service.dc.StockDailyDC;
import org.jxch.capital.breath.a.service.dc.StockSectorDC;
import org.jxch.capital.breath.a.utils.MAIndexUtil;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BreathService implements Breath {
    private final StockSectorDC sectorDC;
    private final StockDailyDC stockDailyDC;

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
            List<StockMAKLines> mkLines = computeMA(kLines, ma);
            List<StockSectorScore> sectorScores = sectorScoreSortedByDate(stockSector.getName(), mkLines, stockSector.getCodes().size());
            breaths.add(sectorScores);
            log.info("{} success.", stockSector.getName());

            try {
                TimeUnit.MINUTES.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return breaths;
    }

    private List<StockMAKLines> computeMA(@NotNull List<StockKLines> kLines, int ma) {
        return kLines.stream().map(stockKLines -> new StockMAKLines(stockKLines.getCode(),
                        MAIndexUtil.getMAKLinesSortedByDate(stockKLines.getKLines(), ma)))
                .collect(Collectors.toList());
    }

    @NotNull
    private List<StockSectorScore> sectorScoreSortedByDate(String sector, @NotNull List<StockMAKLines> mkLines, int size) {
        Map<Date, Integer> scores = new HashMap<>();
        mkLines.forEach(makLines -> makLines.getMakLines().forEach(makLine -> {
            Date date = makLine.getStockKLine().getDate();
            scores.putIfAbsent(date, 0);
            if (makLine.getMa().compareTo(makLine.getStockKLine().getClose()) < 0) {
                scores.put(date, scores.get(date) + 1);
            }
        }));

        return scores.keySet().stream().map(date -> new StockSectorScore(sector, date, 100 * scores.get(date) / size))
                .sorted(Comparator.comparing(StockSectorScore::getDate).reversed())
                .collect(Collectors.toList());
    }
}
