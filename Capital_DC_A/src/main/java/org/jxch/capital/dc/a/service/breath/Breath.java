package org.jxch.capital.dc.a.service.breath;

import org.jxch.capital.dc.a.service.breath.model.StockSectorScore;

import java.util.Date;
import java.util.List;

public interface Breath {
    List<List<StockSectorScore>> breathByMA(int ma, Date start, Date end);

    default List<List<StockSectorScore>> breathByMA(Date start, Date end) {
        return breathByMA(20, start, end);
    }
}
