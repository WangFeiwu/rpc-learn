package client;

import client.net.NetClient;
import common.protocol.MessageProtocol;
import common.protocol.Request;
import common.protocol.Response;
import discovery.ServiceInfo;
import discovery.ServiceInfoDiscoverer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @Author wfw
 * @Date 2020/06/16 19:26
 */
public class ClientStubProxyFactory {
    private ServiceInfoDiscoverer sid;
    private Map<String, MessageProtocol> supportMessageProtocols;
    private NetClient netClient;
    private Map<Class<?>, Object> objectCache = new HashMap<>();

    public <T> T getProxy(Class<T> interf) {
        T obj = (T) this.objectCache.get(interf);
        if (obj == null) {
            obj = (T) Proxy.newProxyInstance(interf.getClassLoader(),new Class<?>[]{interf},
                    new ClientStubInvocationHandler(interf));
            this.objectCache.put(interf, obj);
        }
        return obj;
    }

    public ServiceInfoDiscoverer getSid() {
        return sid;
    }

    public void setSid(ServiceInfoDiscoverer sid) {
        this.sid = sid;
    }

    public Map<String, MessageProtocol> getSupportMessageProtocols() {
        return supportMessageProtocols;
    }

    public void setSupportMessageProtocols(Map<String, MessageProtocol> supportMessageProtocols) {
        this.supportMessageProtocols = supportMessageProtocols;
    }

    public NetClient getNetClient() {
        return netClient;
    }

    public void setNetClient(NetClient netClient) {
        this.netClient = netClient;
    }

    private class ClientStubInvocationHandler implements InvocationHandler{

        private Class<?> interf;

        private Random random = new Random();

        public ClientStubInvocationHandler(Class<?> interf) {
            super();
            this.interf = interf;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("toString")) {
                return proxy.getClass().toString();
            }

            if (method.getName().equals("hashCode")) {
                return proxy.getClass().hashCode();
            }

            // 1.获得服务信息
            String serviceName = this.interf.getName();
            List<ServiceInfo> serviceInfos = sid.getServiceInfo(serviceName);

            if (serviceInfos == null || serviceInfos.size() == 0) {
                throw new Exception("远程服务不存在！");
            }

            // 随机选择一个服务提供者（软负载均衡）
            ServiceInfo serviceInfo = serviceInfos.get(random.nextInt(serviceInfos.size()));

            // 2、构造request对象
            Request request = new Request();
            request.setServiceName(serviceInfo.getName());
            request.setMethod(method.getName());
            request.setParameterType(method.getParameterTypes());
            request.setParameters(args);

            // 3、协议层编组
            // 获得该方法对应的协议
            MessageProtocol protocol = supportMessageProtocols.get(serviceInfo.getProtocol());
            // 编组请求
            byte[] data = protocol.marshallingRequest(request);

            // 4、调用网络层发送请求
            byte[] repData = netClient.sendRequest(data, serviceInfo);

            // 5、解组响应信息
            Response response = protocol.unmarshallingResponse(repData);

            // 6、结果处理
            if (response.getException() != null) {
                throw response.getException();
            }
            return response.getReturnValue();
        }

    }

}
