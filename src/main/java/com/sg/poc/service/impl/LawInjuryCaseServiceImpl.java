package com.sg.poc.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sg.poc.domain.dto.LawInjuryCaseDto;
import com.sg.poc.domain.entity.LawInjuryCase;
import com.sg.poc.domain.es.LawInjuryCaseES;
import com.sg.poc.repository.LawInjuryCaseRepository;
import com.sg.poc.repository.RelatedKeywordRepository;
import com.sg.poc.service.LawInjuryCaseService;
import java.util.LinkedList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LawInjuryCaseServiceImpl implements LawInjuryCaseService {

  private final ObjectMapper objectMapper;
  private final LawInjuryCaseRepository lawInjuryCaseRepo;
  private final ElasticsearchClient elasticsearchClient;
  private final RelatedKeywordRepository relatedKeywordRepo;
  private final String lawInjuryCaseIndexName = "law_injury_case";

  @Override
  @SneakyThrows
  public String convertAndPushToES(LawInjuryCaseDto lawInjuryCaseDto) {
    LawInjuryCase lawInjuryCase = toEntity(lawInjuryCaseDto);
    lawInjuryCase.setDoc_json(objectMapper.writeValueAsString(lawInjuryCaseDto));
    LawInjuryCase savedLawInjuryCase = lawInjuryCaseRepo.save(lawInjuryCase);
    LawInjuryCaseES documentLawInjuryCaseES = toDocument(savedLawInjuryCase);
    elasticsearchClient.index(i -> i
        .index(lawInjuryCaseIndexName)
        .id(String.valueOf(savedLawInjuryCase.getId()))
        .document(documentLawInjuryCaseES)
    );

    return savedLawInjuryCase.getDoc_json();
  }

  @Override
  @SneakyThrows
  public List<LawInjuryCaseES> search(String keyword) {
    // TODO: get related keyword
    // relatedKeywordRepo
    SearchResponse<LawInjuryCaseES> response = elasticsearchClient.search(s -> s
            .index(lawInjuryCaseIndexName)
            .query(q -> q
                .match(t -> t
                    .field("doc_json")
                    .query(keyword)
                )
            ),
        LawInjuryCaseES.class
    );
    List<LawInjuryCaseES> lawInjuryCaseES = new LinkedList<>();
    List<Hit<LawInjuryCaseES>> hits = response.hits().hits();
    hits.forEach(lawInjuryCaseESHit -> lawInjuryCaseES.add(lawInjuryCaseESHit.source()));
    return lawInjuryCaseES;
  }

  LawInjuryCase toEntity(LawInjuryCaseDto lawInjuryCaseDto) {
    LawInjuryCase lawInjuryCase = new LawInjuryCase();
    lawInjuryCase.setUuid(lawInjuryCaseDto.getUuid());
    lawInjuryCase.setName(lawInjuryCaseDto.getName());
    lawInjuryCase.setParty_name(lawInjuryCaseDto.getParty_name());
    lawInjuryCase.setLiability(lawInjuryCaseDto.getLiability());
    lawInjuryCase.setAccident_time(lawInjuryCaseDto.getAccident_time());
    lawInjuryCase.setAsessed_time(lawInjuryCaseDto.getAsessed_time());
    lawInjuryCase.setPlaintiff_sex(lawInjuryCaseDto.getPlaintiff_sex());
    lawInjuryCase.setPlaintiff_age(lawInjuryCaseDto.getPlaintiff_age());
    lawInjuryCase.setPlaintiff_job(lawInjuryCaseDto.getPlaintiff_job());
    lawInjuryCase.setInjury(lawInjuryCaseDto.getInjury());
    lawInjuryCase.setTreatment(lawInjuryCaseDto.getTreatment());
    lawInjuryCase.setDisabilities(lawInjuryCaseDto.getDisabilities());
    lawInjuryCase.setAwarded(lawInjuryCaseDto.getAwarded());
    return lawInjuryCase;
  }

  LawInjuryCaseES toDocument(LawInjuryCase injuryCase) {
    LawInjuryCaseES lawInjuryCaseES = new LawInjuryCaseES();
    lawInjuryCaseES.setId(String.valueOf(injuryCase.getId()));
    lawInjuryCaseES.setUuid(injuryCase.getUuid());
    lawInjuryCaseES.setName(injuryCase.getName());
    lawInjuryCaseES.setParty_name(injuryCase.getParty_name());
    lawInjuryCaseES.setLiability(injuryCase.getLiability());
    lawInjuryCaseES.setPlaintiff_sex(injuryCase.getPlaintiff_sex());
    lawInjuryCaseES.setPlaintiff_age(injuryCase.getPlaintiff_age());
    lawInjuryCaseES.setPlaintiff_job(injuryCase.getPlaintiff_job());
    lawInjuryCaseES.setInjury(injuryCase.getInjury());
    lawInjuryCaseES.setTreatment(injuryCase.getTreatment());
    lawInjuryCaseES.setDisabilities(injuryCase.getDisabilities());
    lawInjuryCaseES.setAwarded(injuryCase.getAwarded());
    lawInjuryCaseES.setDoc_json(injuryCase.getDoc_json());
    return lawInjuryCaseES;
  }
}
