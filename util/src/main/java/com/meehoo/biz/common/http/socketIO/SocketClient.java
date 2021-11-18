package com.meehoo.biz.common.http.socketIO;

//import com.google.gson.GsonBuilder;
//import io.socket.client.Ack;
//import io.socket.client.Socket;

/**
 * Created by CZ on 2018/10/8.
 */
public abstract class SocketClient {
//
//    private static final GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss");
//    private static final Logger log = LoggerFactory.getLogger(SocketClient.class);
//
//    /**
//     * 发送消息
//     *
//     * @param socketMessage
//     *
//     */
//    public static void sendMsg(SocketMessage socketMessage) {
//        final String json = gsonBuilder.create().toJson(socketMessage);
//        Socket socket = SocketHolder.getConnection();
//        if (socket.connected()) {
//            socket.emit(Socket.EVENT_MESSAGE, json, (Ack) args -> {
//            });
//        } else {
//            log.error("与"+SocketHolder.getUrl()+"建立连接失败,未能发送消息:\n%s",json);
//        }
//    }
}
