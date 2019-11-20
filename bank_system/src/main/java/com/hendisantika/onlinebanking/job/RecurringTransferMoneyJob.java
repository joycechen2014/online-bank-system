package com.hendisantika.onlinebanking.job;

import com.hendisantika.onlinebanking.entity.Recipient;
import com.hendisantika.onlinebanking.entity.TransferMoneyDTO;
import com.hendisantika.onlinebanking.entity.User;
import com.hendisantika.onlinebanking.exception.InsufficientBalanceException;
import com.hendisantika.onlinebanking.service.TransactionService;
import com.hendisantika.onlinebanking.service.UserService;
import java.security.Principal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecurringTransferMoneyJob implements Job {

  private static Logger logger = LogManager.getLogger(RecurringTransferMoneyJob.class.getName());
  @Autowired
  private UserService userService;

  @Autowired
  private TransactionService transactionService;


  @Override
  public void execute(JobExecutionContext context)  {
    try {
      SchedulerContext schedulerContext = context.getScheduler().getContext();
      TransferMoneyDTO payload = (TransferMoneyDTO) schedulerContext.get("payload");
      Principal principal = (Principal) schedulerContext.get("principal");

//      String recipientName = payload.getRecipientName();
      String accountNumber = payload.getAccountNumber();
      String accountType = payload.getAccountType();
      String amount = payload.getAmount();
      User user = userService.findByUsername(principal.getName());
      Recipient recipient = transactionService.findRecipientByAccountNumber(accountNumber);
      logger.info(
          "recurring transfer money from " + user.getUsername() + " to " + recipient.getName());
      transactionService
          .toSomeoneElseTransfer(recipient, accountType, amount, user.getPrimaryAccount(),
              user.getSavingsAccount());
    } catch (SchedulerException | InsufficientBalanceException e) {
      e.printStackTrace();
      logger.error(e);
    }
  }


}