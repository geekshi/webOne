package web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.Random;

@Slf4j
@Component
public class MessageProducer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    private Random random = new Random();

    private DecimalFormat df = new DecimalFormat("#.00");

    @Scheduled(cron="0/5 * *  * * ? ")
    public void publish() {
        double price = random.nextDouble() * 100;
        String message = df.format(price);
        log.info("send message: {}", message);
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TOPICS_INFORM, "inform.stockupdate", message);
    }
}
