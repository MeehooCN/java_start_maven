package com.meehoo.biz.common.http.socketIO;

//import io.socket.client.IO;
//import io.socket.client.Socket;

/**
 * Created by CZ on 2018/10/9.
 */
public abstract class SocketHolder {
//
//    private static final String url;
//    private static final int socketIdleTimeout;
//    private static final IO.Options options;
//
//    private static volatile Socket socket;
//    private static volatile long invokeTime;
//
//    static {
//        ResourceBundle props = ResourceBundle.getBundle("socketIO");
//        url = props.getString("server-url");
//        int _idleTimeout = Integer.parseInt(props.getString("connect-idle-timeout"));
//        int _reconnectionAttempts = Integer.parseInt(props.getString("reconnection-Attempts"));
//        socketIdleTimeout = _idleTimeout < 1 ? -1 : _idleTimeout < 60000 ? 60000 : _idleTimeout;
//        options = new IO.Options();
//        options.timeout = Integer.parseInt(props.getString("connect-timeout"));
//        options.reconnectionAttempts = _reconnectionAttempts <= 0 ? 1 : _reconnectionAttempts;
//        options.reconnectionDelay = Integer.parseInt(props.getString("reconnection-Delay"));
//        options.query = "clientId=" + props.getString("clientId");
//    }
//
//    protected static Socket getConnection() {
//        invokeTime = System.currentTimeMillis();
//        if (socket == null) {
//            synchronized (SocketHolder.class) {
//                if (socket == null) {
//                    try {
//                        socket = IO.socket(url, options);
//
//                        socket.on(Socket.EVENT_CONNECT, args -> {
//                            //连接成功唤醒调用等待socket返回的线程
//                            synchronized (SocketHolder.class) {
//                                SocketHolder.class.notifyAll();
//                            }
//                            //连接成功时开启socket闲置监控线程
//                            Thread socketMonitorThread = new Thread("SocketIdleMonitor") {
//                                long lastInvokeTime = 1L;
//                                @Override
//                                public void run() {
//                                    if (SocketHolder.socketIdleTimeout > 0) {
//                                        while (lastInvokeTime != SocketHolder.invokeTime) {
//                                            lastInvokeTime = SocketHolder.invokeTime;
//                                            try {
//                                                Thread.sleep(SocketHolder.socketIdleTimeout);
//                                            } catch (InterruptedException e) {
//                                                e.printStackTrace();
//                                            }
//                                        }
//                                        //如果getConnection方法在设定的闲置时间内没有被执行，则表示socket处于闲置状态
//                                        if (lastInvokeTime == SocketHolder.invokeTime) {
//                                            if (SocketHolder.socket.connected()) {
//                                                synchronized (SocketHolder.socket) {
//                                                    if (SocketHolder.socket.connected()) {
//                                                        SocketHolder.socket.disconnect();
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            };
//                            socketMonitorThread.setDaemon(true);
//                            socketMonitorThread.start();
//                        }).on(Socket.EVENT_RECONNECT_FAILED, args -> {
//                            //连接失败唤醒调用等待socket返回的线程
//                            synchronized (SocketHolder.class) {
//                                SocketHolder.class.notifyAll();
//                            }
//                        });
//                        socket.connect();
//                        //将线程挂起，等成功连接或者连接失败时唤醒线程以返回socket实例
//                        SocketHolder.class.wait();
//                    } catch (URISyntaxException e) {
//                        e.printStackTrace();
//                    } catch (InterruptedException ignored) {
//                    }
//                }
//            }
//        }
//        if (!socket.connected()) {
//            synchronized (SocketHolder.class) {
//                if (!socket.connected()) {
//                    socket.connect();
//                }
//            }
//        }
//        return socket;
//    }
//
//    protected static String getUrl(){
//        return SocketHolder.url;
//    }

}
