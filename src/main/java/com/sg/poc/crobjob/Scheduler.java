package com.sg.poc.crobjob;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Scheduler {

  @Scheduled(fixedDelay = 1000)
  public void scheduleFixedDelayTask() {
    log.info("Fixed delay task - {}", System.currentTimeMillis() / 1000);
  }
}
