package web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class StockService {

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public List<Stock> getStocks() {
        return stockMapper.getAllStocks();
    }

    public int updateStock(Stock stock) {
        return stockMapper.updateStock(stock);
    }

    public Stock getStockByCode(String code) {
        Stock stock = (Stock) redisTemplate.opsForValue().get(code);
        if(stock != null) {
            log.info("Find the stock from the cache");
        }else {
            log.info("Didn't find the stock from the cache, fetch from the database and set to the cache");
            stock = stockMapper.getStockByCode(code);
            if(stock != null) {
                redisTemplate.opsForValue().set(stock.getCode(), stock);
                redisTemplate.expire(stock.getCode(), 1, TimeUnit.MINUTES);
            }
        }
        return stock;
    }
}
