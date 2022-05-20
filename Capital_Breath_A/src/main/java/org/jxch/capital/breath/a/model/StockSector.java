package org.jxch.capital.breath.a.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockSector {
    private String name;
    private List<Stock> stocks;

    public List<String> getCodes() {
        return stocks.stream().map(Stock::getCode).collect(Collectors.toList());
    }
}
