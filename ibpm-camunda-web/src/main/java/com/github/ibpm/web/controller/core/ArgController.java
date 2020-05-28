package com.github.ibpm.web.controller.core;

import com.github.ibpm.common.constant.APIPath;
import com.github.ibpm.common.param.core.arg.ArgListParam;
import com.github.ibpm.common.param.core.arg.ArgNameParam;
import com.github.ibpm.common.param.core.arg.ArgNamesParam;
import com.github.ibpm.common.param.core.arg.ArgSaveParam;
import com.github.ibpm.common.param.core.arg.ext.TradeDateArgParam;
import com.github.ibpm.common.result.CommonResult;
import com.github.ibpm.common.result.core.arg.Arg;
import com.github.ibpm.core.service.core.ArgService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name ="Arg")
@RestController
public class ArgController {

    @Autowired
    private ArgService service;

    @Operation(summary = "Add arg", parameters = {@Parameter(name = "param", required = true)})
    @PostMapping(value = APIPath.ArgPath.$)
    public CommonResult<Arg> add(@RequestBody ArgSaveParam param) {
        return CommonResult.putResult(service.add(param));
    }

    @Operation(summary = "Update arg", parameters = {@Parameter(name = "param", required = true)})
    @PutMapping(value = APIPath.ArgPath.$)
    public CommonResult<Arg> update(@RequestBody ArgSaveParam param) {
        return CommonResult.putResult(service.update(param));
    }

    @Operation(summary = "Get arg", parameters = {@Parameter(name = "param", required = true)})
    @GetMapping(value = APIPath.ArgPath.GET)
    public CommonResult<Arg> get(ArgNameParam param) {
        return CommonResult.putResult(service.get(param));
    }

    @Operation(summary = "List args", parameters = {@Parameter(name = "param", required = true)})
    @GetMapping(APIPath.ArgPath.$)
    public CommonResult<Map<String, Object>> list(ArgListParam param) {
        return CommonResult.putResult(service.list(param));
    }

    @Operation(summary = "Remove args", parameters = {@Parameter(name = "param", required = true)})
    @DeleteMapping(value = APIPath.ArgPath.$)
    public CommonResult<Void> remove(@RequestBody ArgNamesParam param) {
        return CommonResult.putResult(service.remove(param));
    }

    @Operation(summary = "Get trade date", parameters = {@Parameter(name = "param", required = true)})
    @GetMapping(value = APIPath.ArgPath.GET_TRADE_DATE)
    public CommonResult<Integer> getTradeDate(TradeDateArgParam param) {
        return CommonResult.putResult(service.getTradeDate(param));
    }

}
