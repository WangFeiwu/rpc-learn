package discovery;

import com.fasterxml.jackson.core.JsonProcessingException;
import common.serializer.MyZkSerializer;
import org.I0Itec.zkclient.ZkClient;
import common.serializer.Charset;
import util.JsonUtil;
import util.PropertiesUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author wfw
 * @Date 2020/06/16 19:49
 */
public class ZookeeperServiceInfoDiscoverer implements ServiceInfoDiscoverer {

    ZkClient client;

    private String centerRootPath = "/Rpc-learn";

    public ZookeeperServiceInfoDiscoverer() {
        String addr = PropertiesUtils.getProperties("zk.address");
        client = new ZkClient(addr);
        client.setZkSerializer(new MyZkSerializer());
    }

    @Override
    public List<ServiceInfo> getServiceInfo(String name) {
        String servicePath = centerRootPath + "/" + name + "/service";
        List<String> children = client.getChildren(servicePath);
        List<ServiceInfo> resources = new ArrayList<>();
        for (String child : children) {
            try {
                String serviceInfoStr = URLDecoder.decode(child, Charset.UTF8);
                ServiceInfo serviceInfo = JsonUtil.fromJson(serviceInfoStr, ServiceInfo.class);
                resources.add(serviceInfo);
            } catch (UnsupportedEncodingException | JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return resources;
    }
}
