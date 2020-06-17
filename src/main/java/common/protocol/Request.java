package common.protocol;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author wfw
 * @Date 2020/06/17 08:38
 */
public class Request implements Serializable {
    private static final long serialVersionUID = -9000912256161111885L;
    private String serviceName;
    private String method;
    private Map<String, String> headers = new HashMap<>();
    private Class<?>[] parameterType;
    private Object[] parameters;

    public String getHeader(String name) {
        return this.headers == null ? null : this.headers.get(name);
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Class<?>[] getParameterType() {
        return parameterType;
    }

    public void setParameterType(Class<?>[] parameterType) {
        this.parameterType = parameterType;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
