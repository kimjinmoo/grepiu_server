package com.grepiu.www.process.sample.util.socket.module.pool;

import java.util.Optional;

class SejongInvalid implements ValidationResult {

  private final String reason;

  SejongInvalid(String reason) {
    this.reason = reason;
  }

  public boolean isValid() {
    return false;
  }

  public Optional<String> getReason() {
    return Optional.of(reason);
  }
}