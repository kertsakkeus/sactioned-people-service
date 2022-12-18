package com.example.sanctionedpeopleservice.sanctionedperson.usecase;

import com.example.sanctionedpeopleservice.common.error.UseCaseError;
import com.example.sanctionedpeopleservice.common.error.UseCaseResult;
import com.example.sanctionedpeopleservice.sanctionedperson.model.SanctionedPerson;
import com.example.sanctionedpeopleservice.sanctionedperson.repository.SanctionedPersonRepository;
import com.example.sanctionedpeopleservice.sanctionedperson.resource.SanctionedPersonSearchResult;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.example.sanctionedpeopleservice.common.error.model.ErrorStatus.PERSON_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FindSanctionedPersonUseCaseTest {
  private final SanctionedPersonRepository sanctionedPersonRepository = mock(SanctionedPersonRepository.class);
  private final FindSanctionedPersonUseCase findSanctionedPersonUseCase =
      new FindSanctionedPersonUseCase(sanctionedPersonRepository);

  private final String personName = "personName";

  @Test
  void getSanctionedPerson() {
    when(sanctionedPersonRepository.findByPersonName(personName)).thenReturn(Optional.of(SanctionedPerson.builder()
                                                                                             .id(1L)
                                                                                             .personName(personName)
                                                                                             .build()));

    UseCaseResult<SanctionedPersonSearchResult> useCaseResult = findSanctionedPersonUseCase.getSanctionedPerson(personName);

    assertSanctionedPerson(useCaseResult.getResult());
  }

  @Test
  void getSanctionedPerson_NotFound() {
    Optional<UseCaseError> useCaseError = findSanctionedPersonUseCase.getSanctionedPerson(personName).getError();

    assertTrue(useCaseError.isPresent());
    assertError(useCaseError.get(), "Person: ".concat(personName).concat(" does not exist in sanctioned people list!"));
  }

  @Test
  void getSanctionedPeople() {
    when(sanctionedPersonRepository.findAll()).thenReturn(List.of(SanctionedPerson.builder()
                                                                      .id(1L)
                                                                      .personName(personName)
                                                                      .build()));

    UseCaseResult<List<SanctionedPersonSearchResult>> useCaseResult = findSanctionedPersonUseCase.getSanctionedPeople();

    assertSanctionedPerson(useCaseResult.getResult().get(0));
  }

  private void assertSanctionedPerson(SanctionedPersonSearchResult searchResult) {
    assertEquals(1L, searchResult.sanctionedPerson().getId());
    assertEquals(personName, searchResult.sanctionedPerson().getPersonName());
  }

  private void assertError(UseCaseError useCaseError,
                           String expectedError) {
    assertEquals("Person does not exist", useCaseError.message());
    assertEquals(expectedError, useCaseError.errorDetails().get(0).getMessage());
    assertEquals(PERSON_NOT_FOUND, useCaseError.status());
  }
}