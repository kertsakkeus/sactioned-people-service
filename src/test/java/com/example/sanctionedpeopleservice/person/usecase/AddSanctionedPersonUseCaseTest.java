package com.example.sanctionedpeopleservice.person.usecase;

import com.example.sanctionedpeopleservice.common.error.UseCaseError;
import com.example.sanctionedpeopleservice.person.model.SanctionedPerson;
import com.example.sanctionedpeopleservice.person.repository.SanctionedPersonRepository;
import com.example.sanctionedpeopleservice.person.resource.SanctionedPersonRequest;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.example.sanctionedpeopleservice.common.error.model.ErrorStatus.PERSON_EXIST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AddSanctionedPersonUseCaseTest {
  private final SanctionedPersonRepository sanctionedPersonRepository = mock(SanctionedPersonRepository.class);
  private final AddSanctionedPersonUseCase addSanctionedPersonUseCase = new AddSanctionedPersonUseCase(sanctionedPersonRepository);

  private final String personName = "personName";

  @Test
  void addSanctionedPerson() {
    when(sanctionedPersonRepository.findByPersonName(personName)).thenReturn(Optional.empty());

    SanctionedPersonRequest sanctionedPersonRequest = SanctionedPersonRequest.builder()
        .personName(personName)
        .build();

    addSanctionedPersonUseCase.addSanctionedPerson(sanctionedPersonRequest);

    verify(sanctionedPersonRepository).save(argThat(argument -> {
      assertEquals(personName, argument.getPersonName());
      return true;
    }));
  }

  @Test
  void addSanctionedPerson_AlreadyExist() {
    when(sanctionedPersonRepository.findByPersonName(personName)).thenReturn(Optional.of(SanctionedPerson.builder()
                                                                                             .personName(personName)
                                                                                             .build()));

    SanctionedPersonRequest sanctionedPersonRequest = SanctionedPersonRequest.builder()
        .personName(personName)
        .build();

    Optional<UseCaseError> useCaseError = addSanctionedPersonUseCase.addSanctionedPerson(sanctionedPersonRequest).getError();

    assertTrue(useCaseError.isPresent());
    assertError(useCaseError.get(), "Person: ".concat(personName).concat(" already exist in sanctioned people list!"));
  }

  private void assertError(UseCaseError useCaseError,
                           String expectedError) {
    assertEquals("Person exist", useCaseError.message());
    assertEquals(expectedError, useCaseError.errorDetails().get(0).getMessage());
    assertEquals(PERSON_EXIST, useCaseError.status());
  }
}