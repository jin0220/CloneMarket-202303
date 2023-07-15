package com.example.toy.configuration;

import com.example.toy.socket.NettyServerSocket;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 네티 서버 소켓을 실행하여 incoming connection을 받을 줍비
 */
@Component
@RequiredArgsConstructor
public class ApplicationStartupTask implements ApplicationListener<ApplicationReadyEvent> {

    private final NettyServerSocket nettyServerSocket;

    @SneakyThrows
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        nettyServerSocket.start();
    }
}
