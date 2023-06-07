package com.sg.poc.service;

import com.sg.poc.domain.dto.LawInjuryCaseDto;
import com.sg.poc.domain.es.LawInjuryCaseES;
import java.util.List;

public interface LawInjuryCaseService {
  String convertAndPushToES(LawInjuryCaseDto injuryCase);

  List<LawInjuryCaseES> search(String keyword);


}
