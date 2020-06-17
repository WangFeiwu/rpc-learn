package discovery;

import java.io.Serializable;

/**
 * @Author wfw
 * @Date 2020/06/16 19:32
 */
public class ServiceInfo implements Serializable {
    private static final long serialVersionUID = -5722784212848870062L;
    private String name;
    private String protocol;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
