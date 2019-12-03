package com.hendisantika.onlinebanking.job;

import com.hendisantika.onlinebanking.entity.AccountsMoneyTransferDTO;
import com.hendisantika.onlinebanking.entity.User;
import com.hendisantika.onlinebanking.service.TransactionService;
import com.hendisantika.onlinebanking.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class RecurringAccountsMoneyTransfer implements Job {
    private static Logger logger = LogManager.getLogger(RecurringAccountsMoneyTransfer.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @Override
    public void execute(JobExecutionContext context) {
        try{
            SchedulerContext schedulerContext = context.getScheduler().getContext();
            AccountsMoneyTransferDTO payload = (AccountsMoneyTransferDTO) schedulerContext.get("payload");
            Principal principal = (Principal) schedulerContext.get("principal");

            String transferFrom = payload.getTransferFrom();
            String transferTo = payload.getTransferTo();
            String amount = payload.getAmount();
            User user = userService.findByUsername(principal.getName());
            logger.info("Recurring transfer money from " + transferFrom + " to " + transferTo);
            transactionService.betweenAccountsTransfer(transferFrom, transferTo, amount,
                    user.getPrimaryAccount(),
                    user.getSavingsAccount());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }
    }
}
