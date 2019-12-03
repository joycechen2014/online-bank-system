package com.hendisantika.onlinebanking.entity;

public class AccountsMoneyTransferDTO {
    private String transferFrom;
    private String transferTo;
    private String amount;

    private String cron;
    public AccountsMoneyTransferDTO(){

    }

    public String getTransferFrom(){
        return transferFrom;
    }
    public void setTransferFrom(String transferFrom) {
        this.transferFrom = transferFrom;
    }

    public String getTransferTo(){
        return transferTo;
    }
    public void setTransferTo(String transferTo){
        this.transferTo = transferTo;
    }

    public String getAmount(){
        return amount;
    }
    public void setAmount(String amount){
        this.amount = amount;
    }

    public String getCron() {
        return cron;
    }
    public void setCron(String cron) {
        this.cron = cron;
    }
}
