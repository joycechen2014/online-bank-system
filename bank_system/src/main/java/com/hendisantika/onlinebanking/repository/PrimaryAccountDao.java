package com.hendisantika.onlinebanking.repository;

import com.hendisantika.onlinebanking.entity.PrimaryAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by IntelliJ IDEA. Project : online-banking User: hendisantika Email:
 * hendisantika@gmail.com Telegram : @hendisantika34 Date: 08/08/18 Time: 06.04 To change this
 * template use File | Settings | File Templates.
 */
public interface PrimaryAccountDao extends CrudRepository<PrimaryAccount, Long> {

  PrimaryAccount findByAccountNumber(int accountNumber);

  PrimaryAccount findById(Long id);

  void deleteById(Long id);

  @Query(value = "SELECT max(account_number) FROM primary_account", nativeQuery = true)
  int getMaxAccountNumber();
}