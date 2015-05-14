package org.ethereum.tserver.control.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author Mikhail Kalinin
 * @since 05/14/2015
 */
public class ControlServerInitializer extends ChannelInitializer<SocketChannel> {

    private static final int MAX_FRAME_SIZE = 8192;

    private static final StringDecoder DECODER = new StringDecoder();
    private static final StringEncoder ENCODER = new StringEncoder();

    private static final ControlServerHandler HANDLER = new ControlServerHandler();

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // request decoder
        pipeline.addLast(new DelimiterBasedFrameDecoder(MAX_FRAME_SIZE, Delimiters.lineDelimiter()));
        pipeline.addLast(DECODER);
        pipeline.addLast(ENCODER);

        // request handler
        pipeline.addLast(HANDLER);
    }
}
