package org.jxch.capital.breath.a.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StockKLines {
    private String code;
    private List<StockKLine> kLines;
}
