package org.jxch.capital.breath.a.mapstruct;


import org.jxch.capital.breath.a.domain.BreathA;
import org.jxch.capital.breath.a.model.StockSectorScore;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface BreathAConvert {
    BreathAConvert INSTANCE = Mappers.getMapper(BreathAConvert.class);

    @Mappings({
            @Mapping(target = "date", source = "date"),
            @Mapping(target = "sector", source = "sector"),
            @Mapping(target = "score", source = "score"),
    })
    BreathA toBreathA(StockSectorScore data);

    default List<BreathA> toBreathA(@NonNull List<StockSectorScore> vos) {
        return vos.stream().map(this::toBreathA).collect(Collectors.toList());
    }
}
