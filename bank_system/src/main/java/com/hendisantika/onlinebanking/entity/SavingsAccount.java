package com.hendisantika.onlinebanking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.*;

/**
 * Created by IntelliJ IDEA. Project : online-banking User: Lijuan Hou
 * template use File | Settings | File Templates.
 */
@Entity
public class SavingsAccount {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private int accountNumber;
  private BigDecimal accountBalance;
  @Column(name = "enabled", columnDefinition="BIT")
  private Boolean enabled;

  @OneToMany(mappedBy = "savingsAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  private List<SavingsTransaction> savingsTransactionList;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(int accountNumber) {
    this.accountNumber = accountNumber;
  }

  public BigDecimal getAccountBalance() {
    return accountBalance;
  }

  public void setAccountBalance(BigDecimal accountBalance) {
    this.accountBalance = accountBalance;
  }

  public List<SavingsTransaction> getSavingsTransactionList() {
    return savingsTransactionList;
  }

  public void setSavingsTransactionList(List<SavingsTransaction> savingsTransactionList) {
    this.savingsTransactionList = savingsTransactionList;
  }

  public Boolean isEnabled() {
    return enabled;
  }

  public void enable() {
    enabled = true;
  }

  public void disable() {
    enabled = false;
  }

}