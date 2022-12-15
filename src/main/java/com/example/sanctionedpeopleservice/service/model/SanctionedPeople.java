package com.example.sanctionedpeopleservice.service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
public class SanctionedPeople {
  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "PERSON_NAME")
  private String personName;
}