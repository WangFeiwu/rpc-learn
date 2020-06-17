package server;

import common.protocol.MessageProtocol;
import common.protocol.Request;
import common.protocol.Response;
import common.protocol.Status;
import server.register.ServiceObject;
import server.register.ServiceRegister;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author wfw
 * @Date 2020/06/17 10:16
 */
public class RequestHandler {
    private MessageProtocol protocol;
    private ServiceRegister serviceRegister;

    public RequestHandler(MessageProtocol protocol, ServiceRegister serviceRegister) {
        super();
        this.protocol = protocol;
        this.serviceRegister = serviceRegister;
    }

    public byte[] handleRequest(byte[] data) throws IOException, ClassNotFoundException {
        // 1.解组消息
        Request request = this.protocol.unmarshallingRequest(data);

        // 2.查找服务对象
        ServiceObject serviceObject = this.serviceRegister.getServiceObject(request.getServiceName());

        Response response = null;

        if (serviceObject == null) {
            response = new Response(Status.NOT_FOUND);
        } else {
            // 3.反射调用对应的过程方法
            try {
                Method method = serviceObject.getInterf().getMethod(request.getMethod(), request.getParameterType());
                Object returnValue = method.invoke(serviceObject.getObject(), request.getParameters());
                response = new Response(Status.SUCCESS);
                response.setReturnValue(returnValue);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException e) {
                response = new Response(Status.ERROR);
                response.setException(e);
            }
        }

        // 4.编组响应消息
        return this.protocol.marshallingResponse(response);
    }

    public MessageProtocol getProtocol() {
        return protocol;
    }

    public void setProtocol(MessageProtocol protocol) {
        this.protocol = protocol;
    }

    public ServiceRegister getServiceRegister() {
        return serviceRegister;
    }

    public void setServiceRegister(ServiceRegister serviceRegister) {
        this.serviceRegister = serviceRegister;
    }
}
