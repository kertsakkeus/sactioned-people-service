package com.example.sanctionedpeopleservice.person.usecase;

import com.example.sanctionedpeopleservice.common.error.UseCaseError;
import com.example.sanctionedpeopleservice.common.error.UseCaseResult;
import com.example.sanctionedpeopleservice.person.model.SanctionedPerson;
import com.example.sanctionedpeopleservice.person.repository.SanctionedPersonRepository;
import com.example.sanctionedpeopleservice.person.resource.SanctionedPersonUpdateRequest;
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

  private final Long personId = 1L;

  @Test
  void updateSanctionedPerson() {
    String newPersonName = "newPersonName";

    SanctionedPersonUpdateRequest updateRequest = SanctionedPersonUpdateRequest.builder()
        .newPersonName(newPersonName)
        .build();

    when(sanctionedPersonRepository.findById(personId)).thenReturn(Optional.of(SanctionedPerson.builder()
                                                                                             .id(1L)
                                                                                             .build()));

    UseCaseResult<SanctionedPerson> useCaseResult = updateSanctionedPersonUseCase.updateSanctionedPerson(personId,
                                                                                                         updateRequest);

    verify(sanctionedPersonRepository).save(useCaseResult.getResult());
    assertEquals(newPersonName, useCaseResult.getResult().getPersonName());
  }

  @Test
  void updateSanctionedPerson_NotFound() {
    SanctionedPersonUpdateRequest updateRequest = SanctionedPersonUpdateRequest.builder()
        .newPersonName("newPersonName")
        .build();

    Optional<UseCaseError> useCaseError = updateSanctionedPersonUseCase.updateSanctionedPerson(personId,
                                                                                               updateRequest).getError();

    assertTrue(useCaseError.isPresent());
    assertError(useCaseError.get(), "Person with id ".concat(personId.toString())
        .concat(" does not exist in sanctioned people list!"));
  }

  private void assertError(UseCaseError useCaseError,
                           String expectedError) {
    assertEquals("Person does not exist", useCaseError.message());
    assertEquals(expectedError, useCaseError.errorDetails().get(0).getMessage());
    assertEquals(PERSON_NOT_FOUND, useCaseError.status());
  }
}