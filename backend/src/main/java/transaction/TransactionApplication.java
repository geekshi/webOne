package transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
//Reference: https://javaguide.cn/system-design/framework/spring/spring-transaction.html#transactional-%E6%B3%A8%E8%A7%A3%E4%BD%BF%E7%94%A8%E8%AF%A6%E8%A7%A3
public class TransactionApplication implements CommandLineRunner {

    @Autowired
    private AccountService accountService;

    public static void main(String[] args) {
        new SpringApplication(TransactionApplication.class).run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        accountService.transferMoney("1000", "1001", 1000.0);
    }
}
