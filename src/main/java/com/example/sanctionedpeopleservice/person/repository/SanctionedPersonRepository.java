package com.example.sanctionedpeopleservice.person.repository;

import com.example.sanctionedpeopleservice.person.model.SanctionedPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SanctionedPersonRepository extends JpaRepository<SanctionedPerson, Long>, JpaSpecificationExecutor<SanctionedPerson> {
  Optional<SanctionedPerson> findByPersonName(String personName);
}