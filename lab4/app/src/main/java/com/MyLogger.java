package com;

import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class MyLogger {
  public static Logger getLogger(String name) {
    Logger logger = Logger.getLogger(name);
    logger.setUseParentHandlers(false);
    ConsoleHandler handler = new ConsoleHandler();
    handler.setFormatter(new Formatter() {
      @Override
      public String format(LogRecord record) {
        return String.format("[%s] - %s %n", record.getLevel(),
            record.getMessage());
      }
    });
    logger.addHandler(handler);
    return logger;
  }
}