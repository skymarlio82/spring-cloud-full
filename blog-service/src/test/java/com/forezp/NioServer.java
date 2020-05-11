
package com.forezp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NioServer {

    private static SelectorProvider provider = SelectorProvider.provider();
    private static Selector selector = null;
    private static ServerSocketChannel server = null;

    private static Map<Integer, String> tokens = new HashMap<Integer, String>();

    private static void accept() throws IOException {
        SocketChannel channel = null;
        try {
            // 接受连接
            channel = server.accept();
            // 非阻塞模式
            channel.configureBlocking(false);
            // 监听读就绪
            channel.register(selector, SelectionKey.OP_READ, null);
            System.out.println("channel.hash=" + channel.hashCode());
        } catch (IOException e) {
            if (channel != null) {
                channel.close();
            }
        }
    }

    private static int read(SocketChannel channel) throws IOException {
        try {
            // 分配HeapByteBuffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            // 直到没有数据 || buffer满
            int len = channel.read(buffer);
            if (len > 0) {
                // buffer.array()：取HeapByteBuffer中的原始byte[]
                String str_read = new String(buffer.array(), 0, len, Charset.forName("UTF-8"));
                tokens.put(channel.hashCode(), str_read);
                System.out.println("tokenId=" + channel.hashCode() + ", clientData=" + str_read);
            }
            return len;
        } catch (IOException e) {
            if (channel != null) {
                channel.close();
            }
            return -1;
        }
    }

    private static void write(SocketChannel channel, String msg) throws IOException {
        try {
            byte[] bytes = msg.getBytes(Charset.forName("UTF-8"));
            // 分配HeapByteBuffer
            ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
            buffer.put(bytes);
            // 切换为读模式
            buffer.flip();
            channel.write(buffer);
        } catch (IOException e) {
            if (channel != null) {
                channel.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        try {
            selector = provider.openSelector();
            server = provider.openServerSocketChannel();
            // 非阻塞模式
            server.configureBlocking(false);
            // 注册
            SelectionKey key = server.register(selector, 0, null);
            // 绑定成功
            if (server.bind(new InetSocketAddress(8080)).socket().isBound()) {
                // 监听连接请求
                key.interestOps(SelectionKey.OP_ACCEPT);
            }
            while (true) {
                selector.select(); // 监听就绪事件
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    key = it.next();
                    // 从已选择键集中移除key
                    it.remove();
                    // 连接请求到来
                    if (key.isAcceptable()) {
                        System.out.println("accept ...");
                        accept();
                    } else {
                        SocketChannel channel = (SocketChannel) key.channel();
                        // 写就绪
                        if (key.isWritable()) {
                            System.out.println("write ...");
                            write(channel, channel.hashCode() + " : " + tokens.get(channel.hashCode()));
                            // 取消写就绪，否则会一直触发写就绪（写就绪为代码触发）
                            key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
                            // 关闭channel（key将失效）
                            key.channel().close();
                        }
                        // key有效（避免在写就绪时关闭了channel或者取消了key） && 读就绪
                        if (key.isValid() && key.isReadable()) {
                            System.out.println("read ...");
                            int len = read(channel);
                            if (len >= 0) {
                                // 写就绪，准备写数据
                                key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
                            } else if (len < 0) { // 客户端已关闭socket
                                // 关闭channel（key将失效）
                                channel.close();
                            }
                        }
                    }
                }
            }
        } finally {
            if (server != null) {
                server.close();
            }
            if (selector != null) {
                selector.close();
            }
        }
    }
}