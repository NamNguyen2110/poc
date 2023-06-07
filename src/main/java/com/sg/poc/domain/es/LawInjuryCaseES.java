package com.sg.poc.domain.es;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "LawInjuryCase")
@Data
public class LawInjuryCaseES {

  @Id
  private String id;
  @Field(type = FieldType.Text, name = "uuid")
  private String uuid;
  @Field(type = FieldType.Text, name = "name")
  private String name;
  @Field(type = FieldType.Text, name = "party_name")
  private String party_name;
  @Field(type = FieldType.Text, name = "liability")
  private String liability;
  @Field(type = FieldType.Text, name = "plaintiff_sex")
  private String plaintiff_sex;
  @Field(type = FieldType.Text, name = "plaintiff_age")
  private String plaintiff_age;
  @Field(type = FieldType.Text, name = "plaintiff_job")
  private String plaintiff_job;
  @Field(type = FieldType.Text, name = "injury")
  private String injury;
  @Field(type = FieldType.Text, name = "treatment")
  private String treatment;
  @Field(type = FieldType.Text, name = "disabilities")
  private String disabilities;
  @Field(type = FieldType.Text, name = "awarded")
  private String awarded;
  @Field(type = FieldType.Text, name = "doc_json")
  private String doc_json;

//  @Column(name = "accident_time")
//  private Timestamp accident_time;
//  @Column(name = "asessed_time")
//  private Timestamp asessed_time;
}
