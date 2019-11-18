package com.hendisantika.onlinebanking.controller;

import com.hendisantika.onlinebanking.entity.Refund;
import com.hendisantika.onlinebanking.entity.Result;
import com.hendisantika.onlinebanking.service.AccountService;
import com.hendisantika.onlinebanking.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "AdminController", description = "Only Admin Role Can Visit")
@RestController
@RequestMapping(value = "/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminCanDoRestController {

  @Autowired
  private AccountService accountService;

  @Autowired
  private UserService userService;


  @ApiOperation("admin user use this to refund money")
  @RequestMapping(value = "/refund", method = RequestMethod.POST)
  public Result depositPOST(@RequestBody Refund refund) {

    accountService.deposit(refund.getAccountType(), Double.parseDouble(
        String.valueOf(refund.getAmount())), refund.getTargetUserName());

    return Result.ok();
  }


}
