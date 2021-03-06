package server;

/**
 * @Author wfw
 * @Date 2020/06/17 10:58
 */
public abstract class RpcServer {
    protected int port;
    protected String protocol;
    protected RequestHandler handler;

    public RpcServer(int port, String protocol, RequestHandler handler) {
        super();
        this.port = port;
        this.protocol = protocol;
        this.handler = handler;
    }

    /**
     * 开启服务
     */
    public abstract void start();

    /**
     * 停止服务
     */
    public abstract void stop();

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public RequestHandler getHandler() {
        return handler;
    }

    public void setHandler(RequestHandler handler) {
        this.handler = handler;
    }
}
