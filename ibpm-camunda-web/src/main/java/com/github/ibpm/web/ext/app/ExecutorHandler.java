package com.github.ibpm.web.ext.app;

import com.github.ibpm.common.constant.CommonConstants;
import com.github.ibpm.common.exception.RTException;
import com.github.ibpm.common.result.CommonResult;
import com.github.ibpm.common.result.core.instance.Instance;
import com.github.ibpm.config.property.AppProperties;
import com.github.ibpm.config.util.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * handle interaction from web to executor
 */
@Service
@Transactional
public class ExecutorHandler extends CrossSystemHandler {

    @Autowired
    private AppProperties appProperties;

    public void terminate(Instance instance) {
        //call(instance.getExecutorUri(), APIPath.ExecutorPath.TERMINATE, new IdParam().setId(instance.getId()));
    }

    private void call(String baseUrl, String apiSubPath, Object param) {
        CommonResult result = WebClient.builder().build()
                .method(HttpMethod.POST)
                .uri(buildFullPath(baseUrl, apiSubPath))
                .contentType(MediaType.APPLICATION_JSON)
                .header(CommonConstants.token, ServletUtil.getHeader(CommonConstants.token))
                .bodyValue(param)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, resp -> Mono.error(new RTException(resp.rawStatusCode(), resp.statusCode().getReasonPhrase())))
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
        preHandleResult(result);
    }

    private String buildFullPath(String baseUrl, String apiPath) {
        return baseUrl + appProperties.getExecutor().getContextPath() + apiPath;
    }

}
