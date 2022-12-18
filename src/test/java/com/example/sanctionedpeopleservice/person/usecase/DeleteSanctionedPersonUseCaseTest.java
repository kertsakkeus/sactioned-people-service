package com.example.sanctionedpeopleservice.person.usecase;

import com.example.sanctionedpeopleservice.common.error.UseCaseError;
import com.example.sanctionedpeopleservice.common.error.UseCaseResult;
import com.example.sanctionedpeopleservice.person.model.SanctionedPerson;
import com.example.sanctionedpeopleservice.person.repository.SanctionedPersonRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.example.sanctionedpeopleservice.common.error.model.ErrorStatus.PERSON_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeleteSanctionedPersonUseCaseTest {
  private final SanctionedPersonRepository sanctionedPersonRepository = mock(SanctionedPersonRepository.class);
  private final DeleteSanctionedPersonUseCase deleteSanctionedPersonUseCase =
      new DeleteSanctionedPersonUseCase(sanctionedPersonRepository);

  private final String personName = "personName";

  @Test
  void deleteSanctionedPerson() {
    SanctionedPerson sanctionedPerson = SanctionedPerson.builder()
        .id(1L)
        .personName(personName)
        .build();

    when(sanctionedPersonRepository.findByPersonName(personName)).thenReturn(Optional.of(sanctionedPerson));

    UseCaseResult<SanctionedPerson> useCaseResult = deleteSanctionedPersonUseCase.deleteSanctionedPerson(personName);

    verify(sanctionedPersonRepository).deleteById(useCaseResult.getResult().getId());
    assertEquals(sanctionedPerson, useCaseResult.getResult());
  }

  @Test
  void deleteSanctionedPerson_NotFound() {
    Optional<UseCaseError> useCaseError = deleteSanctionedPersonUseCase.deleteSanctionedPerson(personName).getError();

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