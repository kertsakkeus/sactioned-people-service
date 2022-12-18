package com.example.sanctionedpeopleservice.person;

import com.example.sanctionedpeopleservice.common.error.UseCaseResult;
import com.example.sanctionedpeopleservice.person.model.SanctionedPerson;
import com.example.sanctionedpeopleservice.person.resource.SanctionedPeopleResource;
import com.example.sanctionedpeopleservice.person.resource.SanctionedPersonRequest;
import com.example.sanctionedpeopleservice.person.resource.SanctionedPersonResource;
import com.example.sanctionedpeopleservice.person.resource.SanctionedPersonSearchResult;
import com.example.sanctionedpeopleservice.person.resource.SanctionedPersonUpdateRequest;
import com.example.sanctionedpeopleservice.person.usecase.AddSanctionedPersonUseCase;
import com.example.sanctionedpeopleservice.person.usecase.DeleteSanctionedPersonUseCase;
import com.example.sanctionedpeopleservice.person.usecase.FindSanctionedPersonUseCase;
import com.example.sanctionedpeopleservice.person.usecase.UpdateSanctionedPersonUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SanctionedPersonControllerTest {
  private final AddSanctionedPersonUseCase addSanctionedPersonUseCase = mock(AddSanctionedPersonUseCase.class);
  private final UpdateSanctionedPersonUseCase updateSanctionedPersonUseCase = mock(UpdateSanctionedPersonUseCase.class);
  private final DeleteSanctionedPersonUseCase deleteSanctionedPersonUseCase = mock(DeleteSanctionedPersonUseCase.class);
  private final FindSanctionedPersonUseCase findSanctionedPersonUseCase = mock(FindSanctionedPersonUseCase.class);
  private final SanctionedPersonResourceFactory sanctionedPersonResourceFactory = mock(SanctionedPersonResourceFactory.class);

  private final SanctionedPersonController sanctionedPersonController =
      new SanctionedPersonController(addSanctionedPersonUseCase, updateSanctionedPersonUseCase,
                                     deleteSanctionedPersonUseCase, findSanctionedPersonUseCase,
                                     sanctionedPersonResourceFactory);

  private final String personName = "personName";

  @Test
  void findSanctionedPerson() {
    UseCaseResult<SanctionedPersonSearchResult> useCaseResult =
        new UseCaseResult<>(new SanctionedPersonSearchResult(null));

    SanctionedPersonResource sanctionedPersonResource = SanctionedPersonResource.builder()
        .personName(personName)
        .build();

    when(findSanctionedPersonUseCase.getSanctionedPerson(personName)).thenReturn(useCaseResult);
    when(sanctionedPersonResourceFactory.createSanctionedPersonResource(useCaseResult.getResult()))
        .thenReturn(sanctionedPersonResource);

    ResponseEntity<SanctionedPersonResource> responseEntity = sanctionedPersonController.getSanctionedPerson(personName);

    assertEquals(sanctionedPersonResource.getPersonName(), requireNonNull(responseEntity.getBody()).getPersonName());
  }

  @Test
  void findSanctionedPeople() {
    List<SanctionedPersonSearchResult> sanctionedPersonSearchResults = List.of(new SanctionedPersonSearchResult(null));

    SanctionedPersonResource sanctionedPersonResource = SanctionedPersonResource.builder()
        .personName(personName)
        .build();

    UseCaseResult<List<SanctionedPersonSearchResult>> useCaseResult =
        new UseCaseResult<>(sanctionedPersonSearchResults);


    when(findSanctionedPersonUseCase.getSanctionedPeople()).thenReturn(useCaseResult);
    when(sanctionedPersonResourceFactory.createSanctionedPersonResource(useCaseResult.getResult().get(0)))
        .thenReturn(sanctionedPersonResource);

    ResponseEntity<SanctionedPeopleResource> responseEntity = sanctionedPersonController.getSanctionedPeople();

    assertEquals(sanctionedPersonResource.getPersonName(),
                 requireNonNull(responseEntity.getBody()).getSanctionedPeople().get(0).getPersonName());

  }

  @Test
  void updateSanctionedPerson() {
    String nerPersonName = "newPersonName";

    SanctionedPersonUpdateRequest updateRequest = SanctionedPersonUpdateRequest.builder()
        .newPersonName(nerPersonName)
        .build();

    UseCaseResult<SanctionedPerson> useCaseResult = new UseCaseResult<>(SanctionedPerson.builder().build());

    when(updateSanctionedPersonUseCase.updateSanctionedPerson(personName, updateRequest)).thenReturn(useCaseResult);

    ResponseEntity<Object> responseEntity = sanctionedPersonController.updateSanctionedPerson(personName, updateRequest);

    verify(updateSanctionedPersonUseCase).updateSanctionedPerson(personName, updateRequest);
    assertEquals(responseEntity, ResponseEntity.noContent().build());
  }

  @Test
  void deleteSanctionedPerson() {
    UseCaseResult<SanctionedPerson> useCaseResult = new UseCaseResult<>(SanctionedPerson.builder().build());

    when(deleteSanctionedPersonUseCase.deleteSanctionedPerson(personName)).thenReturn(useCaseResult);

    ResponseEntity<Object> responseEntity = sanctionedPersonController.deleteSanctionedPerson(personName);

    verify(deleteSanctionedPersonUseCase).deleteSanctionedPerson(personName);
    assertEquals(responseEntity, ResponseEntity.noContent().build());
  }

  @Test
  void addSanctionedPerson() {
    SanctionedPersonRequest sanctionedPersonRequest = SanctionedPersonRequest.builder()
        .personName(personName)
        .build();

    UseCaseResult<SanctionedPerson> useCaseResult = new UseCaseResult<>(SanctionedPerson.builder()
                                                                            .personName(personName)
                                                                            .build());

    when(addSanctionedPersonUseCase.addSanctionedPerson(sanctionedPersonRequest)).thenReturn(useCaseResult);

    ResponseEntity<Object> responseEntity = sanctionedPersonController.addSanctionedPerson(sanctionedPersonRequest);

    assertNotNull(responseEntity.getHeaders().getLocation());
    assertEquals("/sanctions/" + personName, responseEntity.getHeaders().getLocation().toString());
  }
}