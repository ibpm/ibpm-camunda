package com.github.ibpm.config.dao;

import com.github.ibpm.config.entity.ApiLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ApiLogMapper {

    void add(ApiLog model);
}
