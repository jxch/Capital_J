package org.jxch.capital.breath.a.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockSectorScore {
    private String sector;
    private Date date;
    private int score;
}
