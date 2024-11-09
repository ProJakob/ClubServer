package me.devjakob.clubserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import me.devjakob.clubserver.api.BasicHandler;
import me.devjakob.clubserver.protocol.*;
import me.devjakob.clubserver.protocol.codec.PacketDecoder;
import me.devjakob.clubserver.protocol.codec.PacketEncoder;
import me.devjakob.clubserver.protocol.codec.PacketSplitter;
import me.devjakob.clubserver.protocol.codec.VarIntLengthFieldPrepender;
import me.devjakob.clubserver.protocol.handler.PacketHandlerHandshake;
import me.devjakob.clubserver.protocol.packet.Packets;

public class Main {

    public static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static final boolean EPOLL = Epoll.isAvailable();

    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = EPOLL ? new EpollEventLoopGroup(1) : new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();

        Constants.EVENTS.register(new BasicHandler());

        Packets.dump();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(Epoll.isAvailable() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.attr(ProtocolAttributes.CONNECTION_INFO).setIfAbsent(new ConnectionInfo());

                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("splitter", new PacketSplitter())
                                    .addLast("decoder", new PacketDecoder());

                            pipeline.addLast("prepender", new VarIntLengthFieldPrepender());
                            pipeline.addLast("encoder", new PacketEncoder());

                            pipeline.addLast("handler", new PacketHandlerHandshake());
                        }
                    });

            ChannelFuture f = b.bind(Constants.PORT).sync();
            if(f.isSuccess()) {
                Constants.LOG.info("Server started on port {}", Constants.PORT);
            } else {
                Constants.LOG.error("Server failed to start on port {}", Constants.PORT);
            }
            KeepAliveTask.start();
            f.channel().closeFuture().sync();
            KeepAliveTask.stop();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}
