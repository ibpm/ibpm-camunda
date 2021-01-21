package com.github.ibpm.core.dao.core;

import com.github.ibpm.common.result.core.process.ProcessModel;
import com.github.ibpm.common.result.core.process.ProcessWithVersionResult;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ProcessMapper {

    void add(ProcessModel model);

    void update(ProcessModel model);

    ProcessModel get(Map<String, Object> paramMap);

    void delete(Map<String, Object> paramMap);

    List<ProcessModel> list(Map<String, Object> paramMap);

    void updateContent(ProcessModel processModel);

    List<ProcessWithVersionResult> versions(Map<String, Object> paramMap);

    void updateStatus(Map<String, Object> paramMap);

}
