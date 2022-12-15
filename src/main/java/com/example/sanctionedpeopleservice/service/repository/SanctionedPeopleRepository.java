package com.example.sanctionedpeopleservice.service.repository;

import com.example.sanctionedpeopleservice.service.model.SanctionedPeople;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SanctionedPeopleRepository extends JpaRepository<SanctionedPeople, Long>, JpaSpecificationExecutor<SanctionedPeople> {

}