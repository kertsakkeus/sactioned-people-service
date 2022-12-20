package com.example.sanctionedpeopleservice.person.usecase;

import com.example.sanctionedpeopleservice.common.error.UseCaseError;
import com.example.sanctionedpeopleservice.common.error.UseCaseResult;
import com.example.sanctionedpeopleservice.common.error.response.ErrorDetail;
import com.example.sanctionedpeopleservice.person.model.SanctionedPerson;
import com.example.sanctionedpeopleservice.person.repository.SanctionedPersonRepository;
import com.example.sanctionedpeopleservice.person.resource.SanctionedPersonRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.sanctionedpeopleservice.common.error.model.ErrorStatus.PERSON_EXIST;

@Service
public class AddSanctionedPersonUseCase {
  private final SanctionedPersonRepository sanctionedPersonRepository;

  public AddSanctionedPersonUseCase(SanctionedPersonRepository sanctionedPersonRepository) {
    this.sanctionedPersonRepository = sanctionedPersonRepository;
  }

  public UseCaseResult<SanctionedPerson> addSanctionedPerson(SanctionedPersonRequest request) {
    return sanctionedPersonRepository.findByPersonName(request.getPersonName())
        .map(sanctionedPerson -> new UseCaseResult<SanctionedPerson>(createExistingPersonError(sanctionedPerson.getId())))
        .orElseGet(() -> new UseCaseResult<>(sanctionedPersonRepository.save(SanctionedPerson.builder()
            .personName(request.getPersonName())
            .build())));
  }

  private UseCaseError createExistingPersonError(Long personId) {
    return new UseCaseError("Person exist", PERSON_EXIST,
        List.of(ErrorDetail.builder()
            .message("Person with id: "
                .concat(personId.toString())
                .concat(" already exist in sanctioned people list!"))
            .build()));
  }
}