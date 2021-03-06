package com.github.ibpm.web.controller.tool;

import com.github.ibpm.common.constant.APIPath;
import com.github.ibpm.common.param.tool.server.ServerInfoGetParam;
import com.github.ibpm.common.result.CommonResult;
import com.github.ibpm.core.service.tool.ToolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Tag(name = "Tool")
@RestController
public class ToolController {

    @Autowired
    private ToolService service;

    @Operation(summary = "Get time zones of all")
    @GetMapping(APIPath.ToolPath.GET_TIME_ZONE)
    public CommonResult<String[]> listTimeZone() {
        return CommonResult.putResult(service.listTimeZone());
    }

    @Operation(summary = "Get server info", parameters = {@Parameter(name = "param", required = true)})
    @GetMapping(APIPath.ToolPath.GET_SERVER_INFO)
    public CommonResult<Map<String, Object>> getServerInfo(ServerInfoGetParam param) {
        return CommonResult.putResult(service.requestServerInfo(param));
    }

    @Operation(summary = "Get server time")
    @GetMapping(APIPath.ToolPath.TIME)
    public CommonResult<Date> getCurrentTime() {
        return CommonResult.putResult(service.getCurrentTime());
    }

    @Operation(summary = "Get trade date units")
    @GetMapping(APIPath.ToolPath.TRADE_DATE_UNITS)
    public CommonResult<List<String>> listTradeDateUnit() {
        return CommonResult.putResult(service.listTradeDateUnit());
    }
}
