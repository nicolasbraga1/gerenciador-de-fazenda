package com.betrybe.agrix.dto;

import com.betrybe.agrix.ebytr.staff.entity.Person;
import com.betrybe.agrix.ebytr.staff.security.Role;

/**
 * Record PersonDto.
 */
public record PersonDto(Long id, String username, String password, Role role) {
  public Person toPerson() {
    return new Person(id, username, password, role);
  }
}
