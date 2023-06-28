package com.sg.poc.controller;

import com.sg.poc.domain.dto.ApiResponse;
import com.sg.poc.domain.dto.IngestRequest;
import com.sg.poc.service.LawInjuryCaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/poc/v1")
@RequiredArgsConstructor
@Slf4j
public class ElasticSearchController {

  private final LawInjuryCaseService injuryCaseService;

  @PostMapping(value = "/cases")
  public ResponseEntity<ApiResponse<Object>> ingest(@RequestBody IngestRequest request) {
    return ResponseEntity.ok(ApiResponse.success(injuryCaseService.ingest(request)));
  }

  @GetMapping(value = "/cases")
  public ResponseEntity<ApiResponse<Object>> findById(@RequestParam("id") Integer id) {
    return ResponseEntity.ok(ApiResponse.success(injuryCaseService.findById(id)));
  }

  @GetMapping(value = "/search/cases")
  public ResponseEntity<ApiResponse<Object>> search(
      @RequestParam(value = "searchTerm", required = false) String searchTerm) {
    return ResponseEntity.ok(ApiResponse.success(injuryCaseService.search(searchTerm)));
  }
}
