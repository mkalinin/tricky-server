package org.ethereum.tserver.control.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.ethereum.tserver.control.commands.Command;
import org.ethereum.tserver.control.commands.CommandParser;
import org.ethereum.tserver.control.commands.CommandManager;
import org.ethereum.tserver.exceptions.ValidationException;

/**
 * @author Mikhail Kalinin
 * @since 05/14/2015
 */
@ChannelHandler.Sharable
public class ControlServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String request) {
        String response;
        try {
            Command cmd = CommandParser.parse(request);
            CommandManager.getInstance().submit(cmd);
            response = "OK";
        } catch (ValidationException e) {
            response = e.getMessage();
        }
        ctx.write(String.format("%s\n", response));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
