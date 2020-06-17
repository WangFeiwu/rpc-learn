package common.protocol;

import java.io.*;

/**
 * @Author wfw
 * @Date 2020/06/17 09:03
 */
public class JavaSerializeMessageProtocol implements MessageProtocol {

    private byte[] serialize(Object object) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(object);

        return byteArrayOutputStream.toByteArray();
    }

    @Override

    public byte[] marshallingRequest(Request request) throws IOException {
        return this.serialize(request);
    }

    @Override
    public Request unmarshallingRequest(byte[] data) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(data));
        return (Request) objectInputStream.readObject();
    }

    @Override
    public byte[] marshallingResponse(Response response) throws IOException {
        return this.serialize(response);
    }

    @Override
    public Response unmarshallingResponse(byte[] data) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(data));
        return (Response) objectInputStream.readObject();
    }
}
