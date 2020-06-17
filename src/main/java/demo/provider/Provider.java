package demo.provider;

import common.protocol.JavaSerializeMessageProtocol;
import demo.DemoService;
import server.NettyRpcServer;
import server.RequestHandler;
import server.RpcServer;
import server.register.ServiceObject;
import server.register.ServiceRegister;
import server.register.ZookeeperExportServiceRegister;
import util.PropertiesUtils;

import java.io.IOException;

/**
 * @Author wfw
 * @Date 2020/06/17 12:22
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(PropertiesUtils.getProperties("rpc.port"));
        String protocol = PropertiesUtils.getProperties("rpc.protocol");

        // 服务注册
        ServiceRegister serviceRegister = new ZookeeperExportServiceRegister();
        DemoService demoService = new DemoServiceImpl();
        ServiceObject serviceObject = new ServiceObject(DemoService.class.getName(), DemoService.class, demoService);
        serviceRegister.register(serviceObject, protocol, port);

        RequestHandler requestHandler = new RequestHandler(new JavaSerializeMessageProtocol(), serviceRegister);

        RpcServer server = new NettyRpcServer(port, protocol, requestHandler);
        server.start();
        // 按任意键退出
        System.in.read();
        server.stop();
    }
}
