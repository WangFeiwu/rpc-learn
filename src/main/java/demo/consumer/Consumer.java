package demo.consumer;

import client.ClientStubProxyFactory;
import client.net.NettyNetClient;
import common.protocol.JavaSerializeMessageProtocol;
import common.protocol.MessageProtocol;
import demo.DemoService;
import discovery.ZookeeperServiceInfoDiscoverer;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author wfw
 * @Date 2020/06/17 12:29
 */
public class Consumer {
    public static void main(String[] args) {
        ClientStubProxyFactory clientStubProxyFactory = new ClientStubProxyFactory();
        // 设置服务发现者
        clientStubProxyFactory.setSid(new ZookeeperServiceInfoDiscoverer());

        // 设置支持的协议
        Map<String, MessageProtocol> supportMessageProtocols = new HashMap<>();
        supportMessageProtocols.put("my-protocol", new JavaSerializeMessageProtocol());
        clientStubProxyFactory.setSupportMessageProtocols(supportMessageProtocols);

        // 设置网络层实现
        clientStubProxyFactory.setNetClient(new NettyNetClient());

        // 获取远程服务代理
        DemoService demoService = clientStubProxyFactory.getProxy(DemoService.class);
        // 执行远程方法
        String hello = demoService.sayHello("hahahaha");

        // 显示调用结果
        System.out.println(hello);
        System.out.println(demoService.multiPoint(new Point(5, 10), 2));
    }
}
