package net.ryian.flow.common.response;

/**
 * @author allenwc
 * @date 2024/5/18 17:55
 */
public enum ResponseEnum {

    /**
     * ok
     */
    OK(200, "ok"),
    NOT_READY(202, "not ready"),;

    private final Integer code;

    private final String msg;

    public Integer value() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ResponseEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResponseEnum{" + "code='" + code + '\'' + ", msg='" + msg + '\'' + "} " + super.toString();
    }

}
