package com.github.ibpm.engine.service.instance;

import com.github.ibpm.common.constant.CommonConstants;
import com.github.ibpm.common.exception.RTException;
import com.github.ibpm.common.param.core.instance.InstanceActParam;
import com.github.ibpm.common.param.core.instance.InstanceGetParam;
import com.github.ibpm.common.param.core.instance.InstanceListParam;
import com.github.ibpm.common.param.core.instance.TaskIdParam;
import com.github.ibpm.common.result.core.instance.Instance;
import com.github.ibpm.common.result.core.instance.InstanceAct;
import com.github.ibpm.common.result.core.instance.InstanceWithChildren;
import com.github.ibpm.config.util.BeanUtil;
import com.github.ibpm.engine.dao.InstanceMapper;
import com.github.ibpm.engine.model.ProcessData;
import com.github.ibpm.engine.service.CommandService;
import com.github.ibpm.engine.service.IbpmEngineService;
import com.github.ibpm.sys.holder.UserHolder;
import com.github.ibpm.sys.service.BaseServiceAdapter;
import com.github.ibpm.sys.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.impl.cfg.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional/*(propagation = Propagation.REQUIRES_NEW)*/
public class InstanceService extends BaseServiceAdapter {

    @Autowired
    private InstanceMapper mapper;
    /*
    @Autowired
    private NoticeService noticeService;

    @Autowired
    private UserService userService;

    @Autowired
    private ScheduledExecutorService scheduledExecutorService;
    */

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private CommandService commandService;

    @Autowired
    private IbpmEngineService ibpmEngineService;

    public Instance get(String id) {
        return mapper.getById(id);
    }

    public List<Instance> getByIds(List<String> ids) {
        return mapper.getByIds(ids);
    }

    public Instance getByInstId(String processInstanceId) {
        return mapper.getByInstId(processInstanceId);
    }

    public int count(InstanceListParam param) {
        return mapper.count(BeanUtil.bean2Map(param));
    }

    public Map<String, Object> listTodo(InstanceListParam param) {
        Map<String, Object> paramMap = BeanUtil.bean2Map(param);
        paramMap.put(CommonConstants.userName, UserHolder.get().getUserName());
        PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
        Page<InstanceWithChildren> list = (Page<InstanceWithChildren>) mapper.listTodo(paramMap);
        if (param.isListChildren()) {
            list.parallelStream().forEach(item -> {
                if (item.getProcessInstanceId() != null) {
                    item.setHasChildren(mapper.countProcessChildren(item.getProcessInstanceId()) > 0);
                }
            });
        }
        return PageUtil.toResultMap(list);
    }

    public Map<String, Object> listDoing(InstanceListParam param) {
        Map<String, Object> paramMap = BeanUtil.bean2Map(param);
        paramMap.put(CommonConstants.userName, UserHolder.get().getUserName());
        PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
        Page<InstanceWithChildren> list = (Page<InstanceWithChildren>) mapper.listDoing(paramMap);
        if (param.isListChildren()) {
            list.parallelStream().forEach(item -> {
                if (item.getProcessInstanceId() != null) {
                    item.setHasChildren(mapper.countProcessChildren(item.getProcessInstanceId()) > 0);
                }
            });
        }
        return PageUtil.toResultMap(list);
    }

    public Map<String, Object> listDone(InstanceListParam param) {
        Map<String, Object> paramMap = BeanUtil.bean2Map(param);
        paramMap.put(CommonConstants.userName, UserHolder.get().getUserName());
        PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
        Page<InstanceWithChildren> list = (Page<InstanceWithChildren>) mapper.listDone(paramMap);
        if (param.isListChildren()) {
            list.parallelStream().forEach(item -> {
                if (item.getProcessInstanceId() != null) {
                    item.setHasChildren(mapper.countProcessChildren(item.getProcessInstanceId()) > 0);
                }
            });
        }
        return PageUtil.toResultMap(list);
    }

