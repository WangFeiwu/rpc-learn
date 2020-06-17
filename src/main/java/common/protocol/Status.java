package common.protocol;

/**
 * @Author wfw
 * @Date 2020/06/17 08:43
 */
public enum Status {
    /**
     * 成功
     */
    SUCCESS(200,"SUCCESS"),
    /**
     * 500错误
     */
    ERROR(500, "ERROR"),
    /**
     * 404错误
     */
    NOT_FOUND(404, "NOT FOUND"),
    ;

    /**
     * 错误码
     */
    private int code;

    /**
     * 错误信息
     */
    private String message;

    private Status(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
