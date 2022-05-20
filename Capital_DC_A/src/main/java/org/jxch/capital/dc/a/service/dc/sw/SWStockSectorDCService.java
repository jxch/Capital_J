package org.jxch.capital.dc.a.service.dc.sw;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jxch.capital.dc.a.service.dc.StockSectorDC;
import org.jxch.capital.dc.a.service.dc.model.Stock;
import org.jxch.capital.dc.a.service.dc.model.StockSector;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SWStockSectorDCService implements StockSectorDC {
    private final String downloadURL = "http://www.swsindex.com/downloadfiles.aspx?swindexcode=SwClass&type=530&columnid=8892";
    private final Request request = new Request.Builder().url(downloadURL).build();
    private final OkHttpClient okHttpClient;
    private Path filePath;

    public SWStockSectorDCService(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    @PostConstruct
    private void init() {
        Path folder = Paths.get("src/main/resources/sector");
        boolean desk = Files.exists(folder);
        if (!desk) {
            try {
                Files.createDirectories(folder);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        filePath = Paths.get(folder + "/SW.html");
    }

    @Override
    public List<StockSector> getStockSectors() {
        if (notExists()) {
            downloadStockSectors();
        }

        return convert();
    }

    @NotNull
    private List<StockSector> convert() {
        if (notExists())
            throw new RuntimeException("没有文件存在, 请检查目标网站的状态");

        Map<String, List<Stock>> stockSectors;
        try {
            String html = FileUtils.readFileToString(new File(filePath.toAbsolutePath().toString()), "GB2312");
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

    /*
     * 每月1号2点30分执行一次
     * todo 按时下载文件
     * */
//    @Scheduled(cron = "0 30 2 1 * ?")
    public void downloadStockSectors() {
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (response.body() != null) {
                createFile(response.body().bytes());
            } else throw new RuntimeException("请求无返回, 请检查目标网站状态!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createFile(byte[] bytes) {
        try {
            if (notExists()) {
                Files.write(filePath, bytes, StandardOpenOption.CREATE);
            } else {
                Files.delete(filePath);
                Files.write(filePath, bytes, StandardOpenOption.CREATE);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean notExists() {
        return !Files.exists(filePath, LinkOption.NOFOLLOW_LINKS);
    }
}
