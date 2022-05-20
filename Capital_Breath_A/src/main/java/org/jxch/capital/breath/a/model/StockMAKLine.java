package org.jxch.capital.breath.a.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockMAKLine {
    private StockKLine stockKLine;
    private BigDecimal ma;
}
