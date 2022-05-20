package org.jxch.capital.breath.a.mapper;

import org.apache.ibatis.annotations.Update;
import org.jxch.capital.breath.a.domain.BreathA;
import org.jxch.capital.breath.a.domain.BreathAKey;

import java.util.List;

public interface BreathADao {
    int deleteByPrimaryKey(BreathAKey key);

    int insert(BreathA record);

    int insertSelective(BreathA record);

    BreathA selectByPrimaryKey(BreathAKey key);

    int updateByPrimaryKeySelective(BreathA record);

    int updateByPrimaryKey(BreathA record);

    int inserts(List<BreathA> records);

    @Update("TRUNCATE TABLE breath_a")
    void truncate();
}