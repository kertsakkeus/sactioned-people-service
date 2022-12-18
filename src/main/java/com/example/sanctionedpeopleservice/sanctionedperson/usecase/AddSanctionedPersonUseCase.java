package com.example.sanctionedpeopleservice.sanctionedperson.usecase;

import com.example.sanctionedpeopleservice.common.error.UseCaseError;
import com.example.sanctionedpeopleservice.common.error.UseCaseResult;
import com.example.sanctionedpeopleservice.common.error.response.ErrorDetail;
import com.example.sanctionedpeopleservice.sanctionedperson.model.SanctionedPerson;
import com.example.sanctionedpeopleservice.sanctionedperson.repository.SanctionedPersonRepository;
import com.example.sanctionedpeopleservice.sanctionedperson.resource.SanctionedPersonRequest;
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
        .map(sanctionedPerson -> new UseCaseResult<SanctionedPerson>(createPersonError(sanctionedPerson.getPersonName())))
        .orElseGet(() -> new UseCaseResult<>(sanctionedPersonRepository.save(SanctionedPerson.builder()
            .personName(request.getPersonName())
            .build())));
  }

  private UseCaseError createPersonError(String personName) {
    return new UseCaseError("Person exist", PERSON_EXIST,
        List.of(ErrorDetail.builder()
            .message("Person: "
                .concat(personName)
                .concat(" already exist in sanctioned people list!"))
            .build()));
  }
}