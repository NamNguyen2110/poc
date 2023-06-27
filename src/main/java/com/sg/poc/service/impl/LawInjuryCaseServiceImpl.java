package com.sg.poc.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import com.sg.poc.domain.dto.IngestRequest;
import com.sg.poc.domain.entity.LawInjuryCase;
import com.sg.poc.exception.BusinessException;
import com.sg.poc.repository.LawInjuryCaseRepository;
import com.sg.poc.service.LawInjuryCaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class LawInjuryCaseServiceImpl implements LawInjuryCaseService {

  private final ElasticsearchClient elasticsearchClient;
  private final LawInjuryCaseRepository lawInjuryCaseRepository;
  private final static String INDEX_NAME = "law_injury_case";

  @Override
  public Integer ingest(IngestRequest request) {
    request.getIds().stream().parallel().forEach(id -> {
      try {
        GetResponse<LawInjuryCase> response = retrieveDocument(id);
        if (response != null && response.found()) {
          deleteDocument(id);
        }
        lawInjuryCaseRepository.findById(id).ifPresent(this::save);
      } catch (Exception e) {
        log.error("Ingest id: {} fails and message: {}", id, e.getMessage());
      }
    });
    return 0;
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
  public LawInjuryCase search(String searchTerm) {
    return null;
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
