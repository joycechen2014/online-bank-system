package com.hendisantika.onlinebanking.exception;

public class InsufficientBalanceException extends Exception {

  public InsufficientBalanceException(String message, Throwable cause) {
    super(message, cause);
  }


  public InsufficientBalanceException(String s) {
    super(s);
  }
}
