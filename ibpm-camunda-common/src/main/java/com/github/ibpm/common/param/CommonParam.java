package com.github.ibpm.common.param;

/**
 * common methods implemented by every param
 */
public interface CommonParam {

    /**
     * @return error code or msg
     */
    String validate();

    /**
     * handle some param before call controller
     */
    default void preHandle() {}

}
