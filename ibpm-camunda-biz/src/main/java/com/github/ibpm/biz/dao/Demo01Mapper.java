package com.github.ibpm.biz.dao;

import com.github.ibpm.biz.model.Demo01Data;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface Demo01Mapper {

    void add(Demo01Data bizData);

    void update(Demo01Data bizData);

    Demo01Data get(String businessKey);

}
