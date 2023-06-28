package com.sg.poc.service;


import com.sg.poc.domain.dto.IngestRequest;
import com.sg.poc.domain.entity.LawInjuryCase;

public interface LawInjuryCaseService {

  Integer ingest(IngestRequest request);

  LawInjuryCase findById(Integer id);

  Object search(String searchTerm);

}
