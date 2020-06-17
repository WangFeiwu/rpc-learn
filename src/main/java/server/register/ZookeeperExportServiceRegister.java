package server.register;

import com.fasterxml.jackson.core.JsonProcessingException;
import common.serializer.Charset;
import common.serializer.MyZkSerializer;
import discovery.ServiceInfo;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.JsonUtil;
import util.PropertiesUtils;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;

/**
 * @Author wfw
 * @Date 2020/06/17 10:25
 */
public class ZookeeperExportServiceRegister extends DefaultServiceRegister implements ServiceRegister {

    private static final Logger log = LoggerFactory.getLogger(ZookeeperExportServiceRegister.class);

    private ZkClient client;

    private String centerRootPath = "/Rpc-learn";

    public ZookeeperExportServiceRegister() {
        String addr = PropertiesUtils.getProperties("zk.address");
        client = new ZkClient(addr);
        client.setZkSerializer(new MyZkSerializer());
    }

    @Override
    public void register(ServiceObject serviceObject, String protocol, int port) throws UnknownHostException {
        super.register(serviceObject, protocol, port);

        String host = InetAddress.getLocalHost().getHostAddress();
        String address = host + ":" + port;

        log.info("注册地址：{}", address);

        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setAddress(address);
        serviceInfo.setName(serviceObject.getInterf().getName());
        serviceInfo.setProtocol(protocol);

        this.exportService(serviceInfo);
    }

    private void exportService(ServiceInfo serviceResource) {
        String serviceName = serviceResource.getName();
        String uri = null;
        try {
            uri = JsonUtil.toJson(serviceResource);
            uri = URLEncoder.encode(uri, Charset.UTF8);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (uri == null) {
            log.info("uri为空");
            return;
        }
        String servicePath = centerRootPath + "/" + serviceName + "/service";
        if (!client.exists(servicePath)) {
            client.createPersistent(servicePath, true);
        }
        String uriPath = servicePath + "/" + uri;
        if (client.exists(uriPath)) {
            client.delete(uriPath);
        }
        client.createEphemeral(uriPath);
    }

}
