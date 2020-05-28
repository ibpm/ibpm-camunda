package com.github.ibpm.web.ext.app;

import com.github.ibpm.common.constant.CommonConstants;
import com.github.ibpm.common.exception.RTException;
import com.github.ibpm.common.result.CommonResult;

public class CrossSystemHandler {

    protected void preHandleResult(CommonResult commonResult) {
        if (commonResult.getCode() != CommonConstants.OK) {
            throw new RTException(commonResult.getCode(), commonResult.getMsg());
        }
    }
}
