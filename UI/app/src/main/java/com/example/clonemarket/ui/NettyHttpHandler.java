package com.example.clonemarket.ui;

import android.util.Log;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;

public class NettyHttpHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
            * 서버에 대한 연결이 만들어지면 호출
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
//        String sendMessage = "Hello,Netty!";

//        ByteBuf messageBuffer = Unpooled.buffer();
//        messageBuffer.writeBytes(sendMessage.getBytes());
//
//        StringBuilder builder = new StringBuilder();
//        builder.append("전송한 문자열 [");
//        builder.append(sendMessage);
//        builder.append("]");
//
//        System.out.println(builder.toString());
//        ctx.writeAndFlush(Unpooled.copiedBuffer(sendMessage.trim(), CharsetUtil.UTF_8));
        Log.d("confirm","connect");
    }


    /**
     * 데이터가 수신되었을 때
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if(msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;

            System.out.println("STATUS: " + response.status());
            System.out.println("VERSION: " + response.protocolVersion());

            if(!response.headers().isEmpty()) {
                for(CharSequence name : response.headers().names()) {
                    for(CharSequence value : response.headers().getAll(name)) {
                        System.out.println("HEADER: " + name + " = " + value);
                    }
                }
                System.out.println();
            }
            if (HttpUtil.isTransferEncodingChunked(response)) {
                System.out.println("CHUNKED CONTENT {");
            } else {
                System.out.println("CONTENT {");
            }
        }

        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;

            System.out.print(content.content().toString(CharsetUtil.UTF_8));
            System.out.flush();

            if (content instanceof LastHttpContent) {
                System.out.println();
                System.out.println("] END OF CONTENT");
                ctx.close();
            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        Log.d("confirm","종료");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
