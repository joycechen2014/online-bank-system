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
public class PrimaryAccount {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private int accountNumber;
  private BigDecimal accountBalance;
  @Column(name = "enabled", columnDefinition="BIT")
  private Boolean enabled;

  @OneToMany(mappedBy = "primaryAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  private List<PrimaryTransaction> primaryTransactionList;

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

  public List<PrimaryTransaction> getPrimaryTransactionList() {
    return primaryTransactionList;
  }

  public void setPrimaryTransactionList(List<PrimaryTransaction> primaryTransactionList) {
    this.primaryTransactionList = primaryTransactionList;
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
