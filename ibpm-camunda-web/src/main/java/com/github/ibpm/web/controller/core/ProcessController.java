package com.github.ibpm.web.controller.core;

import com.github.ibpm.common.constant.APIPath;
import com.github.ibpm.common.param.core.process.*;
import com.github.ibpm.common.result.CommonResult;
import com.github.ibpm.common.result.core.process.ProcessModel;
import com.github.ibpm.common.result.core.process.ProcessWithVersionResult;
import com.github.ibpm.core.service.core.ProcessService;
import com.github.ibpm.web.service.core.ProcessOperatedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Tag(name = "Process")
@RestController
public class ProcessController {
	
	@Autowired
	private ProcessService processService;

	@Autowired
	private ProcessOperatedService processOperatedService;

	@Operation(summary = "List process(es)", parameters = {@Parameter(name = "param", required = true)})
	@GetMapping(APIPath.ProcessPath.$)
	public CommonResult<Map<String, Object>> list(ProcessListParam param) {
		return CommonResult.putResult(processOperatedService.list(param));
	}

	@Operation(summary = "Add process", parameters = {@Parameter(name = "param", required = true)})
	@PostMapping(APIPath.ProcessPath.$)
	public CommonResult<ProcessModel> add(@RequestBody ProcessSaveParam param) {
		return CommonResult.putResult(processOperatedService.add(param));
	}

	@Operation(summary = "Update process", parameters = {@Parameter(name = "param", required = true)})
	@PutMapping(APIPath.ProcessPath.$)
	public CommonResult<ProcessModel> update(@RequestBody ProcessSaveParam param) {
		return CommonResult.putResult(processOperatedService.update(param));
	}

	@Operation(summary = "Update process content", parameters = {@Parameter(name = "param", required = true)})
	@PutMapping(APIPath.ProcessPath.CONTENT)
	public CommonResult<Void> updateContent(@RequestBody ProcessContentSaveParam param) {
		return CommonResult.putResult(processOperatedService.updateContent(param));
	}

	@Operation(summary = "Remove process(es)", parameters = {@Parameter(name = "param", required = true)})
	@DeleteMapping(APIPath.ProcessPath.$)
	public CommonResult<Void> remove(@RequestBody ProcessDefinitionKeysParam param) {
		return CommonResult.putResult(processOperatedService.remove(param));
	}

	@Operation(summary = "Enable process", parameters = {@Parameter(name = "param", required = true)})
	@PutMapping(APIPath.ProcessPath.ENABLE)
	public CommonResult<Void> enable(@RequestBody ProcessDefinitionKeysParam param) {
		return CommonResult.putResult(processOperatedService.enable(param));
	}

	@Operation(summary = "Disable process", parameters = {@Parameter(name = "param", required = true)})
	@PutMapping(APIPath.ProcessPath.DISABLE)
	public CommonResult<Void> disable(@RequestBody ProcessDefinitionKeysParam param) {
		return CommonResult.putResult(processOperatedService.disable(param));
	}

	@Operation(summary = "Get process", parameters = {@Parameter(name = "param", required = true)})
	@GetMapping(APIPath.ProcessPath.GET)
	public CommonResult<ProcessModel> get(ProcessDefinitionKeyParam param) {
		return CommonResult.putResult(processService.get(param));
	}

	@Operation(summary = "Get process content", parameters = {@Parameter(name = "param", required = true)})
	@GetMapping(APIPath.ProcessPath.CONTENT)
    public CommonResult<String> getContent(ProcessDefinitionKeyWithDefinitionParam param) {
		return CommonResult.putResult(processOperatedService.getContent(param));
	}

	@Operation(summary = "List process(es) by versions", parameters = {@Parameter(name = "param", required = true)})
	@GetMapping(APIPath.ProcessPath.VERSIONS)
	public CommonResult<List<ProcessWithVersionResult>> listVersions(ProcessDefinitionKeyParam param) {
		return CommonResult.putResult(processOperatedService.versions(param));
	}

	@Operation(summary = "Publish process to engine", parameters = {@Parameter(name = "param", required = true)})
	@PutMapping(APIPath.ProcessPath.PUBLISH)
	public CommonResult<Void> publish(@RequestBody ProcessDefinitionKeysParam param) {
		return CommonResult.putResult(processOperatedService.publish(param));
	}

	@Operation(summary = "Copy process", parameters = {@Parameter(name = "param", required = true)})
	@PutMapping(APIPath.ProcessPath.COPY)
	public CommonResult<ProcessModel> copy(@RequestBody ProcessCopyParam param) {
		return CommonResult.putResult(processOperatedService.copy(param));
	}

	@Operation(summary = "Exchange current reversion to the latest reversion", parameters = {@Parameter(name = "param", required = true)})
	@PutMapping(APIPath.ProcessPath.EXCHANGE)
    public CommonResult<Void> exchange(@RequestBody ProcessDefinitionKeyWithDefinitionParam param) {
		return CommonResult.putResult(processOperatedService.exchange(param));
	}

	@Operation(summary = "Manual start process(es)", parameters = {@Parameter(name = "param", required = true)})
	@PostMapping(APIPath.ProcessPath.START)
	public CommonResult<String> start(@RequestBody ProcessDefinitionKeysParam param) {
		return CommonResult.putResult(processOperatedService.start(param));
	}

	@Operation(summary = "Export model", parameters = {@Parameter(name = "param", required = true)})
	@GetMapping(APIPath.ProcessPath.EXPORT_MODEL)
	public void exportModel(HttpServletResponse response, ProcessExportParam param) {
		processOperatedService.exportModel(response, param);
	}

	@Operation(summary = "Import model", parameters = {@Parameter(name = "file", required = true)})
	@PostMapping(APIPath.ProcessPath.IMPORT_MODEL)
	public CommonResult<Void> importModel(MultipartFile file) {
		return CommonResult.putResult(processOperatedService.importModel(file));
	}

}
