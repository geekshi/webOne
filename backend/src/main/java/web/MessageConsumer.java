package web;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Slf4j
@Component
public class MessageConsumer {

    @Autowired
    private WebSocketServer webSocketServer;

    @Autowired
    private StockService stockService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @RabbitListener(queues = {RabbitmqConfig.QUEUE_INFORM_STOCK_UPDATE})
    public void messageHandler(Object msg, Message message, Channel channel) throws UnsupportedEncodingException {
        String parsedMsg = new String(message.getBody(),"utf-8");
        log.info("receive message: {}", parsedMsg);
        webSocketServer.sendInfo(parsedMsg);
        Stock stock = new Stock("004", "HSBC", Double.valueOf(parsedMsg));
        int resultCount = stockService.updateStock(stock);
        if(resultCount != 1) {
            log.error("Update database failed, so won't update redis");
            return;
        }
        redisTemplate.opsForValue().set("004", stock);
        log.info("update redis done");
    }
}
