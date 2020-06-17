package discovery;

import java.util.List;

/**
 * @Author wfw
 * @Date 2020/06/16 19:47
 */
public interface ServiceInfoDiscoverer {
    List<ServiceInfo> getServiceInfo(String name);
}
