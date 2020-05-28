package com.github.ibpm.web.dao.statistics;

import com.github.ibpm.common.result.MapResult;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface CountMapper {

    List<MapResult<String, Integer>> getUserCount();

    List<MapResult<String, Integer>> getJobCount(Map<String, Object> paramMap);

    List<MapResult<String, Integer>> getInstanceCount(Map<String, Object> paramMap);
}
