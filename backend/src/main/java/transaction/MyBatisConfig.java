package transaction;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("transaction")
public class MyBatisConfig {
}
