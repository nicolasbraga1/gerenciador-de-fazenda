package com.betrybe.agrix.dto;

import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Farm;
import com.betrybe.agrix.models.entities.Fertilizer;
import java.time.LocalDate;
import java.util.List;

/**
 * Record CropsDto.
 */
public record CropsDto(
    Integer id,
    String name,
    Double plantedArea,
    Farm farm,
    LocalDate plantedDate,
    LocalDate harvestDate,
    List<Fertilizer> fertilizers
) {
  public Crop makeCrop() {
    return new Crop(id, name, plantedArea, farm, plantedDate, harvestDate, fertilizers);
  }
}
