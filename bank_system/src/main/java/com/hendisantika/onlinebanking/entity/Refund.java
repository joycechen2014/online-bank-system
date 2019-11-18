package com.hendisantika.onlinebanking.entity;

import java.math.BigDecimal;

public class Refund {

  private BigDecimal amount;
  private String accountType;
  private String targetUserName;

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getAccountType() {
    return accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public String getTargetUserName() {
    return targetUserName;
  }

  public void setTargetUserName(String targetUserName) {
    this.targetUserName = targetUserName;
  }
}
