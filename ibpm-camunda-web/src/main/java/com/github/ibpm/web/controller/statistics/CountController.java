package com.github.ibpm.web.controller.statistics;

import com.github.ibpm.common.constant.APIPath;
import com.github.ibpm.common.param.statistics.CountInstanceParam;
import com.github.ibpm.common.result.CommonResult;
import com.github.ibpm.common.result.MapResult;
import com.github.ibpm.web.service.statistics.CountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Count")
@RestController
public class CountController {

    @Autowired
    private CountService service;

    @GetMapping(APIPath.CountPath.USER)
    @Operation(summary = "Get user's count by status")
    public CommonResult<List<MapResult<String, Integer>>> getUserCount() {
        return CommonResult.putResult(service.getUserCount());
    }

    @GetMapping(APIPath.CountPath.JOB)
    @Operation(summary = "Get process's count by status")
    public CommonResult<List<MapResult<String, Integer>>> getProcessCount() {
        return CommonResult.putResult(service.getProcessCount());
    }

    @GetMapping(APIPath.CountPath.INSTANCE)
    @Operation(summary = "Get process instance's count by status", parameters = {@Parameter(name = "param", required = true)})
    public CommonResult<List<MapResult<String, Integer>>> getInstanceCount(CountInstanceParam param) {
        return CommonResult.putResult(service.getInstanceCount(param));
    }

}
