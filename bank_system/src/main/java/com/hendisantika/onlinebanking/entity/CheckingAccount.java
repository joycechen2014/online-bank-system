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
public class CheckingAccount {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private int accountNumber;
  private BigDecimal accountBalance;
  @Column(name = "enabled", columnDefinition="BIT")
  private Boolean enabled;

  @OneToMany(mappedBy = "checkingAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  private List<CheckingTransaction> checkingTransactionList;

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

  public List<CheckingTransaction> getCheckingTransactionList() {
    return checkingTransactionList;
  }

  public void setCheckingTransactionList(List<CheckingTransaction> checkingTransactionList) {
    this.checkingTransactionList = checkingTransactionList;
  }

  public Boolean isEnabled() {
    return enabled;
  }

  public void disable() {
    enabled = false;
  }

  public void enable() {
    enabled = true;
  }

}
