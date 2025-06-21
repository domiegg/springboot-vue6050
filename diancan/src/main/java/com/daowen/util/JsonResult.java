package com.daowen.util;


public class JsonResult<T> {

    public static JsonResult error(int stateCode, String des) {
        return JsonResult.error(stateCode, des, null);
    }

    public static JsonResult success(int stateCode, String des) {
        return JsonResult.success(stateCode, des, null);
    }

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    private int stateCode;

    private String des;

    private T data;

    public JsonResult() {

    }

    public JsonResult(int stateCode, String des, T data) {
        this.stateCode = stateCode;
        this.des = des;
        this.data = data;
    }

    public static <T> JsonResult success(int stateCode, String des, T data) {
        return new JsonResult<T>(stateCode, des, data);
    }

    public static <T> JsonResult error(int stateCode, String des, T data) {
        return new JsonResult<T>(stateCode, des, data);
    }

}