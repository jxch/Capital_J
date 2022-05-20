package org.jxch.capital.dc.a.service.dc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockSector {
    private String name;
    private List<Stock> stocks = new ArrayList<>();

    public void addStock(Stock stock) {
        this.stocks.add(stock);
    }

    public List<String> getCodes() {
        return stocks.stream().map(Stock::getCode).collect(Collectors.toList());
    }
}
