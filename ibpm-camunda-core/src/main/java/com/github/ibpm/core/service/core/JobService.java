package com.github.ibpm.core.service.core;

import com.github.ibpm.common.param.core.job.JobNameParam;
import com.github.ibpm.common.result.core.job.Job;
import com.github.ibpm.config.util.BeanUtil;
import com.github.ibpm.core.dao.core.JobMapper;
import com.github.ibpm.sys.service.BaseServiceAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@Transactional
public class JobService extends BaseServiceAdapter {

    @Autowired
    private JobMapper mapper;

    /**
     * get job by jobName
     * @param param
     * @return
     */
    public Job get(JobNameParam param) {
        Map<String, Object> paramMap = BeanUtil.bean2Map(param);
        return mapper.get(paramMap);
    }

    public Job get(String jobName) {
        return mapper.get(BeanUtil.bean2Map(new JobNameParam().setJobName(jobName)));
    }

}
