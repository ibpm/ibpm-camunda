package com.github.ibpm.common.param.core.job;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JobNameWithJsonArgParam extends JobNameParam {

    protected JsonNode jsonData;
}
