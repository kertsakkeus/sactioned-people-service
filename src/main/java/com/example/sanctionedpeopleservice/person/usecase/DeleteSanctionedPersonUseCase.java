package com.example.sanctionedpeopleservice.person.usecase;

import com.example.sanctionedpeopleservice.common.error.UseCaseError;
import com.example.sanctionedpeopleservice.common.error.UseCaseResult;
import com.example.sanctionedpeopleservice.common.error.response.ErrorDetail;
import com.example.sanctionedpeopleservice.person.model.SanctionedPerson;
import com.example.sanctionedpeopleservice.person.repository.SanctionedPersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.sanctionedpeopleservice.common.error.model.ErrorStatus.PERSON_NOT_FOUND;

@Service
public class DeleteSanctionedPersonUseCase {
  private final SanctionedPersonRepository sanctionedPersonRepository;

  public DeleteSanctionedPersonUseCase(SanctionedPersonRepository sanctionedPersonRepository) {
    this.sanctionedPersonRepository = sanctionedPersonRepository;
  }

  public UseCaseResult<SanctionedPerson> deleteSanctionedPerson(Long personId) {
    return sanctionedPersonRepository.findById(personId)
        .map(sanctionedPerson -> {
          sanctionedPersonRepository.deleteById(sanctionedPerson.getId());
          return new UseCaseResult<>(sanctionedPerson);
        })
        .orElseGet(() -> new UseCaseResult<>(createPersonNonExistenceError(personId)));
  }

  private UseCaseError createPersonNonExistenceError(Long personId) {
    return new UseCaseError("Person does not exist", PERSON_NOT_FOUND,
        List.of(ErrorDetail.builder()
            .message("Person with id: "
                .concat(personId.toString())
                .concat(" does not exist in sanctioned people list!"))
            .build()));
  }
}