package com.hendisantika.onlinebanking.repository;

import com.hendisantika.onlinebanking.entity.CheckingAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by IntelliJ IDEA. Project : online-banking User: hendisantika Email:
 * hendisantika@gmail.com Telegram : @hendisantika34 Date: 08/08/18 Time: 06.04 To change this
 * template use File | Settings | File Templates.
 */
public interface CheckingAccountDao extends CrudRepository<CheckingAccount, Long> {

  CheckingAccount findByAccountNumber(int accountNumber);

  CheckingAccount findById(Long id);

  void deleteById(Long id);

  @Query(value = "SELECT max(account_number) FROM checking_account", nativeQuery = true)
  int getMaxAccountNumber();
}
