package com.github.ibpm.core.service.tool;

import com.github.ibpm.common.constant.APIPath;
import com.github.ibpm.common.constant.CommonConstants;
import com.github.ibpm.common.enums.PredefinedDateArgType;
import com.github.ibpm.common.exception.RTException;
import com.github.ibpm.common.param.tool.server.ServerInfoGetParam;
import com.github.ibpm.common.result.CommonResult;
import com.github.ibpm.common.result.core.tool.ServerInfoResult;
import com.github.ibpm.config.property.AppProperties;
import com.github.ibpm.config.util.ServletUtil;
import com.github.ibpm.core.ext.server.*;
import com.github.ibpm.sys.service.BaseServiceAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import oshi.SystemInfo;
import reactor.core.publisher.Mono;

import java.util.*;

@Slf4j
@Service
public class ToolService extends BaseServiceAdapter {

    @Autowired
    private AppProperties appProperties;

    public String[] listTimeZone() {
        return TimeZone.getAvailableIDs();
    }

    public Map<String, Object> requestServerInfo(ServerInfoGetParam param) {
        String url;
        if ("scheduler".equals(param.getSource())) {
            url = param.getAddress() + appProperties.getScheduler().getContextPath() + APIPath.CommonPath.SERVER_INFO;
        } else if ("executor".equals(param.getSource())) {
            url = param.getAddress() + appProperties.getExecutor().getContextPath() + APIPath.CommonPath.SERVER_INFO;
        } else {
            throw new RTException(HttpStatus.INTERNAL_SERVER_ERROR.value(), new IllegalArgumentException(param.getSource()));
        }
        CommonResult<Map<String, Object>> commonResult = WebClient.builder().build()
                .post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header(CommonConstants.token, ServletUtil.getHeader(CommonConstants.token))
                .retrieve()
                .onStatus(s -> s == HttpStatus.UNAUTHORIZED, resp -> Mono.error(new RTException(HttpStatus.UNAUTHORIZED.value(), resp.statusCode().getReasonPhrase())))
                .bodyToMono(CommonResult.class)
                .doOnError(Exception.class, err -> {
                    if (err instanceof RTException) {
                        throw (RTException) err;
                    } else if (err.getMessage().contains("Connection refused")) {
                        throw new RTException(3009, err);
                    }
                    throw new RTException(err);
                })
                .block();
        if (commonResult.getCode() != 200) {
            throw new RTException(commonResult.getCode(), commonResult.getMsg());
        }
        return commonResult.getResult();
    }

    public ServerInfoResult responseServerInfo() {
        SystemInfo systemInfo = new SystemInfo();
        return new ServerInfoResult()
                .setCpu(CpuUtil.getCpuStatus(systemInfo))
                .setMemory(MemoryUtil.getMemoryStatus(systemInfo))
                .setMachine(MachineUtil.getMachineInfo())
                .setJvm(JvmUtil.getJvmStatus())
                .setResource(ResourceUtil.getResourceInfo())
                .setFile(FileStoreUtil.getFileStoreInfo(systemInfo));
    }

    public Date getCurrentTime() {
        return new Date();
    }

    public List<String> listTradeDateUnit() {
        List<String> values = new ArrayList<>(10);
        for (PredefinedDateArgType type : PredefinedDateArgType.values()) {
            values.add(type.getValue());
        }
        return values;
    }
}
