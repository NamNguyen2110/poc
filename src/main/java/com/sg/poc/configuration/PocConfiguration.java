package com.sg.poc.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class PocConfiguration {

  @Bean
  public ObjectMapper objectMapper(){
    return new ObjectMapper();
  }
  @Bean
  public RestTemplate restTemplate(){
    return new RestTemplate();
  }


}
