package client.net;

import discovery.ServiceInfo;

/**
 * @Author wfw
 * @Date 2020/06/17 09:13
 */
public interface NetClient {
    byte[] sendRequest(byte[] data, ServiceInfo serviceInfo) throws Throwable;
}
