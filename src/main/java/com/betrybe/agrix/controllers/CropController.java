package com.betrybe.agrix.controllers;

import com.betrybe.agrix.dto.CropsDtoResponse;
import com.betrybe.agrix.dto.FertilizerDtoResponse;
import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Fertilizer;
import com.betrybe.agrix.services.CropService;
import com.betrybe.agrix.services.FertilizerService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Classe CropController.
 */
@RestController
@RequestMapping("/crops")
public class  CropController {
  private final CropService cropService;
  private final FertilizerService fertilizerService;

  @Autowired
  public CropController(CropService cropService, FertilizerService fertilizerService) {
    this.cropService = cropService;
    this.fertilizerService = fertilizerService;
  }

  /**
   * Rota GET crops responsável por mostrar todas as plantações.
   */
  @GetMapping
  public ResponseEntity<List<CropsDtoResponse>> getAllCrops() {
    List<Crop> crops = cropService.getAllCrops();
    List<CropsDtoResponse> response = crops.stream()
        .map(c -> new CropsDtoResponse(
            c.getId(), c.getName(), c.getPlantedArea(),
            c.getPlantedDate(), c.getHarvestDate(), c.getFarm().getId()))
        .collect(Collectors.toList());
    return ResponseEntity.ok(response);
  }

  /**
   * Rota GET /{id} responsável por mostrar apenas uma plantação específica.
   */
  @GetMapping("/{id}")
  public ResponseEntity<CropsDtoResponse> getById(@PathVariable Integer id) {
    Optional<Crop> optionalCrop = cropService.getById(id);

    if (optionalCrop.isEmpty()) {
      CropsDtoResponse response = new CropsDtoResponse(
          null, "Plantação não encontrada!",
          null, null, null, null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    Crop crop = optionalCrop.get();
    CropsDtoResponse response = new CropsDtoResponse(crop.getId(), crop.getName(),
        crop.getPlantedArea(), crop.getPlantedDate(),
        crop.getHarvestDate(), crop.getFarm().getId());
    return ResponseEntity.ok(response);
  }

  /**
   * Rota GET /search responsável por encontrar plantações conforme a data.
   */
  @GetMapping("search")
  public ResponseEntity<List<CropsDtoResponse>> getByDate(@RequestParam LocalDate start,
      @RequestParam LocalDate end) {
    List<Crop> crops = cropService.getByDate(start, end);
    List<CropsDtoResponse> response = crops.stream()
        .map(c -> new CropsDtoResponse(
            c.getId(), c.getName(), c.getPlantedArea(),
            c.getPlantedDate(), c.getHarvestDate(), c.getFarm().getId()))
        .collect(Collectors.toList());
    return ResponseEntity.ok(response);
  }

  /**
   * Rota POST /{cropId}/fertilizers responsável por associar um fertilizante a uma planyação.
   */
  @PostMapping("/{cropId}/fertilizers/{fertilizerId}")
  public ResponseEntity<String> fertilizerToCrop(
      @PathVariable Integer cropId,
      @PathVariable Integer fertilizerId
  ) {
    Optional<Crop> optionalCrop = cropService.getById(cropId);
    Optional<Fertilizer> optionalFertilizer = fertilizerService.getById(fertilizerId);

    if (optionalCrop.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plantação não encontrada!");
    }

    if (optionalFertilizer.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fertilizante não encontrado!");
    }

    fertilizerService.makeCrop(cropId, fertilizerId);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body("Fertilizante e plantação associados com sucesso!");
  }

  /**
   * Rota GET /{cropId}/fertilizers responsável por exibir os fertilizantes de uma plantação.
   */
  @GetMapping("/{cropId}/fertilizers")
  public ResponseEntity<List<FertilizerDtoResponse>> getAllCropFertilizers(
      @PathVariable Integer cropId
  ) {
    Optional<Crop> optionalCrop = cropService.getById(cropId);
    if (optionalCrop.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of(new FertilizerDtoResponse(
          null, "Plantação não encontrada!",
          null, null)));
    }
    List<Fertilizer> fertilizers = optionalCrop.get().getFertilizers();
    List<FertilizerDtoResponse> response = fertilizers.stream()
        .map(f -> new FertilizerDtoResponse(
            f.getId(), f.getName(),
            f.getBrand(), f.getComposition()))
        .collect(Collectors.toList());

    return ResponseEntity.ok(response);
  }
}
