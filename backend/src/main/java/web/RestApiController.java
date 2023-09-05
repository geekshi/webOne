package web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api")
public class RestApiController {

    @Autowired
    StockService stockService;

    @GetMapping("/stocks")
    public ResponseEntity<?> getStocks() {
        log.info("Request received");
        List<Stock> stockList;
        try {
            stockList = stockService.getStocks();
        }catch (Exception e) {
            log.error("Call getStocks failed");
            return new ResponseEntity<>(new CustomErrorType("Call getStocks failed"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(stockList, HttpStatus.OK);
    }

    @GetMapping("/stocks/{code}")
    public ResponseEntity<?> getStock(@PathVariable("code") String code) {
        log.info("Request received with parameter code: {}", code);
        Stock stock;
        try {
            stock = stockService.getStockByCode(code);
        }catch (Exception e) {
            e.printStackTrace();
            log.error("Call getStock failed");
            return new ResponseEntity<>(new CustomErrorType("Call getStock failed"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }
}
