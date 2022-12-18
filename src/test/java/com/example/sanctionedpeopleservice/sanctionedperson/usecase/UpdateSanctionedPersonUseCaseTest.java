package com.example.sanctionedpeopleservice.sanctionedperson.usecase;

import com.example.sanctionedpeopleservice.common.error.UseCaseError;
import com.example.sanctionedpeopleservice.common.error.UseCaseResult;
import com.example.sanctionedpeopleservice.sanctionedperson.model.SanctionedPerson;
import com.example.sanctionedpeopleservice.sanctionedperson.repository.SanctionedPersonRepository;
import com.example.sanctionedpeopleservice.sanctionedperson.resource.SanctionedPersonUpdateRequest;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.example.sanctionedpeopleservice.common.error.model.ErrorStatus.PERSON_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpdateSanctionedPersonUseCaseTest {
  private final SanctionedPersonRepository sanctionedPersonRepository = mock(SanctionedPersonRepository.class);
  private final UpdateSanctionedPersonUseCase updateSanctionedPersonUseCase =
      new UpdateSanctionedPersonUseCase(sanctionedPersonRepository);

  private final String personName = "personName";

  @Test
  void updateSanctionedPerson() {
    String newPersonName = "newPersonName";

    SanctionedPersonUpdateRequest updateRequest = SanctionedPersonUpdateRequest.builder()
        .newPersonName(newPersonName)
        .build();

    when(sanctionedPersonRepository.findByPersonName(personName)).thenReturn(Optional.of(SanctionedPerson.builder()
                                                                                             .id(1L)
                                                                                             .personName(personName)
                                                                                             .build()));

    UseCaseResult<SanctionedPerson> useCaseResult = updateSanctionedPersonUseCase.updateSanctionedPerson(personName,
                                                                                                         updateRequest);

    verify(sanctionedPersonRepository).save(useCaseResult.getResult());
    assertEquals(newPersonName, useCaseResult.getResult().getPersonName());
  }

  @Test
  void updateSanctionedPerson_NotFound() {
    SanctionedPersonUpdateRequest updateRequest = SanctionedPersonUpdateRequest.builder()
        .newPersonName("newPersonName")
        .build();

    Optional<UseCaseError> useCaseError = updateSanctionedPersonUseCase.updateSanctionedPerson(personName,
                                                                                               updateRequest).getError();

    assertTrue(useCaseError.isPresent());
    assertError(useCaseError.get(), "Person: ".concat(personName).concat(" does not exist in sanctioned people list!"));
  }

  private void assertError(UseCaseError useCaseError,
                           String expectedError) {
    assertEquals("Person does not exist", useCaseError.message());
    assertEquals(expectedError, useCaseError.errorDetails().get(0).getMessage());
    assertEquals(PERSON_NOT_FOUND, useCaseError.status());
  }
}