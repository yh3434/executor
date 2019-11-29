package netty.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoClientHandler extends ChannelInboundHandlerAdapter {
    private final ByteBuf firstMessage;

    public EchoClientHandler() {
        this.firstMessage = Unpooled.buffer(EchoClient.SIZE);

        for (int i = 0; i < this.firstMessage.capacity(); ++i) {
            System.out.println("firstMessage");
            this.firstMessage.writeByte((byte) i);
        }

    }

    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(this.firstMessage);
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ctx.write(msg);
    }

    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
