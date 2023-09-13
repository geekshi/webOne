package transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.DEFAULT, readOnly = false, timeout = -1)
    public void transferMoney(String from, String to, Double amount) throws Exception {
        if(!transactionValidate(from, to, amount))
            return;
        accountMapper.addMoney(to, amount);
        //somethingWrong();
        accountMapper.reduceMoney(from, amount);
    }

    private boolean transactionValidate(String from, String to, Double amount) {
        Account fromAccount = accountMapper.getAccountByNumber(from);
        if(fromAccount == null) {
            log.error("Invalid source account: {}", from);
            return false;
        }
        Account toAccount = accountMapper.getAccountByNumber(to);
        if(toAccount == null) {
            log.error("Invalid target account: {}", to);
            return false;
        }
        Double balance = fromAccount.getBalance();
        if(balance < amount) {
            log.error("Insufficient balance in the source account!");
            return false;
        }
        return true;
    }

    public void somethingWrong() throws Exception {
        throw new RuntimeException("Something wrong!");
    }
}
