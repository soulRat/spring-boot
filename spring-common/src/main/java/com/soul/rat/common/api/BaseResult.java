package com.soul.rat.common.api;


import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

/**
 * 顶级response
 *
 * @author zhujx
 */
public class BaseResult<T> extends HashMap<String, Object> {

    public static <T> BaseResult<T> success(T t) {
        BaseResult<T> success = success();
        success.put("data", t);
        return success;
    }

    public static <T> BaseResult<T> success() {
        return bizCode(BaseBizCodeEnum.SUCCESS);
    }

    public static <T> BaseResult<T> failed() {
        return bizCode(BaseBizCodeEnum.FAILED);
    }

    public static <T> BaseResult<T> failed(BizCode bizCode) {
        return bizCode(bizCode);
    }

    public static <T> BaseResult<T> failed(String code, String message) {
        return bizCode(new BizCode() {
            @Override
            public String getCode() {
                return code;
            }

            @Override
            public String getMessage() {
                return message;
            }
        });
    }


    private static <T> BaseResult<T> bizCode(BizCode bizCode) {
        BaseResult<T> result = new BaseResult<>();
        result.put("code", bizCode.getCode());
        result.put("message", bizCode.getMessage());
        return result;
    }

    public T getData() {
        Object data = this.get("data");
        return (T) data;
    }

    public String getCode() {
        Object code = this.get("code");
        return (String) code;
    }

    public String getMessage() {
        Object message = this.get("message");
        return (String) message;
    }

    public BaseResult<T> and(String key, String value) {
        this.put(key, value);
        return this;
    }

    public BaseResult<T> msg(String message) {
        this.put("message", message);
        return this;
    }

    public BaseResult<T> code(String code) {
        this.put("code", code);
        return this;
    }

    public BaseResult<T> data(T t) {
        this.put("data", t);
        return this;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }


}