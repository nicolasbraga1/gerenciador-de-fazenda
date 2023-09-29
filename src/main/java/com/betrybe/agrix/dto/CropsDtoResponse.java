package com.betrybe.agrix.dto;

import java.time.LocalDate;

/**
 * Record CropsDtoResponse.
 */
public record CropsDtoResponse(
    Integer id,
    String name,
    Double plantedArea,
    LocalDate plantedDate,
    LocalDate harvestDate,
    Integer farmId
) {

}
