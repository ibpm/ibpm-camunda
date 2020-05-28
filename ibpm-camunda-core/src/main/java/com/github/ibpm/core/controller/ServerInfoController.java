package com.github.ibpm.core.controller;

import com.github.ibpm.common.constant.APIPath;
import com.github.ibpm.common.result.CommonResult;
import com.github.ibpm.common.result.core.tool.ServerInfoResult;
import com.github.ibpm.core.service.tool.ToolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name ="Server info")
@RestController
public class ServerInfoController {

    @Autowired
    private ToolService toolService;

    @Operation(summary = "Response server info")
    @PostMapping(APIPath.CommonPath.SERVER_INFO)
    public CommonResult<ServerInfoResult> responseServerInfo() {
        return CommonResult.putResult(toolService.responseServerInfo());
    }
}
