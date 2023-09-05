package web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@ServerEndpoint("/push/websocket/{sid}")
public class WebSocketServer {

    private static AtomicInteger onlineCount = new AtomicInteger();

    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    private Session session;

    private String sid = "";

    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        webSocketSet.add(this);
        onlineCount.incrementAndGet();
        log.info("New session listens on: {}, onlineCount is: {}", sid, onlineCount.get());
        this.sid = sid;
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        onlineCount.decrementAndGet();
        log.info("Session closed, onlineCount is: {}", onlineCount.get());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        if(message.equalsIgnoreCase("heart")) {
            try {
                sendMessage("heartOK");
            }catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error(error.getMessage());
    }

    public void sendInfo(String message) {

        for (WebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
                log.info("push to the session: {}, with content: {}", item.sid, message);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
}
