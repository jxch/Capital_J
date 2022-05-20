package org.jxch.capital.dc.a.service.dc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    private String code;
    private String name;
    private String sector;
}
