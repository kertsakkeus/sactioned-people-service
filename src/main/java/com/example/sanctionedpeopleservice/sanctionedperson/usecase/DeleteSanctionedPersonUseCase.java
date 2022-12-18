package com.example.sanctionedpeopleservice.sanctionedperson.usecase;

import com.example.sanctionedpeopleservice.common.error.UseCaseError;
import com.example.sanctionedpeopleservice.common.error.UseCaseResult;
import com.example.sanctionedpeopleservice.common.error.response.ErrorDetail;
import com.example.sanctionedpeopleservice.sanctionedperson.model.SanctionedPerson;
import com.example.sanctionedpeopleservice.sanctionedperson.repository.SanctionedPersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.sanctionedpeopleservice.common.error.model.ErrorStatus.PERSON_NOT_FOUND;

@Service
public class DeleteSanctionedPersonUseCase {
  private final SanctionedPersonRepository sanctionedPersonRepository;

  public DeleteSanctionedPersonUseCase(SanctionedPersonRepository sanctionedPersonRepository) {
    this.sanctionedPersonRepository = sanctionedPersonRepository;
  }

  public UseCaseResult<SanctionedPerson> deleteSanctionedPerson(String personName) {
    return sanctionedPersonRepository.findByPersonName(personName)
        .map(sanctionedPerson -> {
          sanctionedPersonRepository.deleteById(sanctionedPerson.getId());
          return new UseCaseResult<>(sanctionedPerson);
        })
        .orElseGet(() -> new UseCaseResult<>(createPersonNonExistenceError(personName)));
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