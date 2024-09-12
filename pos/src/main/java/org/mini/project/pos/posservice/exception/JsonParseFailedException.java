package org.mini.project.pos.posservice.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonParseFailedException extends RuntimeException {

  public JsonParseFailedException(Exception exception) {
    log.error("Failed to parse object with error {}", exception.getLocalizedMessage());
  }
}
