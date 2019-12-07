package com.hendisantika.onlinebanking.service;

import com.hendisantika.onlinebanking.entity.*;
import com.hendisantika.onlinebanking.entity.CheckingAccount;
import com.hendisantika.onlinebanking.exception.InsufficientBalanceException;
import java.security.Principal;
import java.util.List;

/**
 * Created by IntelliJ IDEA. Project : online-banking User: hendisantika Email:
 * hendisantika@gmail.com Telegram : @hendisantika34 Date: 09/08/18 Time: 04.34 To change this
 * template use File | Settings | File Templates.
 */
public interface TransactionService {

  List<CheckingTransaction> findCheckingTransactionList(String username);

  List<SavingsTransaction> findSavingsTransactionList(String username);

  void saveCheckingDepositTransaction(CheckingTransaction checkingTransaction);

  void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction);

  void saveCheckingWithdrawTransaction(CheckingTransaction checkingTransaction);

  void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction);

  void betweenAccountsTransfer(String transferFrom, String transferTo, String amount,
                               CheckingAccount checkingAccount, SavingsAccount savingsAccount) throws Exception;

  List<Recipient> findRecipientList(Principal principal);

  Recipient saveRecipient(Recipient recipient);

  Recipient findRecipientByName(String recipientName);
  Recipient findRecipientByAccountNumber(String accountNumber);

  void deleteRecipientByName(String recipientName);

  void toSomeoneElseTransfer(Recipient recipient, String accountType, String amount,
                             CheckingAccount checkingAccount, SavingsAccount savingsAccount) throws InsufficientBalanceException;
}