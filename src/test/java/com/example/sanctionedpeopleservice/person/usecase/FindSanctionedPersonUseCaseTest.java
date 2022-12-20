package com.example.sanctionedpeopleservice.person.usecase;

import com.example.sanctionedpeopleservice.common.error.UseCaseError;
import com.example.sanctionedpeopleservice.common.error.UseCaseResult;
import com.example.sanctionedpeopleservice.person.model.SanctionedPerson;
import com.example.sanctionedpeopleservice.person.repository.SanctionedPersonRepository;
import com.example.sanctionedpeopleservice.person.resource.SanctionedPersonSearchResult;
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

  private final Long personId = 1L;
  private final String personName = "personName";

  @Test
  void getSanctionedPerson() {
    when(sanctionedPersonRepository.findById(personId)).thenReturn(Optional.of(SanctionedPerson.builder()
                                                                                             .id(1L)
                                                                                             .personName(personName)
                                                                                             .build()));

    UseCaseResult<SanctionedPersonSearchResult> useCaseResult = findSanctionedPersonUseCase.getSanctionedPerson(personId);

    assertSanctionedPerson(useCaseResult.getResult());
  }

  @Test
  void getSanctionedPerson_NotFound() {
    Optional<UseCaseError> useCaseError = findSanctionedPersonUseCase.getSanctionedPerson(personId).getError();

    assertTrue(useCaseError.isPresent());
    assertError(useCaseError.get(), "Person with id ".concat(personId.toString())
        .concat(" does not exist in sanctioned people list!"));
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