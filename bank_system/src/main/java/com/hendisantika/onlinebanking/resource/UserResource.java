package com.hendisantika.onlinebanking.resource;

import com.hendisantika.onlinebanking.entity.CheckingTransaction;
import com.hendisantika.onlinebanking.entity.SavingsTransaction;
import com.hendisantika.onlinebanking.entity.User;
import com.hendisantika.onlinebanking.service.TransactionService;
import com.hendisantika.onlinebanking.service.UserService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA. Project : online-banking User: hendisantika Email:
 * hendisantika@gmail.com Telegram : @hendisantika34 Date: 04/09/18 Time: 06.39 To change this
 * template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/api")
//@PreAuthorize("hasRole('ADMIN')")
public class UserResource {

  @Autowired
  private UserService userService;

  @Autowired
  private TransactionService transactionService;

  @RequestMapping(value = "/user/all", method = RequestMethod.GET)
  public List<User> userList() {
    return userService.findUserList();
  }
  @RequestMapping(value = "/user/checking/transaction", method = RequestMethod.GET)
  public List<CheckingTransaction> getcheckingTransactionList(
      @RequestParam("username") String username) {
    return transactionService.findCheckingTransactionList(username);
  }

  @RequestMapping(value = "/user/savings/transaction", method = RequestMethod.GET)
  public List<SavingsTransaction> getSavingsTransactionList(
      @RequestParam("username") String username) {
    return transactionService.findSavingsTransactionList(username);
  }

  @RequestMapping(value= "/checkingtransaction/searchresult", method = RequestMethod.GET)
  public List<CheckingTransaction> searchcheckingTransactionList(
          @RequestParam(value="username") String username,
          @RequestParam(value="date", required=false) Date date,
          @RequestParam(value="type", required=false) String type,
          @RequestParam(value="status", required=false) String status,
          @RequestParam(value="amount", required=false) Double amount) {
    List<CheckingTransaction> allCheckingTransaction = transactionService.findCheckingTransactionList(username);
    if (date != null) {
      List<CheckingTransaction> checkingTransactions1 = new ArrayList<>();
      for (CheckingTransaction transaction : allCheckingTransaction) {
        if (transaction.getDate() == date) {
          checkingTransactions1.add(transaction);
        }
      }
      allCheckingTransaction = checkingTransactions1;
    }
    if (type != null) {
      List<CheckingTransaction> checkingTransactions2 = new ArrayList<>();
      for (CheckingTransaction transaction : allCheckingTransaction) {
        if (transaction.getType().equals(type)) {
          checkingTransactions2.add(transaction);
        }
      }
      allCheckingTransaction = checkingTransactions2;
    }
    if (status != null) {
      List<CheckingTransaction> checkingTransactions3 = new ArrayList<>();
      for (CheckingTransaction transaction : allCheckingTransaction) {
        if (transaction.getStatus().equals(status)) {
          checkingTransactions3.add(transaction);
        }
      }
      allCheckingTransaction = checkingTransactions3;
    }
    if (amount != null) {
      List<CheckingTransaction> checkingTransactions4 = new ArrayList<>();
      for (CheckingTransaction transaction : allCheckingTransaction) {
        if (transaction.getAmount() == amount) {
          checkingTransactions4.add(transaction);
        }
      }
      allCheckingTransaction = checkingTransactions4;
    }

            return allCheckingTransaction;
  }
  
  @RequestMapping(value= "/savingstransaction/searchresult", method = RequestMethod.GET)
  public List<SavingsTransaction> searchSavingsTransactionList(
          @RequestParam(value="username") String username,
          @RequestParam(value="date", required=false) Date date,
          @RequestParam(value="type", required=false) String type,
          @RequestParam(value="status", required=false) String status,
          @RequestParam(value="amount", required=false) Double amount) {
    List<SavingsTransaction> allSavingsTransaction = transactionService.findSavingsTransactionList(username);
    if (date != null) {
      List<SavingsTransaction> savingsTransactions1 = new ArrayList<>();
      for (SavingsTransaction transaction : allSavingsTransaction) {
        if (transaction.getDate() == date) {
          savingsTransactions1.add(transaction);
        }
      }
      allSavingsTransaction = savingsTransactions1;
    }
    if (type != null) {
      List<SavingsTransaction> savingsTransactions2 = new ArrayList<>();
      for (SavingsTransaction transaction : allSavingsTransaction) {
        if (transaction.getType().equals(type)) {
          savingsTransactions2.add(transaction);
        }
      }
      allSavingsTransaction = savingsTransactions2;
    }
    if (status != null) {
      List<SavingsTransaction> savingsTransactions3 = new ArrayList<>();
      for (SavingsTransaction transaction : allSavingsTransaction) {
        if (transaction.getStatus().equals(status)) {
          savingsTransactions3.add(transaction);
        }
      }
      allSavingsTransaction = savingsTransactions3;
    }
    if (amount != null) {
      List<SavingsTransaction> savingsTransactions4 = new ArrayList<>();
      for (SavingsTransaction transaction : allSavingsTransaction) {
        if (transaction.getAmount() == amount) {
          savingsTransactions4.add(transaction);
        }
      }
      allSavingsTransaction = savingsTransactions4;
    }

    return allSavingsTransaction;
  }

  @RequestMapping("/user/{username}/enable")
  public void enableUser(@PathVariable("username") String username) {
    userService.enableUser(username);
  }

  @RequestMapping("/user/{username}/disable")
  public void diableUser(@PathVariable("username") String username) {
    userService.disableUser(username);
  }
}
