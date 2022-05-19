package org.jxch.capital.dc.a.service.breath.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class StockSectorScore {
    private String sector;
    private Date date;
    private int score;
}
