package com.github.ibpm.web.controller.instance;

import com.github.ibpm.common.constant.APIPath;
import com.github.ibpm.common.param.IdsParam;
import com.github.ibpm.common.param.core.instance.*;
import com.github.ibpm.common.result.CommonResult;
import com.github.ibpm.common.result.core.instance.InstanceAct;
import com.github.ibpm.common.result.core.instance.InstanceWithChildren;
import com.github.ibpm.engine.service.instance.InstanceService;
import com.github.ibpm.web.service.instance.InstanceOperatedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Tag(name = "Instance")
@RestController
public class InstanceController {

    @Autowired
    private InstanceService instanceService;

    @Autowired
    private InstanceOperatedService instanceOperatedService;

    @Operation(summary = "List todo", parameters = {@Parameter(name = "param", required = true)})
    @GetMapping(APIPath.InstancePath.TODO)
    public CommonResult<Map<String, Object>> listTodo(InstanceListParam param) {
        return CommonResult.putResult(instanceService.listTodo(param));
    }

    @Operation(summary = "List doing", parameters = {@Parameter(name = "param", required = true)})
    @GetMapping(APIPath.InstancePath.DOING)
    public CommonResult<Map<String, Object>> listDoing(InstanceListParam param) {
        return CommonResult.putResult(instanceService.listDoing(param));
    }

    @Operation(summary = "List done", parameters = {@Parameter(name = "param", required = true)})
    @GetMapping(APIPath.InstancePath.DONE)
    public CommonResult<Map<String, Object>> listDone(InstanceListParam param) {
        return CommonResult.putResult(instanceService.listDone(param));
    }

    @Operation(summary = "Open Form", parameters = {@Parameter(name = "param", required = true)})
    @GetMapping(APIPath.InstancePath.OPEN_FORM)
    public CommonResult<String> openForm(TaskIdParam param) {
        return CommonResult.putResult(instanceService.openForm(param));
    }

    @Operation(summary = "List process instances", parameters = {@Parameter(name = "param", required = true)})
    @GetMapping(APIPath.InstancePath.$)
    public CommonResult<Map<String, Object>> listMonitor(InstanceListParam param) {
        return CommonResult.putResult(instanceService.listMonitor(param));
    }

    @Operation(summary = "List process instances children", parameters = {@Parameter(name = "param", required = true)})
    @GetMapping(APIPath.InstancePath.CHILDREN)
    public CommonResult<List<InstanceWithChildren>> listMonitor(InstanceGetParam param) {
        return CommonResult.putResult(instanceService.listMonitorChildren(param));
    }

    @Operation(summary = "List all activity nodes of one instance", parameters = {@Parameter(name = "param", required = true)})
    @GetMapping(APIPath.InstancePath.ACT)
    public CommonResult<List<InstanceAct>> listAct(InstanceActParam param) {
        return CommonResult.putResult(instanceService.listAct(param));
    }

    @Operation(summary = "Terminate", parameters = {@Parameter(name = "param", required = true)})
    @PostMapping(value = APIPath.InstancePath.TERMINATE)
    public CommonResult<Void> terminate(@RequestBody IdsParam param) {
        return CommonResult.putResult(instanceOperatedService.terminate(param));
    }

}
