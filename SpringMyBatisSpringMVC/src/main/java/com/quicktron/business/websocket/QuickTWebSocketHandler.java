package com.quicktron.business.websocket;

import com.quicktron.business.service.impl.QueryBkSlotSerivceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class QuickTWebSocketHandler implements WebSocketHandler {

    private static final Logger LOGGER = Logger.getLogger(QueryBkSlotSerivceImpl.class);

    //Map来存储WebSocketSession，key用USER_ID 即在线用户列表
    private static final Map<String, WebSocketSession> users;
    static {
        users =  new HashMap<String, WebSocketSession>();
    }
    private static final String USER_ID = "WEBSOCKET_USERID";   //对应监听器从的key

    public QuickTWebSocketHandler() {};

    // 关闭 连接时
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        String wsCode = this.getWsCode(webSocketSession);
        users.put(wsCode, webSocketSession);
//        users.put(webSocketSession.getId(), webSocketSession);
//        session.sendMessage(new TextMessage("建立WebSocket连接成功！"));
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        TextMessage message = new TextMessage(webSocketMessage.getPayload()+ " received at server");
        System.out.println("服务器收到消息："+ message);
//        if(message.getPayload().startsWith("#anyone#")){ //单发某人
//            sendMessageToUser((String) webSocketSession.getAttributes().get(USER_ID),message.toString()) ;
//        }
////        Message msg=new Gson().fromJson(message.getPayload().toString(),Message.class);
////        msg.setDate(new Date());
////        sendMessageToUser(msg.getTo(), new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));

    }

//    /*js调用websocket.send时候，会调用该方法*/
////    @Override
//    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
////        String msg = webSocketMessage.toString();
////        String userId = this.getUserId(session);
////        System.err.println("该"+userId+"用户发送的消息是："+msg);
////        message = new TextMessage("服务端已经接收到消息，msg="+msg);
////        session.sendMessage(message);
////
////        super.handleTextMessage(session, message);
//
//        /**
//         * 收到消息，自定义处理机制，实现业务
//         */
//        System.out.println("服务器收到消息："+ message);
//        if(message.getPayload().startsWith("#anyone#")){ //单发某人
//            sendMessageToUser((String) session.getAttributes().get(USER_ID), new TextMessage("服务器单发：" +message.getPayload())) ;
//        }
////        Message msg=new Gson().fromJson(message.getPayload().toString(),Message.class);
////        msg.setDate(new Date());
////        sendMessageToUser(msg.getTo(), new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));
//
//    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
//        String userId= (String) webSocketSession.getAttributes().get(USER_ID);
//        System.out.println("用户"+userId+"已退出！");
//        users.remove(userId);

        String wsCode = this.getWsCode(webSocketSession);
        if(StringUtils.isNoneBlank(wsCode)){
            users.remove(wsCode);
        }else{
            LOGGER.info("无法获取会话中的工作站编码");
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private String getWsCode(WebSocketSession session){
        try {
            String wsCode = (String)session.getAttributes().get("WEBSOCKET_USERID");
            return wsCode;
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
        return null;
    }

    public void sendMessageToUser(String wsCode,String contents) {
        WebSocketSession session = users.get(wsCode);
        String newStr =contents.replaceAll("\\\\","");
        if(session !=null && session.isOpen()) {
            try {
                TextMessage message = new TextMessage(newStr);
                session.sendMessage(message);
            } catch (IOException e) {
                LOGGER.info(e.getMessage());
            }
        }
    }
}
