package com.example.toy.handler;


import com.example.toy.entity.Chat;
import com.example.toy.service.ChatService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.CharsetUtil;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable // 여러 채널에서 핸들러를 공유할 수 있음
//@RequiredArgsConstructor
public class Handler extends ChannelInboundHandlerAdapter {
    private int DATA_LENGTH = 2048;
    private ByteBuf buff;

    private final ChatService chatService;

    @Autowired
    public Handler(ChatService chatService) {
        this.chatService = chatService;
    }

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /**
     * 핸들러가 생성될 때 호출
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        buff = ctx.alloc().buffer(DATA_LENGTH);
    }

    /**
     * 핸들러가 제거될 때 호출
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        buff = null;
    }

    /**
     * 클라이언트와 연결되어 트래픽을 생성할 준비가 되었을 때 호출
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        String remoteAddress = ctx.channel().remoteAddress().toString();
        log.info("Remote Address: " + remoteAddress);
    }

    /**
     * inbound buffer에서 읽을 값이 있을 경우 호출. 즉, 메시지가 들어올 때마다 호출
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws ParseException {
        ByteBuf mBuf = (ByteBuf) msg;
        String jsonData = mBuf.toString(CharsetUtil.UTF_8);
        System.out.println("Server received [" + jsonData + "]");
        //ctx.writeAndFlush(mBuf);

        // JSON 데이터 파싱하여 객체 생성
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(jsonData);
        JSONObject jsonObj = (JSONObject)obj;
        String phoneNum = jsonObj.get("phoneNum").toString();
        String message = jsonObj.get("message").toString();

        System.out.println("phoneNum = " + phoneNum);
        System.out.println("message = " + message);

        Chat chat = new Chat(phoneNum, "test", message);

        chatService.setChat(chat);

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush(); // 응답을 클라이언트로 전송한 후에 버퍼를 비워
    }


    /**
     * 읽기 작업 중 오류가 발생했을 때 호출
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

