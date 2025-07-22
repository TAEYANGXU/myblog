package com.xyz.myblog.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    private int code;
    private String msg;
    private Object data;

    public static Result success() {
        Result result = new Result();
        result.setCode(200);
        result.setMsg("success");
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setCode(200);
        result.setMsg("success");
        result.setData(data);
        return result;
    }

    public static Result error(int code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}