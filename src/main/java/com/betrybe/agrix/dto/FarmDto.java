package com.betrybe.agrix.dto;

import com.betrybe.agrix.models.entities.Farm;

/**
 * Record FarmDto.
 */
public record FarmDto(Integer id, String name, Double size) {
  public Farm makeFarm() {
    return new Farm(id, name, size);
  }
}
