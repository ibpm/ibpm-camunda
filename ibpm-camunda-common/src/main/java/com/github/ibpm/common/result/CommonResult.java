package com.github.ibpm.common.result;

import com.github.ibpm.common.constant.CommonConstants;
import com.github.ibpm.common.exception.RTException;
import com.github.ibpm.common.property.StatusProperty;
import com.github.ibpm.common.constant.CommonConstants;
import com.github.ibpm.common.exception.RTException;
import com.github.ibpm.common.property.StatusProperty;

public class CommonResult<T> {

	/**
	 * app code
	 */
	protected int code;

	/**
	 * success/tip/error msg
	 */
	protected String msg;

	/**
	 * response time in timestamp
	 */
	protected Long handleTime;

	/**
	 * time unit is second
	 */
	protected String duration;

	/**
	 * data body
	 */
	protected T result;

	public CommonResult() {
		this.handleTime = System.currentTimeMillis();
	}

    /**
     * @param code
     * @param msg
     * @return
     */
    public static CommonResult<Void> put(int code, String msg) {
        CommonResult<Void> r = new CommonResult<>();
        r.code = code;
        r.msg = msg;
        return r;
    }

    /**
     * @param code
     * @return
     */
	public static CommonResult<Void> put(int code) {
		return put(code,
				StatusProperty.getValue(code));
	}

    /**
     * @param code
     * @param extraMsg
     * @return
     */
	public static CommonResult putAdd(int code, String extraMsg){
	    return put(code,
				StatusProperty.getValue(code)
						+ ":" +
						StatusProperty.getValue(extraMsg));
    }

    /**
     * 200
     * @return
     */
	public static CommonResult<Void> ok() {
		return put(CommonConstants.OK);
	}

    /**
     * @param t
     * @param <T>
     * @return
     */
	public static <T> CommonResult<T> putResult(T t) {
		CommonResult r = ok();
		r.result = t;
		return r;
	}

    /**
     * code=500
     * @param msg
     * @return
     */
	public static CommonResult error(String msg){
		return CommonResult.putAdd(500, msg);
    }

    /**
     * RTException -> CommonResult
     * @param rte
     * @return
     */
	public static CommonResult error(RTException rte){
	    return CommonResult.put(rte.getCode(), rte.getMsg());
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(Long handleTime) {
		this.handleTime = handleTime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "BaseResult{" +
				"code=" + code +
				", msg='" + msg + '\'' +
				", handleTime='" + handleTime + '\'' +
				", duration='" + duration + '\'' +
				", result=" + result +
				'}';
	}
}
