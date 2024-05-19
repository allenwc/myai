package net.ryian.flow.common.response;

import java.io.Serializable;

import lombok.Data;

/**
 * @author allenwc
 * @date 2024/5/18 17:52
 */
@Data
public class ServerResponseEntity<T> implements Serializable {

    /**
     * 状态码
     */
    private Integer status;

    /**
     * 信息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    public static <T> ServerResponseEntity<T> success(T data) {
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setData(data);
        serverResponseEntity.setStatus(ResponseEnum.OK.value());
        return serverResponseEntity;
    }

    public static <T> ServerResponseEntity<T> success(T data,String msg) {
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setData(data);
        serverResponseEntity.setMsg(msg);
        serverResponseEntity.setStatus(ResponseEnum.OK.value());
        return serverResponseEntity;
    }

    public static <T> ServerResponseEntity<T> success() {
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setStatus(ResponseEnum.OK.value());
        serverResponseEntity.setMsg(ResponseEnum.OK.getMsg());
        return serverResponseEntity;
    }

}
