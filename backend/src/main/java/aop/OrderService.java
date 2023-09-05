package aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderService {

    @RecordOperate(desc = "SaveOrder", convert = SaveOrderConvert.class)
    public Boolean saveOrder(SaveOrder saveOrder) {
        log.info("save order, order id: {}", saveOrder.getId());
        return true;
    }

    @RecordOperate(desc = "UpdateOrder", convert = UpdateOrderConvert.class)
    public Boolean updateOrder(UpdateOrder updateOrder) {
        log.info("update order, order id: {}", updateOrder.getOrderId());
        return true;
    }
}
