package com.hendisantika.onlinebanking.controller;

import com.hendisantika.onlinebanking.entity.ApiResultEnum;
import com.hendisantika.onlinebanking.entity.QuartzBean;
import com.hendisantika.onlinebanking.entity.Recipient;
import com.hendisantika.onlinebanking.entity.Result;
import com.hendisantika.onlinebanking.entity.TransferMoneyDTO;
import com.hendisantika.onlinebanking.entity.User;
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
    Recipient recipient = transactionService.findRecipientByAccountNumber(payload.getAccountNumber());

    User user = userService.findByUsername(principal.getName());

    transactionService
        .toSomeoneElseTransfer(recipient, payload.getAccountType(), payload.getAmount(),
            user.getPrimaryAccount(),
            user.getSavingsAccount());

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
}
