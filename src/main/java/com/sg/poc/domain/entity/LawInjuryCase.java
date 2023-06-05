package com.sg.poc.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "law_injurry_case")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class LawInjuryCase {

  @Id
  @Column(name = "id")
  private Integer id;
  @Column(name = "uuid")
  private String uuid;
  @Column(name = "name")
  private String name;
  @Column(name = "party_name")
  private String party_name;
  @Column(name = "liability")
  private String liability;
  @Column(name = "accident_time")
  private Timestamp accident_time;
  @Column(name = "asessed_time")
  private Timestamp asessed_time;
  @Column(name = "plaintiff_sex")
  private String plaintiff_sex;
  @Column(name = "plaintiff_age")
  private String plaintiff_age;
  @Column(name = "plaintiff_job")
  private String plaintiff_job;
  @Column(name = "injury")
  private String injury;
  @Column(name = "treatment")
  private String treatment;
  @Column(name = "disabilities")
  private String disabilities;
  @Column(name = "awarded")
  private String awarded;
  @Column(name = "doc_json")
  private String doc_json;
}
