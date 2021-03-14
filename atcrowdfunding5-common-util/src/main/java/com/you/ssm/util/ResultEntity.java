package com.you.ssm.util;

/**
 * @author 游斌
 * @create 2020-07-01  23:15
 */
//用于统一项目中所有 Ajax 请求的返回值类型
public class ResultEntity<T> {
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";
    public static final String NO_MESSAGE = "NO_MESSAGE";
    public static final String NO_DATA = "NO_DATA";

    /*** 返回操作结果为成功，不带数据 * @return */
    public static <E> ResultEntity<E> successWithoutData() {
        return new ResultEntity<E>(SUCCESS, NO_MESSAGE, null);
    }

    /*** 返回操作结果为成功，携带数据 * @param data * @return */
    public static <E> ResultEntity<E> successWithData(E data) {
        return new ResultEntity<E>(SUCCESS, NO_MESSAGE, data);
    }

    /*** 返回操作结果为失败，不带数据 * @param message * @return */
    public static <E> ResultEntity<E> failed(String message) {
        return new ResultEntity<E>(FAILED, message, null);
    }

    // 用来保存请求是否成功或失败的  请求成功 success   失败  fail
    private String operationResult;
    // 用来保存请求失败出现异常的消息
    private String operationMessage;
    // 返回的数据
    private T queryData;

    public ResultEntity() {
    }

    public ResultEntity(String operationResult, String operationMessage, T queryData) {
        super();
        this.operationResult = operationResult;
        this.operationMessage = operationMessage;
        this.queryData = queryData;
    }

    @Override
    public String toString() {
        return "AjaxResultEntity [operationResult=" + operationResult + ", operationMessage=" + operationMessage + ", queryData=" + queryData + "]";
    }


    public String getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(String operationResult) {
        this.operationResult = operationResult;
    }

    public String getOperationMessage() {
        return operationMessage;
    }

    public void setOperationMessage(String operationMessage) {
        this.operationMessage = operationMessage;
    }

    public T getQueryData() {
        return queryData;
    }

    public void setQueryData(T queryData) {
        this.queryData = queryData;
    }

    public static void main(String[] args) {
        System.out.println("111111 = " + 111111);
    }
}