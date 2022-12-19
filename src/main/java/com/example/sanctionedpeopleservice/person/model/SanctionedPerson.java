package com.example.sanctionedpeopleservice.person.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "SANCTIONED_PEOPLE"
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SanctionedPerson {
  @Id
  @SequenceGenerator(
      name = "SANCTIONED_PEOPLE_SEQ",
      allocationSize = 1
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "SANCTIONED_PEOPLE_SEQ"
  )
  private Long id;

  @Column(name = "PERSON_NAME")
  private String personName;
}