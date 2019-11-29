package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOServer {
    private Selector selector;

    public void initServer(int port) throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.bind(new InetSocketAddress(port));
        this.selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("line 19,serverChannel is: " + serverChannel);
    }

    public void listen() throws IOException {
        System.out.println("服务端启动成功...");
        while (true) {
            //当注册事件到达时返回，否则阻塞
            final int select = selector.select();
            System.out.println("line 24: " + select);
            final Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                final SelectionKey selectionKey = iterator.next();
                //删除已经选择的key以防重复
                iterator.remove();
                //客户端请求连接事件
                if (selectionKey.isAcceptable()) {
                    final ServerSocketChannel serverChannel = (ServerSocketChannel) selectionKey.channel();
                    System.out.println("line 38,serverChannel is: " + serverChannel);
                    //获得和客户端的连接通道
                    final SocketChannel socketChannel = serverChannel.accept();
                    System.out.println("line 41,socketChanel is : " + socketChannel);
                    //设置成非阻塞
                    socketChannel.configureBlocking(false);
                    //给客户端发送信息
                    socketChannel.write(ByteBuffer.wrap("服务端在第44行接收到了请求".getBytes()));
                    //和客户端连接成功后，为了可以接收到客户端的信息，需要给通道设置读的权限
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) {//获得了可读事件
                    read(selectionKey);
                }
            }

        }
    }

    private void read(SelectionKey selectionKey) {
        try {
            final SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            System.out.println("line 58,socketChannel is: " + socketChannel);
            //创建读缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(10);
            socketChannel.read(buffer);
            final byte[] data = buffer.array();
            String msg = new String(data).trim();
            System.out.println("服务端收到信息,line 64.... ：" + msg);
            ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
            socketChannel.write(outBuffer);// 将消息回送给客户端
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        //启动服务端
        NIOServer server = new NIOServer();
        server.initServer(8000);
        server.listen();
    }
}
