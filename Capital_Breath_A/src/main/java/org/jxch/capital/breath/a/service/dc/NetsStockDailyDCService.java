package org.jxch.capital.breath.a.service.dc;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.breath.a.model.StockKLine;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NetsStockDailyDCService implements StockDailyDC {
    private Path folderPath;
    private final OkHttpClient okHttpClient;

    public NetsStockDailyDCService(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    @PostConstruct
    private void init() {
        try {
            Path folder = new ClassPathResource("stock/daily").getFile().toPath();
            boolean desk = Files.exists(folder);
            if (!desk) {
                Files.createDirectories(folder);
            }
            folderPath = Paths.get(folder.toAbsolutePath().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<StockKLine> dailyKLineSortedByDate(String code, Date start, Date end) {
        return dailyKLineSortedByDateCompatible(code, start, end);
    }

    public List<StockKLine> dailyKLineSortedByDateCompatible(String code, Date start, Date end) {
        String shCode = "0" + code;
        String szCode = "1" + code;
        try {
            List<StockKLine> shStockKLines = getDailyKLineSortedByDate(shCode, start, end);
            if (!shStockKLines.isEmpty()) {
                return shStockKLines;
            }
        } catch (NumberFormatException e) {
            return getDailyKLineSortedByDate(szCode, start, end);
        }

        return getDailyKLineSortedByDate(szCode, start, end);
    }

    public List<StockKLine> getDailyKLineSortedByDate(String code, Date start, Date end) {
        Path filePath = getFilePath(code);
        Request request = new Request.Builder().url(getDownloadURL(code, start, end)).build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (response.body() != null) {
                createFile(filePath, response.body().bytes());
            } else throw new RuntimeException("请求无返回, 请检查目标网站状态!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return convert(filePath);
    }

    public List<StockKLine> convert(@NotNull Path filePath) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        CsvData csv = CsvUtil.getReader().read(FileUtil.file(filePath.toAbsolutePath().toString()));
        List<CsvRow> rows = csv.getRows();
        rows.remove(0);

        return csv.getRows().stream().map(row -> {
            List<String> raws = row.getRawList();
            StockKLine kLine;
            try {
                Date date = format.parse((raws.get(0)));
                String code = (raws.get(1));
                BigDecimal close = new BigDecimal((raws.get(3)));
                BigDecimal high = new BigDecimal((raws.get(4)));
                BigDecimal low = new BigDecimal((raws.get(5)));
                BigDecimal open = new BigDecimal((raws.get(6)));
                BigDecimal vol = new BigDecimal((raws.get(7)));
                kLine = new StockKLine(code, date, open, close, high, low, vol);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            return kLine;
        }).collect(Collectors.toList());
    }

    private void createFile(Path filePath, byte[] bytes) {
        try {
            if (!Files.exists(filePath, LinkOption.NOFOLLOW_LINKS)) {
                Files.write(filePath, bytes, StandardOpenOption.CREATE);
            } else {
                Files.delete(filePath);
                Files.write(filePath, bytes, StandardOpenOption.CREATE);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    private String getDownloadURL(String code, Date start, Date end) {
        String url = "http://quotes.money.163.com/service/chddata.html?";
        String fields = "&fields=TCLOSE;HIGH;LOW;TOPEN;VOTURNOVER;";
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return url + "code=" + code + "&start=" + format.format(start) + "&end=" + format.format(end) + fields;
    }

    @NotNull
    private Path getFilePath(@NotNull String code) {
        return Paths.get(folderPath + "/" + code.substring(1) + ".csv");
    }
}
