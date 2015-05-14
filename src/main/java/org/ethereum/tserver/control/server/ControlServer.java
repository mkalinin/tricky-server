package org.ethereum.tserver.control.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author Mikhail Kalinin
 * @since 05/14/2015
 */
public class ControlServer {

    private static final int PORT = 3284; // TODO move to properties
    private static final ControlServer INSTANCE = new ControlServer();

    public static ControlServer getInstance() {
        return INSTANCE;
    }

    private ControlServer() {
    }

    public void start() throws InterruptedException {
        EventLoopGroup parentGroup = new NioEventLoopGroup(1);
        EventLoopGroup childGroup = new NioEventLoopGroup(1);

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(parentGroup, childGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ControlServerInitializer());

            b.bind(PORT).sync().channel().closeFuture().sync();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }
}
