package com.github.ibpm.config.service;

import com.github.ibpm.config.annotation.MultiDataSource;
import com.github.ibpm.config.dao.ApiLogMapper;
import com.github.ibpm.config.entity.ApiLog;
import com.github.ibpm.config.id.MyIdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApiLogService {

	@Autowired
	private MyIdGenerator myIdGenerator;

	@Autowired
	private ApiLogMapper mapper;
	
	@Transactional
	@Async
	@MultiDataSource
	public void save(ApiLog apiLog){
		if (StringUtils.length(apiLog.getParam()) > 2000) {
			apiLog.setParam(apiLog.getParam().substring(0, 2000));
		}
		if (StringUtils.length(apiLog.getResult()) > 2000) {
			apiLog.setResult(apiLog.getResult().substring(0, 2000));
		}
		if (StringUtils.length(apiLog.getMsg()) > 2000) {
			apiLog.setMsg(apiLog.getMsg().substring(0, 2000));
		}
		apiLog.setId(myIdGenerator.getNextId());
		mapper.add(apiLog);
	}
}
