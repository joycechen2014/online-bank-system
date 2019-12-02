package com.hendisantika.onlinebanking.resource;

import com.hendisantika.onlinebanking.entity.PrimaryTransaction;
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
  @RequestMapping(value = "/user/primary/transaction", method = RequestMethod.GET)
  public List<PrimaryTransaction> getPrimaryTransactionList(
      @RequestParam("username") String username) {
    return transactionService.findPrimaryTransactionList(username);
  }

  @RequestMapping(value = "/user/savings/transaction", method = RequestMethod.GET)
  public List<SavingsTransaction> getSavingsTransactionList(
      @RequestParam("username") String username) {
    return transactionService.findSavingsTransactionList(username);
  }

  @RequestMapping(value= "/transaction/searchResult", method = RequestMethod.GET)
  public List<PrimaryTransaction> searchPrimaryTransactionList(
          @RequestParam(value="username") String username,
          @RequestParam(value="date", required=false) Date date,
          @RequestParam(value="type", required=false) String type,
          @RequestParam(value="status", required=false) String status,
          @RequestParam(value="amount", required=false) Double amount) {
    List<PrimaryTransaction> allPrimaryTransaction = transactionService.findPrimaryTransactionList(username);
    if (date != null) {
      List<PrimaryTransaction> primaryTransactions1 = new ArrayList<>();
      for (PrimaryTransaction transaction : allPrimaryTransaction) {
        if (transaction.getDate() == date) {
          primaryTransactions1.add(transaction);
        }
      }
      allPrimaryTransaction = primaryTransactions1;
    }
    if (type != null) {
      List<PrimaryTransaction> primaryTransactions2 = new ArrayList<>();
      for (PrimaryTransaction transaction : allPrimaryTransaction) {
        if (transaction.getType().equals(type)) {
          primaryTransactions2.add(transaction);
        }
      }
      allPrimaryTransaction = primaryTransactions2;
    }
    if (status != null) {
      List<PrimaryTransaction> primaryTransactions3 = new ArrayList<>();
      for (PrimaryTransaction transaction : allPrimaryTransaction) {
        if (transaction.getStatus().equals(status)) {
          primaryTransactions3.add(transaction);
        }
      }
      allPrimaryTransaction = primaryTransactions3;
    }
    if (amount != null) {
      List<PrimaryTransaction> primaryTransactions4 = new ArrayList<>();
      for (PrimaryTransaction transaction : allPrimaryTransaction) {
        if (transaction.getAmount() == amount) {
          primaryTransactions4.add(transaction);
        }
      }
      allPrimaryTransaction = primaryTransactions4;
    }

            return allPrimaryTransaction;
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
