package org.jxch.capital.dc.a.service.breath;

import org.jxch.capital.dc.a.service.breath.model.StockSectorScore;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface Breath {
    List<List<StockSectorScore>> breathByMA(int ma);

    default List<List<StockSectorScore>> breathByMA() {
        return breathByMA(20);
    }
}