    public Map<String, Object> listMonitor(InstanceListParam param) {
        Map<String, Object> paramMap = BeanUtil.bean2Map(param);
        PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
        Page<InstanceWithChildren> list = (Page<InstanceWithChildren>) mapper.listInstance(paramMap);
        if (param.isListChildren()) {
            list.parallelStream().forEach(item -> {
                if (item.getProcessInstanceId() != null) {
                    item.setHasChildren(mapper.countProcessChildren(item.getProcessInstanceId()) > 0);
                }
            });
        }
        return PageUtil.toResultMap(list);
    }

    public List<InstanceWithChildren> listMonitorChildren(InstanceGetParam param) {
        List<InstanceWithChildren> list = mapper.listProcessChildren(param.getProcessInstanceId());
        list.parallelStream().forEach(item -> {
            if (item.getProcessInstanceId() != null) {
                item.setHasChildren(mapper.countProcessChildren(item.getProcessInstanceId()) > 0);
            }
        });
        return list;
    }

    public List<InstanceAct> listAct(InstanceActParam param) {
        Map<String, Object> paramMap = BeanUtil.bean2Map(param);
        return mapper.listAct(paramMap);
    }

    public Instance add(Instance instance) {
        mapper.addExecution(instance);
        mapper.addInstance(instance);
        noticeWithInstance(instance);
        return instance;
    }

    public void update(Instance instance) {
        mapper.updateExecution(instance);
        mapper.updateInstance(instance);
        noticeWithInstance(instance);
    }

    public void updateDone(Instance instance) {
        mapper.updateDone(instance);
        deleteExecution(instance.getProcessInstanceId());
        noticeWithInstance(instance);
    }

    public void deleteExecution(String processInstanceId) {
        mapper.deleteExecution(processInstanceId);
    }

    public void deleteInstance(String processInstanceId) {
        mapper.deleteInstance(processInstanceId);
    }

    public void addAct(InstanceAct instanceAct) {
        mapper.addAct(instanceAct);
    }

    public void updateAct(InstanceAct instanceAct) {
        mapper.updateAct(instanceAct);
    }

    public List<Instance> listRunningOfExecutor(String executorAddress) {
        return mapper.listRunningOfExecutor(executorAddress);
    }

    public int countInstance(Map<String, Object> paramMap) {
        return mapper.countInstance(paramMap);
    }

