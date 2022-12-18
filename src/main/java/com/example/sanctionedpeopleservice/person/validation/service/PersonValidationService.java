package com.example.sanctionedpeopleservice.person.validation.service;

import com.example.sanctionedpeopleservice.person.repository.SanctionedPersonRepository;
import com.example.sanctionedpeopleservice.person.validation.resource.Condition;
import com.example.sanctionedpeopleservice.person.validation.resource.PersonValidationResult;
import com.example.sanctionedpeopleservice.person.validation.resource.SanctionedPersonCondition;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Locale.ROOT;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Service
public class PersonValidationService {
  private final SanctionedPersonRepository sanctionedPersonRepository;

  public PersonValidationService(SanctionedPersonRepository sanctionedPersonRepository) {
    this.sanctionedPersonRepository = sanctionedPersonRepository;
  }

  public List<PersonValidationResult> validatePerson(String personName) {
    return getSanctionedPeopleConditions().stream()
        .map(sanctionedPersonCondition -> PersonValidationResult.builder()
            .personName(sanctionedPersonCondition.getPersonName())
            .percentage(getPercentageOfSimilarityFromSanctionedPeople(sanctionedPersonCondition.getConditions(), personName))
            .build())
        .toList();
  }

  private List<SanctionedPersonCondition> getSanctionedPeopleConditions() {
    return sanctionedPersonRepository.findAll().stream()
        .map(sanctionedPerson -> SanctionedPersonCondition.builder()
            .personName(sanctionedPerson.getPersonName())
            .conditions(createConditionsByPersonName(sanctionedPerson.getPersonName()))
            .build())
        .toList();
  }

  private Float getPercentageOfSimilarityFromSanctionedPeople(List<Condition> listOfConditions, String personName) {
    List<Boolean> matchingConditions = new ArrayList<>();

    Map<Character, Long> charactersBySum = createListOfCharacters(personName).stream()
        .collect(groupingBy(character -> character, counting()));

    listOfConditions.forEach(condition -> charactersBySum.forEach((key, value) -> {
      if (condition.getCharacter().equals(key) && condition.getSum() <= value) {
        matchingConditions.add(true);
      }
    }));

    return ((float) matchingConditions.size() / listOfConditions.size()) * 100f;
  }

  private List<Condition> createConditionsByPersonName(String personName) {
    return createListOfCharacters(personName).stream()
        .collect(groupingBy(character -> character, counting())).entrySet().stream()
        .map(entry -> Condition.builder()
            .character(entry.getKey())
            .sum(entry.getValue())
            .build())
        .toList();
  }

  private List<Character> createListOfCharacters(String personName) {
    return personName.toLowerCase(ROOT)
        .replaceAll("\\s+", "")
        .chars()
        .mapToObj(e -> (char) e)
        .toList();
  }
}
