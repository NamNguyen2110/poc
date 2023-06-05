package com.sg.poc.controller;

import java.util.Random;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/poc/v1/hello")
@RequiredArgsConstructor
@Slf4j
public class HelloController {

  @GetMapping("")
  public ResponseEntity<?> quotes() {
    return ResponseEntity.ok(
        (new Random().nextBoolean() ? "Today is the first day of the rest of your life"
            : "Another day, another chance"));
  }
}
