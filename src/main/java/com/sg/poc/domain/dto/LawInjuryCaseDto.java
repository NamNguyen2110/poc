package com.sg.poc.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LawInjuryCaseDto {
  @JsonProperty("uuid")
  private String uuid;
  @JsonProperty("name")
  private String name;
  @JsonProperty("party_name")
  private String party_name;
  @JsonProperty("liability")
  private String liability;
  @JsonProperty("accident_time")
  private Timestamp accident_time;
  @JsonProperty("asessed_time")
  private Timestamp asessed_time;
  @JsonProperty("plaintiff_sex")
  private String plaintiff_sex;
  @JsonProperty("plaintiff_age")
  private String plaintiff_age;
  @JsonProperty("plaintiff_job")
  private String plaintiff_job;
  @JsonProperty("injury")
  private String injury;
  @JsonProperty("treatment")
  private String treatment;
  @JsonProperty("disabilities")
  private String disabilities;
  @JsonProperty("awarded")
  private String awarded;
}
