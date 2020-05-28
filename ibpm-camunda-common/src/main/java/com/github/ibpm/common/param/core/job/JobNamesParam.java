package com.github.ibpm.common.param.core.job;

import com.github.ibpm.common.param.CommonParam;
import lombok.ToString;

import java.util.List;

@ToString
public class JobNamesParam implements CommonParam {

    protected List<String> jobNames;

    public String validate() {
        if(jobNames == null || jobNames.isEmpty()){
            return "6000";
        }
        return null;
    }

    public List<String> getJobNames() {
        return jobNames;
    }

    public JobNamesParam setJobNames(List<String> jobNames) {
        this.jobNames = jobNames;
        return this;
    }
}