    public void noticeWithInstance(Instance instance) {
        /*if (InstanceStatus.FAILURE.getStatus() == instance.getStatus()) {
            String noticeTime = DateUtil.format(new Date(), DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS);
            scheduledExecutorService.schedule(() -> {
                try {
                    sendNotice(instance, noticeTime);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }, 5 + (int) (Math.random() * 3), TimeUnit.SECONDS);
        }*/
    }
    /*

    private void sendNotice(Instance instance, String noticeTime) {
        User user = userService.get(new UserNameParam());
        MessageBean messageBean = buildMessageBean(
                instance,
                user,
                noticeTime);
        String sendConfig = user.getSendConfig();
        if (StringUtils.isNotBlank(sendConfig)) {
            for (int i = 0; i < sendConfig.length(); i++) {
                if (sendConfig.charAt(i) != '0') {
                    Sender sender = noticeService.getSenderMap().get(i);
                    if (sender != null) {
                        try {
                            sender.send(messageBean);
                        } catch (Exception e) {
                            log.error(e.getMessage(), e);
                        }
                    } else {
                        log.error("send is missing:{} of {}", i, sendConfig);
                    }
                }
            }
        }
    }

    private MessageBean buildMessageBean(Instance instance, User user, String now) {
        StringBuilder actIdSB = new StringBuilder();
        StringBuilder actNameSB = new StringBuilder();
        StringBuilder bizUriSB = new StringBuilder();
        StringBuilder logKeySB = new StringBuilder();
        StringBuilder errorMsgSB = new StringBuilder();
        String alarmPosition = CommonConstants.EMPTY;
        if (StringUtils.isNotBlank(instance.getProcessInstanceId())) {
            List<InstanceAct> instanceActs = listAct(new InstanceActParam().setProcessInstanceId(instance.getProcessInstanceId()));
            for (InstanceAct instanceAct : instanceActs) {
                if (instanceAct.getStatus() == InstanceStatus.FAILURE.getStatus()
                        && (StringUtils.isNotBlank(instanceAct.getLogKey())
                        || StringUtils.isNotBlank(instanceAct.getLogText()))) {
                    actIdSB.append(StringUtils.trimToEmpty(instanceAct.getActId())).append(CommonConstants.COMMA);
                    if (instanceAct.getActName() != null) {
                        actNameSB.append(instanceAct.getActName()).append(CommonConstants.COMMA);
                    }
                    if (instanceAct.getBizUri() != null) {
                        bizUriSB.append(instanceAct.getBizUri()).append(CommonConstants.COMMA);
                    }
                    if (instanceAct.getLogKey() != null) {
                        logKeySB.append(instanceAct.getLogKey()).append(CommonConstants.COMMA);
                    }
                    if (StringUtils.isBlank(instanceAct.getLogText())) {
                        errorMsgSB.append(instanceAct.getIncidentMsg()).append(CommonConstants.BR);
                    } else {
                        errorMsgSB
                                .append(instanceAct.getLogText()).append(CommonConstants.BR)
                                .append(instanceAct.getIncidentMsg()).append(CommonConstants.BR);
                    }
                }
            }
            if (actIdSB.length() >= 1) {
                actIdSB.deleteCharAt(actIdSB.length() - 1);
            }
            if (actNameSB.length() >= 1) {
                actNameSB.deleteCharAt(actNameSB.length() - 1);
            }
            if (bizUriSB.length() >= 1) {
                bizUriSB.deleteCharAt(bizUriSB.length() - 1);
                if (bizUriSB.length() >= 1) {
                    alarmPosition = TipProperty.getValue(10053);
                } else {
                    alarmPosition = TipProperty.getValue(10054);
                }
            }
            if (logKeySB.length() >= 1) {
                logKeySB.deleteCharAt(logKeySB.length() - 1);
            }
        } else {
            alarmPosition = TipProperty.getValue(10052);
            logKeySB.append(instance.getCode());
            errorMsgSB.append(instance.getMsg());
        }
        String subject = MessageFormat.format(TipProperty.getValue(10050),
                instance.getProcessDefinitionKey(),
                instance.getDisplayName());
        String content = MessageFormat.format(TipProperty.getValue(10051),
                now,
                user.getUserName(),
                user.getDisplayName(),
                instance.getProcessDefinitionKey(),
                instance.getDisplayName(),
                instance.getStartTime() == null ? CommonConstants.EMPTY : DateUtil.format(instance.getStartTime(), DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS),
                actIdSB,
                actNameSB,
                instance.getSchedulerUri(),
                StringUtils.trimToEmpty(instance.getExecutorUri()),
                bizUriSB,
                alarmPosition,
                logKeySB,
                errorMsgSB
                );
        return new MessageBean()
                .setTo(user)
                .setSubject(subject)
                .setContent(content);
    }
    */

    public void add(ProcessData processData) {
        Instance instance = new Instance()
                .setProcessInstanceId(processData.getProcessInstanceId())
                .setProcessDefinitionKey(processData.getProcessDefinitionKey())
                .setTitle(processData.getTitle())
                .setStatus(processData.getStatus());
        mapper.addExecution(instance);
        mapper.addInstance(instance);
    }

    public void update(ProcessData processData) {
        Instance instance = mapper.getByInstId(processData.getProcessInstanceId());
        if (instance == null) {
            throw new RTException(6100, processData.getProcessInstanceId());
        }
        instance.setTitle(processData.getTitle());
        mapper.updateExecution(instance);
        mapper.updateInstance(instance);
    }

    public String openForm(TaskIdParam param) {
        return ibpmEngineService.getTaskFormKey(param.getTaskId());
    }
}
