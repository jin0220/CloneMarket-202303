package com.example.toy.handler;


import com.example.toy.entity.ChattingContent;
import com.example.toy.entity.ChattingRoom;
import com.example.toy.service.ChatService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;
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
        String buyerUser = jsonObj.get("buyerUser").toString();
        String message = jsonObj.get("message").toString();
        String postNum = null;
        String time = jsonObj.get("time").toString();
        String roomId = null;
        String sellerUser = null;

        ChattingRoom chattingRoom = new ChattingRoom();

        ChattingContent chattingContent = new ChattingContent();

        if(jsonObj.containsKey("roomId")){
            System.out.println("contain");
            roomId = jsonObj.get("roomId").toString();
            chattingContent.setRoomId(Long.parseLong(roomId));
//            chattingContent.setChattingRoom(chattingRoom);
        }
        else { // 채팅방 개설
            postNum = jsonObj.get("postNum").toString();
            sellerUser = jsonObj.get("sellerUser").toString();

            ChattingRoom cr = new ChattingRoom();
            cr.setPostNum(Long.parseLong(postNum));
            cr.setSellerUser(sellerUser);
            cr.setBuyerUser(buyerUser);
//            cr.setCreateTime(time.split(" ")[1]);
            cr.setCreateTime(time);

            chattingRoom = chatService.setChattingRoom(cr);
            chattingContent.setRoomId(chattingRoom.getRoomId());
//            chattingContent.setChattingRoom(chattingRoom);
        }

        chattingContent.setUserPhone(buyerUser);
        chattingContent.setContents(message);
//        chattingContent.setSendTime(time.split(" ")[1]);
        chattingContent.setSendTime(time);

        chatService.setChattingContent(chattingContent);

        System.out.println("roomId = " + roomId);
        System.out.println("postNum = " + postNum);
        System.out.println("buyerUser = " + buyerUser);
        System.out.println("sellerUser = " + sellerUser);
        System.out.println("message = " + message);
        System.out.println("time = " + time);


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

