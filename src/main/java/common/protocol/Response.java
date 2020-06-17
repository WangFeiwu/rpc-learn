package common.protocol;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author wfw
 * @Date 2020/06/17 08:43
 */
public class Response implements Serializable {
    private static final long serialVersionUID = 7372996780154452640L;
    private Status status;
    private Map<String, String> headers = new HashMap<>();
    private Class<?> returnType;
    private Object returnValue;
    private Exception exception;

    public Response(Status status) {
        this.status = status;
    }

    public String getHeader(String name) {
        return this.headers == null ? null : this.headers.get(name);
    }

    public void addHeader(String name, String value) {
        this.headers.put(name, value);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
