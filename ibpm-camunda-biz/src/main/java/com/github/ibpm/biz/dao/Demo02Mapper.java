package com.github.ibpm.biz.dao;

import com.github.ibpm.biz.model.Demo02Data;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface Demo02Mapper {

    void add(Demo02Data bizData);

    void update(Demo02Data bizData);

    Demo02Data get(String businessKey);

}
