package org.jxch.capital.dc.a.service.dc.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class StockKLines {
    private String code;
    private List<StockKLine> kLines = new ArrayList<>();

    public StockKLines(String code) {
        this.code = code;
    }
}
