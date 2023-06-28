package com.sg.poc.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import com.sg.poc.domain.dto.IngestRequest;
import com.sg.poc.domain.entity.HistorySearchTerm;
import com.sg.poc.domain.entity.LawInjuryCase;
import com.sg.poc.exception.BusinessException;
import com.sg.poc.repository.HistorySearchTermRepository;
import com.sg.poc.repository.LawInjuryCaseRepository;
import com.sg.poc.service.LawInjuryCaseService;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
@RequiredArgsConstructor
public class LawInjuryCaseServiceImpl implements LawInjuryCaseService {

  private final ElasticsearchClient elasticsearchClient;
  private final LawInjuryCaseRepository lawInjuryCaseRepository;
  private final RestTemplate restTemplate;
  private final HistorySearchTermRepository historySearchTermRepo;
  private final static String INDEX_NAME = "law_injury_case";
  private final static String ES_FULL_TEXT_SEARCH_URL = "http://localhost:9200/law_injury_case/_search";

  @Override
  public Integer ingest(IngestRequest request) {
    if (CollectionUtils.isEmpty(request.getIds())) {
      lawInjuryCaseRepository.findAll().stream().parallel().forEach(this::save);
      return Math.toIntExact(lawInjuryCaseRepository.count());
    } else {
      AtomicInteger count = new AtomicInteger();
      request.getIds().stream().parallel().forEach(id -> {
        try {
          GetResponse<LawInjuryCase> response = retrieveDocument(id);
          if (response != null && response.found()) {
            deleteDocument(id);
          }
          lawInjuryCaseRepository.findById(id).ifPresent(injuryCase -> {
            count.getAndIncrement();
            save(injuryCase);
          });
        } catch (Exception e) {
          log.error("Ingest id: {} fails and message: {}", id, e.getMessage());
        }
      });
      return count.get();
    }
  }

  @Override
  public LawInjuryCase findById(Integer id) {
    try {
      GetResponse<LawInjuryCase> response = elasticsearchClient.get(g -> g
              .index(INDEX_NAME)
              .id(String.valueOf(id)),
          LawInjuryCase.class);
      log.info("Retrieve data completed id: ###{}", id);
      if (response.source() != null) {
        return response.source();
      } else {
        throw new BusinessException("Can not find LawInjuryCase with id " + id);
      }
    } catch (Exception e) {
      log.error("Cannot retrieve data id: {} and message: {}", id, e.getMessage());
      throw new BusinessException(e.getMessage());
    }
  }

  @Override
  @SneakyThrows
  public Object search(String searchTerm) {
    if (StringUtils.hasText(searchTerm)
        && CollectionUtils.isEmpty(historySearchTermRepo.findBySearchTerm(searchTerm))) {
      historySearchTermRepo.save(new HistorySearchTerm(searchTerm, LocalDateTime.now()));
    }
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    String requestBody = String.format("""
        {
          "query": {
            "multi_match": {
              "query": "%s"
            }
          }
        }""", searchTerm);
    HttpEntity<String> requestEntity = new HttpEntity<>(
        StringUtils.hasText(searchTerm) ? requestBody : null, headers);
    ResponseEntity<Object> responseEntity = restTemplate.exchange(
        ES_FULL_TEXT_SEARCH_URL,
        HttpMethod.POST,
        requestEntity,
        Object.class);
    return responseEntity.getBody();
  }

  private void deleteDocument(Integer id) {
    try {
      DeleteRequest deleteRequest = DeleteRequest.of(
          d -> d.index(INDEX_NAME).id(String.valueOf(id)));
      elasticsearchClient.delete(deleteRequest);
      log.info("Delete completed id: {}", id);
    } catch (Exception e) {
      log.error("Cannot delete data id: {} and message: {}", id, e.getMessage());
    }
  }


  public GetResponse<LawInjuryCase> retrieveDocument(Integer id) {
    try {
      GetResponse<LawInjuryCase> response = elasticsearchClient.get(g -> g
              .index(INDEX_NAME)
              .id(String.valueOf(id)),
          LawInjuryCase.class);
      log.info("Retrieve data completed id: ###{}", id);
      return response;
    } catch (Exception e) {
      log.error("Cannot retrieve data id: {} and message: {}", id, e.getMessage());
      throw new BusinessException(e.getMessage());
    }
  }

  private void save(LawInjuryCase obj) {
    try {
      elasticsearchClient.index(i -> i
          .index(INDEX_NAME)
          .id(String.valueOf(obj.getId()))
          .document(obj)
      );
      log.info("Ingest data completed id: ###{}", obj.getId());
    } catch (Exception e) {
      log.error("Cannot ingest data id: {} and message: {}", obj.getId(), e.getMessage());
      throw new BusinessException(e.getMessage());
    }
  }

  private Boolean isExist() {
    try {
      CreateIndexResponse indexResponse = elasticsearchClient.indices()
          .create(c -> c.index(INDEX_NAME));
      return indexResponse.acknowledged();
    } catch (Exception e) {
      return Boolean.FALSE;
    }
  }
}
