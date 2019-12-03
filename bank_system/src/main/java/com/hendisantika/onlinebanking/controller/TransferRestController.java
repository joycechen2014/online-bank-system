package com.hendisantika.onlinebanking.controller;

import com.hendisantika.onlinebanking.entity.*;
import com.hendisantika.onlinebanking.exception.InsufficientBalanceException;
import com.hendisantika.onlinebanking.service.SchedulerService;
import com.hendisantika.onlinebanking.service.TransactionService;
import com.hendisantika.onlinebanking.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Transfer", description = "Onetime/Recurring Transfer API")
@RestController
public class TransferRestController {

  Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private TransactionService transactionService;

  @Autowired
  private UserService userService;

  @Autowired
  private SchedulerService schedulerService;

  private final String jobClassLiteral = "com.hendisantika.onlinebanking.job.RecurringTransferMoneyJob";
  private final String jobClassLiteral2 = "com.hendisantika.onlinebanking.job.RecurringAccountsMoneyTransfer";

  @ApiOperation("start recurring transfer api")
  @PostMapping(value = "/recurring/transfer/start")
  public Result triggerRecurringJob(@RequestBody TransferMoneyDTO payload, Principal principal) {

    QuartzBean bean = new QuartzBean();
    bean.setId("001");
    bean.setJobName(jobClassLiteral);
    bean.setJobClass(jobClassLiteral);
    bean.setCronExpression(payload.getCron());

    try {
      schedulerService.createScheduleJob(bean, payload, principal);
    } catch (SchedulerException e) {
      logger.error("Create recurring job encounter error");
      e.printStackTrace();
      return Result.error(ApiResultEnum.CREATING_RECURRING_JOB_ERROR);
    }
    return Result.ok();
  }

  @ApiOperation("start onetime transfer api")
  @PostMapping(value = "/onetime/transfer/start")
  public Result triggerOneTimeJob(@RequestBody TransferMoneyDTO payload, Principal principal) {

//    Recipient recipient = transactionService.findRecipientByName(payload.getRecipientName());
    Recipient recipient = transactionService
        .findRecipientByAccountNumber(payload.getAccountNumber());

    User user = userService.findByUsername(principal.getName());

    try {
      transactionService
          .toSomeoneElseTransfer(recipient, payload.getAccountType(), payload.getAmount(),
              user.getPrimaryAccount(),
              user.getSavingsAccount());
    } catch (InsufficientBalanceException e) {
      e.printStackTrace();
      return Result.error(ApiResultEnum.INSUFFICIENT_BALANCE_ERROR);
    }

    return Result.ok();
  }

  @ApiOperation("shutdown recurring transfer api")
  @GetMapping("/recurring/transfer/shutdown")
  public Result shutdown() {
    try {
      schedulerService.deleteScheduleJob(jobClassLiteral);
    } catch (SchedulerException e) {
      e.printStackTrace();
      logger.error("shutting job encounter error!");
      return Result.error(ApiResultEnum.SHUTTING_RECURRING_JOB_ERROR);
    }
    return Result.ok();
  }
  
  @ApiOperation("start onetime transfer between accounts")
  @PostMapping(value = "/onetime/transferbetweenaccounts")
  public Result TriggerOnetimeBetweenAccounts(@RequestBody AccountsMoneyTransferDTO payload, Principal principal) {
    User user = userService.findByUsername(principal.getName());
    try {transactionService.betweenAccountsTransfer(payload.getTransferFrom(), payload.getTransferTo(), payload.getAmount(),
            user.getPrimaryAccount(),
            user.getSavingsAccount());
    } catch (Exception e) {
      e.printStackTrace();
      return Result.error(ApiResultEnum.CREATING_TRANSFER_JOB_ERROR);
    }
    return Result.ok();
  }
}
