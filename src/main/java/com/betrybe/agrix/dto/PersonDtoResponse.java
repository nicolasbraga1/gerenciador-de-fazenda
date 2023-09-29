package com.betrybe.agrix.dto;

import com.betrybe.agrix.ebytr.staff.security.Role;

/**
 * Record PersonDtoResponse.
 */
public record PersonDtoResponse(Long id, String username, Role role) {

}
