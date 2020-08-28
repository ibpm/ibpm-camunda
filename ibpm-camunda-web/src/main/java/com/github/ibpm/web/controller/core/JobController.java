package com.github.ibpm.web.controller.core;

import com.github.ibpm.common.constant.APIPath;
import com.github.ibpm.common.param.core.job.*;
import com.github.ibpm.common.result.CommonResult;
import com.github.ibpm.common.result.core.job.Job;
import com.github.ibpm.common.result.core.job.JobWithVersionResult;
import com.github.ibpm.core.service.core.JobService;
import com.github.ibpm.web.service.core.JobOperatedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Tag(name = "Job")
@RestController
public class JobController {
	
	@Autowired
	private JobService jobService;

	@Autowired
	private JobOperatedService jobOperatedService;

	@Operation(summary = "List jobs", parameters = {@Parameter(name = "param", required = true)})
	@GetMapping(APIPath.JobPath.$)
	public CommonResult<Map<String, Object>> list(JobListParam param) {
		return CommonResult.putResult(jobOperatedService.list(param));
	}

	@Operation(summary = "Add job", parameters = {@Parameter(name = "param", required = true)})
	@PostMapping(APIPath.JobPath.$)
	public CommonResult<Job> add(@RequestBody JobSaveParam param) {
		return CommonResult.putResult(jobOperatedService.add(param));
	}

	@Operation(summary = "Update job", parameters = {@Parameter(name = "param", required = true)})
	@PutMapping(APIPath.JobPath.$)
	public CommonResult<Job> update(@RequestBody JobSaveParam param) {
		return CommonResult.putResult(jobOperatedService.update(param));
	}

	@Operation(summary = "Update job content", parameters = {@Parameter(name = "param", required = true)})
	@PutMapping(APIPath.JobPath.CONTENT)
	public CommonResult<Void> updateContent(@RequestBody JobContentSaveParam param) {
		return CommonResult.putResult(jobOperatedService.updateContent(param));
	}

	@Operation(summary = "Remove jobs", parameters = {@Parameter(name = "param", required = true)})
	@DeleteMapping(APIPath.JobPath.$)
	public CommonResult<Void> remove(@RequestBody JobNamesParam param) {
		return CommonResult.putResult(jobOperatedService.remove(param));
	}

	@Operation(summary = "Enable job", parameters = {@Parameter(name = "param", required = true)})
	@PutMapping(APIPath.JobPath.ENABLE)
	public CommonResult<Void> enable(@RequestBody JobNamesParam param) {
		return CommonResult.putResult(jobOperatedService.enable(param));
	}

	@Operation(summary = "Disable job", parameters = {@Parameter(name = "param", required = true)})
	@PutMapping(APIPath.JobPath.DISABLE)
	public CommonResult<Void> disable(@RequestBody JobNamesParam param) {
		return CommonResult.putResult(jobOperatedService.disable(param));
	}

	@Operation(summary = "Get job", parameters = {@Parameter(name = "param", required = true)})
	@GetMapping(APIPath.JobPath.GET)
	public CommonResult<Job> get(JobNameParam param) {
		return CommonResult.putResult(jobService.get(param));
	}

	@Operation(summary = "Get job content", parameters = {@Parameter(name = "param", required = true)})
	@GetMapping(APIPath.JobPath.CONTENT)
    public CommonResult<String> getContent(JobNameWithDefinitionParam param) {
		return CommonResult.putResult(jobOperatedService.getContent(param));
	}

	@Operation(summary = "List jobs by versions", parameters = {@Parameter(name = "param", required = true)})
	@GetMapping(APIPath.JobPath.VERSIONS)
	public CommonResult<List<JobWithVersionResult>> listVersions(JobNameParam param) {
		return CommonResult.putResult(jobOperatedService.versions(param));
	}

	@Operation(summary = "Publish job to engine", parameters = {@Parameter(name = "param", required = true)})
	@PutMapping(APIPath.JobPath.PUBLISH)
	public CommonResult<Void> publish(@RequestBody JobNamesParam param) {
		return CommonResult.putResult(jobOperatedService.publish(param));
	}

	@Operation(summary = "Copy job", parameters = {@Parameter(name = "param", required = true)})
	@PutMapping(APIPath.JobPath.COPY)
	public CommonResult<Job> copy(@RequestBody JobCopyParam param) {
		return CommonResult.putResult(jobOperatedService.copy(param));
	}

	@Operation(summary = "Exchange current reversion to the latest reversion", parameters = {@Parameter(name = "param", required = true)})
	@PutMapping(APIPath.JobPath.EXCHANGE)
    public CommonResult<Void> exchange(@RequestBody JobNameWithDefinitionParam param) {
		return CommonResult.putResult(jobOperatedService.exchange(param));
	}

	@Operation(summary = "Manual start process(es)", parameters = {@Parameter(name = "param", required = true)})
	@PostMapping(APIPath.JobPath.START)
	public CommonResult<String> start(@RequestBody JobNamesParam param) {
		return CommonResult.putResult(jobOperatedService.start(param));
	}

	@Operation(summary = "Export model", parameters = {@Parameter(name = "param", required = true)})
	@GetMapping(APIPath.JobPath.EXPORT_MODEL)
	public void exportModel(HttpServletResponse response, JobExportParam param) {
		jobOperatedService.exportModel(response, param);
	}

	@Operation(summary = "Import model", parameters = {@Parameter(name = "file", required = true)})
	@PostMapping(APIPath.JobPath.IMPORT_MODEL)
	public CommonResult<Void> importModel(MultipartFile file) {
		return CommonResult.putResult(jobOperatedService.importModel(file));
	}

}
