package common.protocol;

import java.io.IOException;

/**
 * @Author wfw
 * @Date 2020/06/17 08:58
 */
public interface MessageProtocol {

    /**
     * 编组请求
     * @param request
     * @return
     */
    byte[] marshallingRequest(Request request) throws IOException;

    /**
     * 解组请求
     * @param data
     * @return
     */
    Request unmarshallingRequest(byte[] data) throws IOException, ClassNotFoundException;

    /**
     * 编组响应
     * @param response
     * @return
     */
    byte[] marshallingResponse(Response response) throws IOException;

    /**
     * 解组响应
     * @param data
     * @return
     */
    Response unmarshallingResponse(byte[] data) throws IOException, ClassNotFoundException;
}
