package com.example.toy.socket;

import com.example.toy.handler.Handler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Component
@RequiredArgsConstructor
public class NettyServerSocket {
    private final int port = 9091;

    public void start(){
        EventLoopGroup group = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();

        final Handler serverHandler = new Handler();

        b.group(group)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port)) // 서버의 포트 주소 설정
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(serverHandler);
                    }
                });

        ChannelFuture f = b.bind(port);
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(future.isSuccess()){
                    System.out.println("Channel bound");
                }
                else {
                    System.out.println("Bind attempt failed");
                    future.cause().printStackTrace();
                }
            }
        });

        f.channel().closeFuture().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(future.isSuccess()){
                    System.out.println("Channel close");
                }
                else {
                    System.out.println("Failed");
                    future.cause().printStackTrace();
                }

                Future<?> future2 = group.shutdownGracefully();
                future2.addListener(new GenericFutureListener()  {
                    @Override
                    public void operationComplete(Future future) throws Exception {
                        System.out.println("EventGroup close");
                    }
                });
            }
        });
    }
}
