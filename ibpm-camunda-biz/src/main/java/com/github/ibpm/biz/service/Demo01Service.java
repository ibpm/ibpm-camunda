package com.github.ibpm.biz.service;

import com.github.ibpm.biz.dao.Demo01Mapper;
import com.github.ibpm.biz.model.Demo01Data;
import com.github.ibpm.config.util.BeanUtil;
import com.github.ibpm.engine.service.BizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("demo01")
@Transactional
public class Demo01Service implements BizService {

    @Autowired
    private Demo01Mapper mapper;

    public Object add(Map<String, Object> paramMap) {
        Demo01Data bizData = BeanUtil.map2Bean(Demo01Data.class, paramMap);
        mapper.add(bizData);
        return bizData;
    }

    public Object update(Map<String, Object> paramMap) {
        Demo01Data bizData = BeanUtil.map2Bean(Demo01Data.class, paramMap);
        mapper.update(bizData);
        return bizData;
    }

    public Demo01Data get(Map<String, Object> paramMap) {
        Demo01Data bizData = BeanUtil.map2Bean(Demo01Data.class, paramMap);
        return mapper.get(bizData.getBusinessKey());
    }
}
