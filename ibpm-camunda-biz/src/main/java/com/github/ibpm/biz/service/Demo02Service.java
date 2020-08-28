package com.github.ibpm.biz.service;

import com.github.ibpm.biz.dao.Demo02Mapper;
import com.github.ibpm.biz.model.Demo02Data;
import com.github.ibpm.config.util.BeanUtil;
import com.github.ibpm.engine.service.BizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("demo02")
@Transactional
public class Demo02Service implements BizService {

    @Autowired
    private Demo02Mapper mapper;

    public Object add(Map<String, Object> paramMap) {
        Demo02Data bizData = BeanUtil.map2Bean(Demo02Data.class, paramMap);
        mapper.add(bizData);
        return bizData;
    }

    public Object update(Map<String, Object> paramMap) {
        Demo02Data bizData = BeanUtil.map2Bean(Demo02Data.class, paramMap);
        mapper.update(bizData);
        return bizData;
    }

    public Demo02Data get(Map<String, Object> paramMap) {
        Demo02Data bizData = BeanUtil.map2Bean(Demo02Data.class, paramMap);
        return mapper.get(bizData.getBusinessKey());
    }
}
