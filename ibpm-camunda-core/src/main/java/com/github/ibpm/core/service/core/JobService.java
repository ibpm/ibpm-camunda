package com.github.ibpm.core.service.core;

import com.github.ibpm.common.param.core.job.JobArgListParam;
import com.github.ibpm.common.param.core.job.JobNameParam;
import com.github.ibpm.common.result.core.arg.Arg;
import com.github.ibpm.common.result.core.job.ArgAllocatedResult;
import com.github.ibpm.common.result.core.job.Job;
import com.github.ibpm.config.util.BeanUtil;
import com.github.ibpm.core.dao.core.JobMapper;
import com.github.ibpm.sys.service.BaseServiceAdapter;
import com.github.ibpm.sys.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public Map<String, Object> listArg(JobArgListParam param) {
        Map<String, Object> paramMap = BeanUtil.bean2Map(param);
        PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
        Page<ArgAllocatedResult> list = (Page<ArgAllocatedResult>) mapper.listArg(paramMap);
        return PageUtil.toResultMap(list);
    }

    public Job get(String jobName) {
        return mapper.get(BeanUtil.bean2Map(new JobNameParam().setJobName(jobName)));
    }

    public List<Arg> getArg(String jobName) {
        return mapper.getArg(BeanUtil.bean2Map(new JobNameParam().setJobName(jobName)));
    }

    public String getJsonArg(JobNameParam jobNameParam) {
        Map<String, Object> map = getArgsAsMap(jobNameParam.getJobName());
        return map.isEmpty() ? null : BeanUtil.bean2JsonStr(map);
    }

    @Autowired
    private ArgService argService;

    public Map<String, Object> getArgsAsMap(String jobName) {
        List<Arg> args = getArg(jobName);
        return argService.transArgsToMap(args);
    }

}
