package com.betrybe.agrix.controllers;

import com.betrybe.agrix.dto.FertilizerDto;
import com.betrybe.agrix.dto.FertilizerDtoResponse;
import com.betrybe.agrix.models.entities.Fertilizer;
import com.betrybe.agrix.services.FertilizerService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Classe FertilizerController.
 */
@RestController
@RequestMapping("/fertilizers")
public class FertilizerController {
  private FertilizerService fertilizerService;

  public FertilizerController(FertilizerService fertilizerService) {
    this.fertilizerService = fertilizerService;
  }

  /**
   * Rota responsável por criar um fertilizante.
   */
  @PostMapping
  public ResponseEntity<FertilizerDtoResponse> makeFertilizer(
      @RequestBody FertilizerDto fertilizerDto) {
    Fertilizer fertilizer = fertilizerDto.toFertilizer();
    Fertilizer newFertilizer = fertilizerService.makeFertilizer(fertilizer);
    FertilizerDtoResponse response = new FertilizerDtoResponse(
        newFertilizer.getId(), newFertilizer.getName(),
        newFertilizer.getBrand(), newFertilizer.getComposition());

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  /**
   * Rota responsável por exibir todos os fertilizantes.
   */
  @GetMapping
  public ResponseEntity<List<FertilizerDtoResponse>> getAllFertilizers() {
    List<Fertilizer> fertilizers = fertilizerService.getAllFertilizers();
    List<FertilizerDtoResponse> response = fertilizers.stream()
        .map(f -> new FertilizerDtoResponse(
            f.getId(), f.getName(),
            f.getBrand(), f.getComposition()))
        .collect(Collectors.toList());

    return ResponseEntity.ok(response);
  }

  /**
   * Rota GET /{fertilizerId} responsável por mostrar apenas um fertilizante específica.
   */
  @GetMapping("/{fertilizerId}")
  public ResponseEntity<FertilizerDtoResponse> getById(@PathVariable Integer fertilizerId) {
    Optional<Fertilizer> optionalFertilizer = fertilizerService.getById(fertilizerId);

    if (optionalFertilizer.isEmpty()) {
      FertilizerDtoResponse response = new FertilizerDtoResponse(
          null, "Fertilizante não encontrado!",
          null, null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    Fertilizer fertilizer = optionalFertilizer.get();
    FertilizerDtoResponse response = new FertilizerDtoResponse(
        fertilizer.getId(), fertilizer.getName(),
        fertilizer.getBrand(), fertilizer.getComposition());
    return ResponseEntity.ok(response);
  }
}
