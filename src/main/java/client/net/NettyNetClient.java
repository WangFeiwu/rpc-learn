package client.net;

import discovery.ServiceInfo;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * @Author wfw
 * @Date 2020/06/17 09:15
 */
public class NettyNetClient implements NetClient {

    private static Logger log = LoggerFactory.getLogger(NettyNetClient.class);

    @Override
    public byte[] sendRequest(byte[] data, ServiceInfo serviceInfo) throws Throwable {

        String[] addInfoArray = serviceInfo.getAddress().split(":");

        SendHandler sendHandler = new SendHandler(data);
        byte[] respData = null;

        // 配置客户端
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY,true).
                    handler(new ChannelInitializer<SocketChannel>(){
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            ChannelPipeline channelPipeline = socketChannel.pipeline();
                            channelPipeline.addLast(sendHandler);
                        }
                    });

            // 启动客户端连接
            bootstrap.connect(addInfoArray[0], Integer.parseInt(addInfoArray[1])).sync();
            respData = (byte[]) sendHandler.rspData();
            log.info("发送请求得到响应：{}", respData);
        }finally {
            // 释放线程组资源
            group.shutdownGracefully();
        }

        return respData;
    }

    private class SendHandler extends ChannelInboundHandlerAdapter {
        private CountDownLatch countDownLatch = null;
        private Object readMsg = null;
        private byte[] data;

        public SendHandler(byte[] data) {
            countDownLatch = new CountDownLatch(1);
            this.data = data;
        }

        public Object rspData() {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return readMsg;
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            log.info("连接服务端成功：{}", ctx);
            ByteBuf reqBuf = Unpooled.buffer(data.length);
            reqBuf.writeBytes(data);
            log.info("客户端发送消息：{}", reqBuf);
            ctx.writeAndFlush(reqBuf);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            log.info("客户端读取到消息：{}", msg);
            ByteBuf msgBuf = (ByteBuf) msg;
            byte[] resp = new byte[msgBuf.readableBytes()];
            msgBuf.readBytes(resp);
            readMsg = resp;
            countDownLatch.countDown();
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            // Close the connection when an exception is raised.
            cause.printStackTrace();
            log.error("发生异常：{}", cause.getMessage());
            ctx.close();
        }
    }

}
