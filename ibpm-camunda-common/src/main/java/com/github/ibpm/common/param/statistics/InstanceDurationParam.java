package com.github.ibpm.common.param.statistics;

import com.github.ibpm.common.param.PageSortParam;
import com.github.ibpm.common.param.PageSortParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;


@Getter
@Setter
@ToString
public class InstanceDurationParam extends PageSortParam {

    protected String jobName;

    protected Long lowerStartTime;

    protected Long upperStartTime;

    protected String firedSource;

    @Override
    public void preHandle() {
        firedSource = StringUtils.trimToNull(firedSource);
    }

    @Override
    public String validate() {
        if (StringUtils.isBlank(jobName)) {
            return "6000";
        }
        return null;
    }
}
