package com.betrybe.agrix.controllers;

import com.betrybe.agrix.dto.PersonDto;
import com.betrybe.agrix.dto.PersonDtoResponse;
import com.betrybe.agrix.ebytr.staff.entity.Person;
import com.betrybe.agrix.ebytr.staff.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Classe PersonController.
 */
@RestController
@RequestMapping("/persons")
public class PersonController {
  private final PersonService personService;

  @Autowired
  public PersonController(PersonService personService) {
    this.personService = personService;
  }

  /**
   * Rota responsável por criar uma pessoa.
   */
  @PostMapping
  public ResponseEntity<PersonDtoResponse> makePerson(@RequestBody PersonDto personDto) {
    Person newPerson = personDto.toPerson();
    Person person = personService.create(newPerson);
    PersonDtoResponse response = new PersonDtoResponse(
        person.getId(),
        person.getUsername(),
        person.getRole());
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
