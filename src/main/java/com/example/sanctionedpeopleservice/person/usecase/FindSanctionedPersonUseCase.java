package com.example.sanctionedpeopleservice.person.usecase;

import com.example.sanctionedpeopleservice.common.error.UseCaseError;
import com.example.sanctionedpeopleservice.common.error.UseCaseResult;
import com.example.sanctionedpeopleservice.common.error.response.ErrorDetail;
import com.example.sanctionedpeopleservice.person.repository.SanctionedPersonRepository;
import com.example.sanctionedpeopleservice.person.resource.SanctionedPersonSearchResult;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.sanctionedpeopleservice.common.error.model.ErrorStatus.PERSON_NOT_FOUND;

@Service
public class FindSanctionedPersonUseCase {
  private final SanctionedPersonRepository sanctionedPersonRepository;

  public FindSanctionedPersonUseCase(SanctionedPersonRepository sanctionedPersonRepository) {
    this.sanctionedPersonRepository = sanctionedPersonRepository;
  }

  public UseCaseResult<SanctionedPersonSearchResult> getSanctionedPerson(String personName) {
    return sanctionedPersonRepository.findByPersonName(personName)
        .map(sanctionedPerson -> new UseCaseResult<>(new SanctionedPersonSearchResult(sanctionedPerson)))
        .orElseGet(() -> new UseCaseResult<>(createPersonNonExistenceError(personName)));
  }

  public UseCaseResult<List<SanctionedPersonSearchResult>> getSanctionedPeople() {
    return new UseCaseResult<>(sanctionedPersonRepository.findAll().stream()
        .map(SanctionedPersonSearchResult::new)
        .toList());
  }

  private UseCaseError createPersonNonExistenceError(String personName) {
    return new UseCaseError("Person does not exist", PERSON_NOT_FOUND,
        List.of(ErrorDetail.builder()
            .message("Person: "
                .concat(personName)
                .concat(" does not exist in sanctioned people list!"))
            .build()));
  }
}