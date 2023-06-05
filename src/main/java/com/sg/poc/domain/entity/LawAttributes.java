package com.sg.poc.domain.entity;

import com.sg.poc.domain.converter.EntityConverter;
import com.sg.poc.domain.converter.LevelConverter;
import com.sg.poc.domain.enums.Level;
import com.sg.poc.domain.enums._Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

@Entity
@Table(name = "law_attributes")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class LawAttributes {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "name")
  private String name;

  @Column(name = "level")
  @Convert(converter = LevelConverter.class)
  private Level level;

  @Column(name = "entity")
  @Convert(converter = EntityConverter.class)
  private _Entity entity;

  @Column(name = "value")
  private String value;

  @Column(name = "parent")
  private Integer parent;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    LawAttributes that = (LawAttributes) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
