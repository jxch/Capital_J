package org.jxch.capital.breath.a.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockMAKLines {
    private String code;
    private List<StockMAKLine> makLines;
}
