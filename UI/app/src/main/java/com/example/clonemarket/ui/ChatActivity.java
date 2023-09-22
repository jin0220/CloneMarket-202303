package com.example.clonemarket.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.clonemarket.R;
import com.example.clonemarket.data.PreferenceManager;
import com.example.clonemarket.data.model.ChatDto;
import com.example.clonemarket.data.model.PostDto;
import com.example.clonemarket.databinding.ActivityChatBinding;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder;
import io.netty.util.CharsetUtil;

public class ChatActivity extends AppCompatActivity {
    private ActivityChatBinding binding;

    ChatAdapter adapter;

    private static final String HOST = "192.168.35.65"; // 노트북의 로컬 주소
    private static final int PORT = 9091;

    private EventLoopGroup group;
    private Channel channel;

    ChannelFuture future;

    ChatViewModel viewModel;

    String postNum;
    String sellerUser;
    long roomId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new ChatAdapter(getApplicationContext());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ChatViewModel();

        if(!getIntent().getStringExtra("postNum").equals("null")) {
            postNum = getIntent().getStringExtra("postNum");
            sellerUser = getIntent().getStringExtra("sellerUser");
        }
        else {
            roomId = getIntent().getLongExtra("roomId",0);
        }

        group = new NioEventLoopGroup();

        Log.d("confirm", "Chat");

        try {
            Log.d("confirm", "Chat1");
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) {
                            // 채널 파이프라인에 이벤트 핸들러 추가
                            ch.pipeline().addLast(new NettyHttpHandler());
                            ch.pipeline().addLast(new NettyRequestHandler());
                        }
                    });

            // 서버에 연결
            future = bootstrap.connect(HOST, PORT);
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if(future.isSuccess()){
                        Log.d("confirm","Channel bound");
                        channel = future.channel();
                    }
                    else {
                        Log.d("confirm","Bind attempt failed ");
                        future.cause().printStackTrace();
                    }
                }
            });


        } catch (Exception e) {
            Log.d("confirm","Chat fail");
            e.printStackTrace();
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("postNum", postNum);
        jsonObject.addProperty("roomId", roomId);
        jsonObject.addProperty("phoneNum", PreferenceManager.getString(getApplicationContext(),"phoneNum"));
        viewModel.getChattingRoom(jsonObject);

        viewModel.response.observe(ChatActivity.this, new Observer<JsonArray>() {
            @Override
            public void onChanged(JsonArray jsonElements) {
                List<ChatDto> list = new ArrayList<>();
                if(!jsonElements.isJsonNull()){
                    for(int i = 0; i < jsonElements.size(); i++) {
                        JsonObject jsonObject1 = (JsonObject) jsonElements.get(i);

                        roomId = jsonObject1.get("roomId").getAsLong();

                        ChatDto data = new ChatDto();
                        data.setRoomId(roomId);
                        data.setPhone(jsonObject1.get("userPhone").getAsString());
                        data.setNickName(jsonObject1.get("userPhone").getAsString());
                        data.setContent(jsonObject1.get("contents").getAsString());
                        data.setTime(jsonObject1.get("sendTime").getAsString());
                        Log.d("confirm", "roomId recv :" + roomId);
                        Log.d("confirm","Chat " + data);

                        list.add(data);
                    }
                }
                adapter.dataList = list;
                adapter.notifyDataSetChanged();
                Log.d("confirm", "1");
            }
        });

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.content.getText().length() > 0) {
                    Log.d("confirm", "message send");
                    sendMessageToServer(roomId);
                    if (adapter.dataList.isEmpty()) {

                    } else {

                    }

                    binding.content.setText("");
                }
            }
        });
    }

    private void sendMessageToServer(Long roomId) {
        if (channel != null && channel.isActive()) {
            String message = binding.content.getText().toString();

            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat sdfTime = new SimpleDateFormat("HHmmss");
            String date = sdfDate.format(System.currentTimeMillis());
            String time = sdfTime.format(System.currentTimeMillis());

            // JSON 형식으로 사용자 정보와 메시지를 함께 전송
            JSONObject jsonObject = new JSONObject();
            Log.d("confirm", "roomId send :" + roomId);
            try {
                if (roomId != 0) { // 기존에 존재하는 채팅방인 경우
                    jsonObject.put("roomId", roomId);
                }
                else {
                    jsonObject.put("sellerUser", sellerUser);
                }
                jsonObject.put("postNum", postNum);
                jsonObject.put("buyerUser", PreferenceManager.getString(this, "phoneNum"));
                jsonObject.put("message", message);
//                jsonObject.put("time", date + " " + time);
                jsonObject.put("time", System.currentTimeMillis());

//                ChatDto data = new ChatDto();
//                data.setRoomId(roomId);
//                data.setPhone(postNum);
//                data.setNickName(postNum);
//                data.setContent(message);
////                data.setTime();
//
//                adapter.dataList.add(data);
//                adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }

            ByteBuf data = Unpooled.copiedBuffer(jsonObject.toString(), CharsetUtil.UTF_8);
            channel.writeAndFlush(data);
        }
    }

//    public void createRequest(String host, int port, String url) throws Exception {
////        String content = "{\"host\":" + objectToJsonString + "}";	// body 데이터
//
//        URI uri = new URI("https://b023-218-236-76-214.ngrok-free.app" + "/api/v1/chat");
//        HttpRequest request = null;
//        HttpPostRequestEncoder postRequestEncoder = null;
//
//        request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, uri.getRawPath()); // http의 header와 body를 직접 정의
//
//        request.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON);
//        request.headers().set(HttpHeaderNames.HOST, host + ":" + port);
//        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
//
//        postRequestEncoder = new HttpPostRequestEncoder(request, false); // post방식의 경우 요청 파라미터가 url에 명시되지 않기 때문에 postRequestEncoder를 통해 지정가능
//        postRequestEncoder.addBodyAttribute("url", url);
//        request=postRequestEncoder.finalizeRequest();
//        postRequestEncoder.close();
//
//        future.channel().writeAndFlush(request);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 액티비티 종료 시 네티 관련 리소스 해제
        if (channel != null) {
            channel.close();
        }
        if (group != null) {
            group.shutdownGracefully();
        }
    }

}

