package com.hendisantika.onlinebanking.service.UserServiceImpl;

import com.hendisantika.onlinebanking.entity.*;
import com.hendisantika.onlinebanking.entity.CheckingAccount;
import com.hendisantika.onlinebanking.repository.CheckingAccountDao;
import com.hendisantika.onlinebanking.repository.SavingsAccountDao;
import com.hendisantika.onlinebanking.service.AccountService;
import com.hendisantika.onlinebanking.service.TransactionService;
import com.hendisantika.onlinebanking.service.UserService;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA. Project : online-banking User: hendisantika Email:
 * hendisantika@gmail.com Telegram : @hendisantika34 Date: 09/08/18 Time: 04.36 To change this
 * template use File | Settings | File Templates.
 */
@Service
public class AccountServiceImpl implements AccountService {

  //private static int nextAccountNumber = 11223101;

  @Autowired
  private CheckingAccountDao checkingAccountDao;

  @Autowired
  private SavingsAccountDao savingsAccountDao;

  @Autowired
  private UserService userService;

  @Autowired
  private TransactionService transactionService;

  public CheckingAccount createPrimaryAccount() {
    CheckingAccount checkingAccount = new CheckingAccount();
    checkingAccount.setAccountBalance(new BigDecimal(0.0));
    checkingAccount.setAccountNumber(accountGenChecking());
    checkingAccount.enable();

    checkingAccountDao.save(checkingAccount);

    return checkingAccountDao.findByAccountNumber(checkingAccount.getAccountNumber());
  }

  public Boolean deletePrimaryAccount(Long id) {
    CheckingAccount account = checkingAccountDao.findById(id);
    if (account != null) {
      account.disable();
      checkingAccountDao.save(account);
      return true;
    }
    return false;
  }

  public SavingsAccount createSavingsAccount() {
    SavingsAccount savingsAccount = new SavingsAccount();
    savingsAccount.setAccountBalance(new BigDecimal(0.0));
    savingsAccount.setAccountNumber(accountGenSavings());
    savingsAccount.enable();

    savingsAccountDao.save(savingsAccount);

    return savingsAccountDao.findByAccountNumber(savingsAccount.getAccountNumber());
  }

  public Boolean deleteSavingsAccount(Long id) {
    SavingsAccount account = savingsAccountDao.findById(id);
    if (account != null) {
      account.disable();
      savingsAccountDao.save(account);
      return true;
    }
    return false;
  }

  public void deposit(String accountType, double amount, Principal principal) {

    User user = userService.findByUsername(principal.getName());

    if (accountType.equalsIgnoreCase("Checking")) {
      CheckingAccount checkingAccount = user.getCheckingAccount();
      checkingAccount
          .setAccountBalance(checkingAccount.getAccountBalance().add(new BigDecimal(amount)));
      checkingAccountDao.save(checkingAccount);

      Date date = new Date();

      CheckingTransaction checkingTransaction = new CheckingTransaction(date,
          "Deposit to Primary Account", "Account", "Finished", amount,
          checkingAccount.getAccountBalance(), checkingAccount);
      transactionService.saveCheckingDepositTransaction(checkingTransaction);

    } else if (accountType.equalsIgnoreCase("Savings")) {
      SavingsAccount savingsAccount = user.getSavingsAccount();
      savingsAccount
          .setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
      savingsAccountDao.save(savingsAccount);

      Date date = new Date();
      SavingsTransaction savingsTransaction = new SavingsTransaction(date,
          "Deposit to savings Account", "Account", "Finished", amount,
          savingsAccount.getAccountBalance(), savingsAccount);
      transactionService.saveSavingsDepositTransaction(savingsTransaction);
    }
  }

  @Override
  public void deposit(String accountType, double amount, String targetUserName) {
    User targetUser = userService.findByUsername(targetUserName);
    if (accountType.equalsIgnoreCase("Checking")) {
      //System.out.println("------------------------primahry");
      CheckingAccount checkingAccount = targetUser.getCheckingAccount();
      checkingAccount
          .setAccountBalance(checkingAccount.getAccountBalance().add(new BigDecimal(amount)));
      checkingAccountDao.save(checkingAccount);

      Date date = new Date();

      CheckingTransaction checkingTransaction = new CheckingTransaction(date,
          "Deposit to Primary Account", "Account", "Finished", amount,
          checkingAccount.getAccountBalance(), checkingAccount);
      transactionService.saveCheckingDepositTransaction(checkingTransaction);

    } else if (accountType.equalsIgnoreCase("Savings")) {
      SavingsAccount savingsAccount = targetUser.getSavingsAccount();
      savingsAccount
          .setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
      savingsAccountDao.save(savingsAccount);

      Date date = new Date();
      SavingsTransaction savingsTransaction = new SavingsTransaction(date,
          "Deposit to savings Account", "Account", "Finished", amount,
          savingsAccount.getAccountBalance(), savingsAccount);
      transactionService.saveSavingsDepositTransaction(savingsTransaction);
    }
  }

  public void withdraw(String accountType, double amount, Principal principal) {
    User user = userService.findByUsername(principal.getName());

    if (accountType.equalsIgnoreCase("Primary")) {
      CheckingAccount checkingAccount = user.getCheckingAccount();
      checkingAccount
          .setAccountBalance(checkingAccount.getAccountBalance().subtract(new BigDecimal(amount)));
      checkingAccountDao.save(checkingAccount);

      Date date = new Date();

      CheckingTransaction checkingTransaction = new CheckingTransaction(date,
          "Withdraw from Primary Account", "Account", "Finished", amount,
          checkingAccount.getAccountBalance(), checkingAccount);
      transactionService.saveCheckingWithdrawTransaction(checkingTransaction);
    } else if (accountType.equalsIgnoreCase("Savings")) {
      SavingsAccount savingsAccount = user.getSavingsAccount();
      savingsAccount
          .setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
      savingsAccountDao.save(savingsAccount);

      Date date = new Date();
      SavingsTransaction savingsTransaction = new SavingsTransaction(date,
          "Withdraw from savings Account", "Account", "Finished", amount,
          savingsAccount.getAccountBalance(), savingsAccount);
      transactionService.saveSavingsWithdrawTransaction(savingsTransaction);
    }
  }


  private int accountGenSavings() {
    int currentAccountNumber  = savingsAccountDao.getMaxAccountNumber();
    return ++currentAccountNumber;
  }
  private int accountGenChecking() {
    int currentAccountNumber  = checkingAccountDao.getMaxAccountNumber();
    return ++currentAccountNumber;
  }

}