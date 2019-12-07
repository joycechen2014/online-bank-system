package com.hendisantika.onlinebanking.service.UserServiceImpl;

import com.hendisantika.onlinebanking.entity.*;
import com.hendisantika.onlinebanking.entity.CheckingAccount;
import com.hendisantika.onlinebanking.exception.InsufficientBalanceException;
import com.hendisantika.onlinebanking.repository.CheckingAccountDao;
import com.hendisantika.onlinebanking.repository.CheckingTransactionDao;
import com.hendisantika.onlinebanking.repository.RecipientDao;
import com.hendisantika.onlinebanking.repository.SavingsAccountDao;
import com.hendisantika.onlinebanking.repository.SavingsTransactionDao;
import com.hendisantika.onlinebanking.service.TransactionService;
import com.hendisantika.onlinebanking.service.UserService;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA. Project : online-banking User: hendisantika Email:
 * hendisantika@gmail.com Telegram : @hendisantika34 Date: 10/08/18 Time: 06.21 To change this
 * template use File | Settings | File Templates.
 */
@Service
public class TransactionServiceImpl implements TransactionService {

  @Autowired
  private UserService userService;

  @Autowired
  private CheckingTransactionDao checkingTransactionDao;

  @Autowired
  private SavingsTransactionDao savingsTransactionDao;

  @Autowired
  private CheckingAccountDao primaryAccountDao;

  @Autowired
  private SavingsAccountDao savingsAccountDao;

  @Autowired
  private RecipientDao recipientDao;


  public List<CheckingTransaction> findCheckingTransactionList(String username) {
    User user = userService.findByUsername(username);
    List<CheckingTransaction> checkingTransactionList = user.getCheckingAccount()
        .getCheckingTransactionList();

    return checkingTransactionList;
  }

  public List<SavingsTransaction> findSavingsTransactionList(String username) {
    User user = userService.findByUsername(username);
    List<SavingsTransaction> savingsTransactionList = user.getSavingsAccount()
        .getSavingsTransactionList();

    return savingsTransactionList;
  }

  public void saveCheckingDepositTransaction(CheckingTransaction checkingTransaction) {
    checkingTransactionDao.save(checkingTransaction);
  }

  public void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction) {
    savingsTransactionDao.save(savingsTransaction);
  }

  public void saveCheckingWithdrawTransaction(CheckingTransaction checkingTransaction) {
    checkingTransactionDao.save(checkingTransaction);
  }

  public void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction) {
    savingsTransactionDao.save(savingsTransaction);
  }

  public void betweenAccountsTransfer(String transferFrom, String transferTo, String amount,
                                      CheckingAccount checkingAccount, SavingsAccount savingsAccount) throws Exception {
    if (transferFrom.equalsIgnoreCase("Checking") && transferTo.equalsIgnoreCase("Savings")) {
      checkingAccount
          .setAccountBalance(checkingAccount.getAccountBalance().subtract(new BigDecimal(amount)));
      savingsAccount
          .setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
      primaryAccountDao.save(checkingAccount);
      savingsAccountDao.save(savingsAccount);

      Date date = new Date();

      CheckingTransaction checkingTransaction = new CheckingTransaction(date,
          "Between account transfer from " + transferFrom + " to " + transferTo, "Account",
          "Finished", Double.parseDouble(amount), checkingAccount.getAccountBalance(),
              checkingAccount);
      checkingTransactionDao.save(checkingTransaction);
    } else if (transferFrom.equalsIgnoreCase("Savings") && transferTo.equalsIgnoreCase("Checking")) {
      checkingAccount
          .setAccountBalance(checkingAccount.getAccountBalance().add(new BigDecimal(amount)));
      savingsAccount
          .setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
      primaryAccountDao.save(checkingAccount);
      savingsAccountDao.save(savingsAccount);

      Date date = new Date();

      SavingsTransaction savingsTransaction = new SavingsTransaction(date,
          "Between account transfer from " + transferFrom + " to " + transferTo, "Transfer",
          "Finished", Double.parseDouble(amount), savingsAccount.getAccountBalance(),
          savingsAccount);
      savingsTransactionDao.save(savingsTransaction);
    } else {
      throw new Exception("Invalid Transfer");
    }
  }

  public List<Recipient> findRecipientList(Principal principal) {
    String username = principal.getName();
    List<Recipient> recipientList = recipientDao.findAll().stream()
        .filter(recipient -> username.equals(recipient.getUser().getUsername()))
        .collect(Collectors.toList());

    return recipientList;
  }

  public Recipient saveRecipient(Recipient recipient) {
    return recipientDao.save(recipient);
  }

  public Recipient findRecipientByName(String recipientName) {
    return recipientDao.findByName(recipientName);
  }

  @Override
  public Recipient findRecipientByAccountNumber(String accountNumber) {
    return recipientDao.findByAccountNumber(accountNumber);
  }

  public void deleteRecipientByName(String recipientName) {
    recipientDao.deleteByName(recipientName);
  }

  public void toSomeoneElseTransfer(Recipient recipient, String accountType, String amount,
                                    CheckingAccount checkingAccount, SavingsAccount savingsAccount)
      throws InsufficientBalanceException {

      if (accountType.equalsIgnoreCase("Checking")) {

        BigDecimal subtractResult = checkingAccount.getAccountBalance()
            .subtract(new BigDecimal(amount));

        if (subtractResult.compareTo(BigDecimal.ZERO) != -1) {
          checkingAccount
              .setAccountBalance(subtractResult);
          primaryAccountDao.save(checkingAccount);
          Date date = new Date();

          CheckingTransaction checkingTransaction = new CheckingTransaction(date,
              "Transfer to recipient " + recipient.getName(), "Transfer", "Finished",
              Double.parseDouble(amount), checkingAccount.getAccountBalance(), checkingAccount);
          checkingTransactionDao.save(checkingTransaction);
        } else {
          throw new InsufficientBalanceException("Insufficient Balance!");
        }


      } else if (accountType.equalsIgnoreCase("Savings")) {
        BigDecimal subtractResult = checkingAccount.getAccountBalance()
            .subtract(new BigDecimal(amount));

        if (subtractResult.compareTo(BigDecimal.ZERO) != -1) {
          savingsAccount
              .setAccountBalance(
                  savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
          savingsAccountDao.save(savingsAccount);

          Date date = new Date();

          SavingsTransaction savingsTransaction = new SavingsTransaction(date,
              "Transfer to recipient " + recipient.getName(), "Transfer", "Finished",
              Double.parseDouble(amount), savingsAccount.getAccountBalance(), savingsAccount);
          savingsTransactionDao.save(savingsTransaction);
        } else {
          throw new InsufficientBalanceException("Insufficient Balance!");
        }
      }
  }
}