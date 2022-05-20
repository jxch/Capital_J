package org.jxch.capital.breath.a.service.dc;


import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jxch.capital.breath.a.model.Stock;
import org.jxch.capital.breath.a.model.StockSector;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SWStockSectorDCService implements StockSectorDC {
    private File file;

    @PostConstruct
    private void init() {
        try {
            file = new ClassPathResource("/sector/SW.htm", this.getClass().getClassLoader()).getFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<StockSector> getStockSectors() {
        return convert();
    }

    @NotNull
    private List<StockSector> convert() {
        if (!file.exists())
            throw new RuntimeException("No File!");

        Map<String, List<Stock>> stockSectors;
        try {
            String html = FileUtils.readFileToString(file, "GB2312");
            Document doc = Jsoup.parse(html);
            Elements elements = doc.select("tr");

            stockSectors = new HashMap<>();
            for (Element element : elements) {
                Elements tds = element.select("td");
                if (tds.size() > 0) {
                    String sector = tds.get(0).text();
                    String code = tds.get(1).text();
                    String name = tds.get(2).text();
                    stockSectors.putIfAbsent(sector, new ArrayList<>());
                    stockSectors.get(sector).add(new Stock(code, name, sector));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return stockSectors.keySet().stream()
                .map(key -> new StockSector(key, stockSectors.get(key)))
                .collect(Collectors.toList());
    }
}
