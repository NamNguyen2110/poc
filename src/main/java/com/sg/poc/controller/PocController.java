package com.sg.poc.controller;

import com.sg.poc.domain.dto.LawInjuryCaseDto;
import com.sg.poc.domain.entity.LawInjuryCase;
import com.sg.poc.service.LawInjuryCaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/poc/v1/es")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class PocController {

  private final LawInjuryCaseService injuryCaseService;
  @PostMapping("/convert")
  public ResponseEntity<?> es(@RequestBody LawInjuryCaseDto injuryCase) {
    return ResponseEntity.ok(injuryCaseService.convertAndPushToES(injuryCase));
  }

  @GetMapping("")
  public ResponseEntity<?> search(@RequestParam String keyword) {
    return ResponseEntity.ok(injuryCaseService.search(keyword));
  }
}
