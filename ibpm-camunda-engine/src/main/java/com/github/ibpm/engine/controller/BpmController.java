package com.github.ibpm.engine.controller;

import com.github.ibpm.common.constant.APIPath;
import com.github.ibpm.common.result.CommonResult;
import com.github.ibpm.engine.service.BpmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class BpmController {

    @Autowired
    private BpmService service;

    @GetMapping(APIPath.BpmPath.GET)
    public CommonResult<Object> get(@RequestParam Map<String, Object> paramMap) {
        return CommonResult.putResult(service.get(paramMap));
    }

    @PostMapping(APIPath.BpmPath.DRAFT)
    public CommonResult<Void> draft(@RequestBody Map<String, Object> paramMap) {
        return CommonResult.putResult(service.draft(paramMap));
    }

    @PostMapping(APIPath.BpmPath.CREATE)
    public CommonResult<Void> create(@RequestBody Map<String, Object> paramMap) {
        return CommonResult.putResult(service.create(paramMap));
    }

    @PostMapping(APIPath.BpmPath.APPROVE)
    public CommonResult<Void> approve(@RequestBody Map<String, Object> paramMap) {
        return CommonResult.putResult(service.approve(paramMap));
    }

}
