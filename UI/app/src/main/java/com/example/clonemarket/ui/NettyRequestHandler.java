package com.example.clonemarket.ui;

import android.util.Log;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class NettyRequestHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        // 메시지 전송 전 로그
        Log.d("confirm","Message sent: " + msg.toString());
        super.write(ctx, msg, promise);
    }

    // 다른 메서드들...
}
