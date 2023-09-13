package transaction;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AccountMapper {

    @Select("select * from test.account where acct_no = #{accountNumber}")
    Account getAccountByNumber(String accountNumber);

    @Update("update test.account set balance = balance + #{amount} where acct_no = #{accountNumber}")
    void addMoney(String accountNumber, Double amount);

    @Update("update test.account set balance = balance - #{amount} where acct_no = #{accountNumber}")
    void reduceMoney(String accountNumber, Double amount);
}
