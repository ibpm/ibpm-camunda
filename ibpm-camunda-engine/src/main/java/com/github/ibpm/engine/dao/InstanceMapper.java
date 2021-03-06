package com.github.ibpm.engine.dao;

import com.github.ibpm.common.result.core.instance.Instance;
import com.github.ibpm.common.result.core.instance.InstanceAct;
import com.github.ibpm.common.result.core.instance.InstanceWithChildren;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface InstanceMapper {

    Instance getById(String id);

    Instance getByInstId(String processInstanceId);

    int count(Map<String, Object> paramMap);

    void addExecution(Instance instance);

    void addInstance(Instance instance);

    void updateExecution(Instance instance);

    void updateInstance(Instance instance);

    void deleteExecution(String processInstanceId);

    void deleteInstance(String processInstanceId);

    List<InstanceWithChildren> listInstance(Map<String, Object> paramMap);

    List<InstanceWithChildren> listTodo(Map<String, Object> paramMap);

    List<InstanceWithChildren> listDoing(Map<String, Object> paramMap);

    List<InstanceWithChildren> listDone(Map<String, Object> paramMap);

    int countProcessChildren(String superProcessInstanceId);

    int countRetriedChildren(String parentId);

    int countInstance(Map<String, Object> paramMap);

    List<InstanceWithChildren> listProcessChildren(String superProcessInstanceId);

    List<InstanceWithChildren> listRetriedChildren(String parentId);

    void addAct(InstanceAct instanceAct);

    void updateAct(InstanceAct instanceAct);

    List<InstanceAct> listAct(Map<String, Object> paramMap);

    void updateDone(Instance instance);

    List<Instance> listRunningOfExecutor(String executorAddress);

    List<Instance> getByIds(List<String> ids);

}
