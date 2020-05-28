package com.github.ibpm.core.dao.core;

import com.github.ibpm.common.result.core.arg.Arg;
import com.github.ibpm.common.result.core.job.ArgAllocatedResult;
import com.github.ibpm.common.result.core.job.Job;
import com.github.ibpm.common.result.core.job.JobWithVersionResult;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface JobMapper {

    void add(Job model);

    void update(Job model);

    Job get(Map<String, Object> paramMap);

    void delete(Map<String, Object> paramMap);

    List<Job> list(Map<String, Object> paramMap);

    void updateContent(Job job);

    List<JobWithVersionResult> versions(Map<String, Object> paramMap);

    void updateStatus(Map<String, Object> paramMap);

    List<ArgAllocatedResult> listArg(Map<String, Object> paramMap);

    List<Arg> getArg(Map<String, Object> paramMap);

    void addArg(Map<String, Object> paramMap);

    void deleteArg(Map<String, Object> paramMap);

    void addJobArg(Map<String, Object> paramMap);

    void deleteJobArg(Map<String, Object> paramMap);
}
