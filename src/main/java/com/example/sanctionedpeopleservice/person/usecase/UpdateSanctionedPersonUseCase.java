package com.example.sanctionedpeopleservice.person.usecase;

import com.example.sanctionedpeopleservice.common.error.UseCaseError;
import com.example.sanctionedpeopleservice.common.error.UseCaseResult;
import com.example.sanctionedpeopleservice.common.error.response.ErrorDetail;
import com.example.sanctionedpeopleservice.person.model.SanctionedPerson;
import com.example.sanctionedpeopleservice.person.repository.SanctionedPersonRepository;
import com.example.sanctionedpeopleservice.person.resource.SanctionedPersonUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.sanctionedpeopleservice.common.error.model.ErrorStatus.PERSON_NOT_FOUND;

@Service
public class UpdateSanctionedPersonUseCase {
  private final SanctionedPersonRepository sanctionedPersonRepository;

  public UpdateSanctionedPersonUseCase(SanctionedPersonRepository sanctionedPersonRepository) {
    this.sanctionedPersonRepository = sanctionedPersonRepository;
  }

  public UseCaseResult<SanctionedPerson> updateSanctionedPerson(Long personId,
                                                                SanctionedPersonUpdateRequest request) {
    return sanctionedPersonRepository.findById(personId)
        .map(sanctionedPerson -> updatePerson(sanctionedPerson, request.getNewPersonName()))
        .orElseGet(() -> new UseCaseResult<>(createPersonNonExistenceError(personId)));
  }

  private UseCaseResult<SanctionedPerson> updatePerson(SanctionedPerson sanctionedPerson, String newPersonName) {
    sanctionedPerson.setPersonName(newPersonName);
    sanctionedPersonRepository.save(sanctionedPerson);

    return new UseCaseResult<>(sanctionedPerson);
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