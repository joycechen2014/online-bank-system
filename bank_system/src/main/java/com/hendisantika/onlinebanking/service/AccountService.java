package com.hendisantika.onlinebanking.service;

import com.hendisantika.onlinebanking.entity.PrimaryAccount;
import com.hendisantika.onlinebanking.entity.SavingsAccount;
import com.hendisantika.onlinebanking.entity.User;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.security.Principal;

/**
 * Created by IntelliJ IDEA. Project : online-banking User: hendisantika Email:
 * hendisantika@gmail.com Telegram : @hendisantika34 Date: 09/08/18 Time: 04.32 To change this
 * template use File | Settings | File Templates.
 */
public interface AccountService {

  PrimaryAccount createPrimaryAccount();

  SavingsAccount createSavingsAccount();

  void deposit(String accountType, double amount, Principal principal);

  void deposit(String accountType, double amount, String targetUserName);

  void withdraw(String accountType, double amount, Principal principal);

  Boolean deletePrimaryAccount(Long accountId);

  Boolean deleteSavingsAccount(Long accountId);
}