package com.github.ibpm.web.dao.statistics;

import com.github.ibpm.common.result.core.instance.Instance;
import com.github.ibpm.common.result.statistics.analysis.AppPlan;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface AnalysisMapper {

    List<AppPlan> getNextFireTimeWithJobName(Map<String, Object> paramMap);

    List<Instance> getInstanceDuration(Map<String, Object> paramMap);
}
