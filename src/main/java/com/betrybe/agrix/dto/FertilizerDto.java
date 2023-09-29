package com.betrybe.agrix.dto;

import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Fertilizer;
import java.util.List;

/**
 * Record FertilizerDto.
 */
public record FertilizerDto(
    Integer id,
    String name,
    String brand,
    String composition,
    List<Crop> crops
) {
  public Fertilizer toFertilizer() {
    return new Fertilizer(id, name, brand, composition, crops);
  }
}
