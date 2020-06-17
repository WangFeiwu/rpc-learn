package server.register;

import java.net.UnknownHostException;

/**
 * @Author wfw
 * @Date 2020/06/17 10:17
 */
public interface ServiceRegister {
    void register(ServiceObject serviceObject, String protocol, int port) throws UnknownHostException;

    ServiceObject getServiceObject(String name);
}
