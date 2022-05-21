package org.jxch.capital.breath.a.service.dc;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jxch.capital.breath.a.model.Stock;
import org.jxch.capital.breath.a.model.StockSector;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SWStockSectorDCService implements StockSectorDC {
    private final Resource file = new ClassPathResource("sector/SW.html");

    @Override
    public List<StockSector> getStockSectors() {
        return convert();
    }

    @NotNull
    private List<StockSector> convert() {
        Map<String, List<Stock>> stockSectors;
        try (InputStream bis = new BufferedInputStream(file.getInputStream())) {
            String html = IOUtils.toString(bis, "GB2312");
            Document doc = Jsoup.parse(html);
            Elements elements = doc.select("tr");
            elements.remove(0);

            stockSectors = new HashMap<>();
            for (Element element : elements) {
                Elements tds = element.select("td");
                String sector = tds.get(0).text();
                String code = tds.get(1).text();
                String name = tds.get(2).text();
                stockSectors.putIfAbsent(sector, new ArrayList<>());
                stockSectors.get(sector).add(new Stock(code, name, sector));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("Sector scan success.");

        return stockSectors.keySet().stream()
                .map(key -> new StockSector(key, stockSectors.get(key)))
                .collect(Collectors.toList());
    }
}
