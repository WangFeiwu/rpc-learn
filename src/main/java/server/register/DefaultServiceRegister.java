package server.register;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author wfw
 * @Date 2020/06/17 10:22
 */
public class DefaultServiceRegister implements ServiceRegister {

    private Map<String, ServiceObject> serviceObjectMap = new HashMap<>();

    @Override
    public void register(ServiceObject serviceObject, String protocol, int port) throws UnknownHostException {
        if (serviceObject == null) {
            throw new IllegalArgumentException("参数不能为空");
        }

        this.serviceObjectMap.put(serviceObject.getName(), serviceObject);
    }

    @Override
    public ServiceObject getServiceObject(String name) {
        return this.serviceObjectMap.get(name);
    }

}
