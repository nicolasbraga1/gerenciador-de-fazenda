package com.betrybe.agrix.controllers;

import com.betrybe.agrix.dto.CropsDto;
import com.betrybe.agrix.dto.CropsDtoResponse;
import com.betrybe.agrix.dto.FarmDto;
import com.betrybe.agrix.dto.FarmDtoResponse;
import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Farm;
import com.betrybe.agrix.services.FarmService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Classe FarmController.
 */
@RestController
@RequestMapping("/farms")
public class FarmController {
  private final FarmService farmService;

  @Autowired
  public FarmController(FarmService farmService) {
    this.farmService = farmService;
  }

  /**
   * Rota POST farms responsável por criar uma fazenda.
   */
  @PostMapping
  public ResponseEntity<FarmDtoResponse> createFarm(@RequestBody FarmDto farmDto) {
    Farm newFarm = farmService.createFarm(farmDto.makeFarm());
    FarmDtoResponse response = new FarmDtoResponse(newFarm.getId(), newFarm.getName(),
        newFarm.getSize());
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  /**
   * Rota GET Farms responsável por mostrar todas as fazendas.
   */
  @GetMapping
  public ResponseEntity<List<FarmDtoResponse>> getAllFarms() {
    List<Farm> farms = farmService.getAllFarms();
    List<FarmDtoResponse> response =
        farms.stream().map(f -> new FarmDtoResponse(f.getId(),
        f.getName(), f.getSize())).collect(Collectors.toList());
    return ResponseEntity.ok(response);
  }

  /**
   * Rota GET farmId responsável por mostrar apenas uma fazenda usando id.
   */
  @GetMapping("/{farmId}")
  public ResponseEntity<FarmDtoResponse> getById(@PathVariable Integer farmId) {
    Optional<Farm> optionalFarm = farmService.getById(farmId);
    if (optionalFarm.isEmpty()) {
      FarmDtoResponse response = new FarmDtoResponse(null, "Fazenda não encontrada!", null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    Farm farm = optionalFarm.get();
    FarmDtoResponse response = new FarmDtoResponse(farm.getId(), farm.getName(), farm.getSize());
    return ResponseEntity.ok(response);
  }

  /**
   * Rota POST /{farmId}/crops responsável por criar uma plantação na fazenda selecionada via id.
   */
  @PostMapping("/{farmId}/crops")
  public ResponseEntity<CropsDtoResponse> createCrop(@PathVariable Integer farmId, @RequestBody
      CropsDto cropsDto) {
    Optional<Farm> optionalFarm = farmService.getById(farmId);

    if (optionalFarm.isEmpty()) {
      CropsDtoResponse response = new CropsDtoResponse(null, "Fazenda não encontrada!",
          null, null, null, null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    Crop crop = cropsDto.makeCrop();
    crop.setFarm(optionalFarm.get());
    Crop createdCrop = farmService.createCrop(crop);
    CropsDtoResponse response = new CropsDtoResponse(createdCrop.getId(),
        createdCrop.getName(), createdCrop.getPlantedArea(),
        createdCrop.getPlantedDate(), createdCrop.getHarvestDate(), createdCrop.getFarm().getId());

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  /**
   * Rota GET /{farmId}/crops responsável por mostrar todas as plantações de uma fazenda.
   */
  @GetMapping("/{farmId}/crops")
  public ResponseEntity<List<CropsDtoResponse>> getFarmCropsById(@PathVariable Integer farmId) {
    Optional<Farm> optionalFarm = farmService.getById(farmId);

    if (optionalFarm.isEmpty()) {
      CropsDtoResponse response = new CropsDtoResponse(null, "Fazenda não encontrada!",
          null, null, null, null);
      List<CropsDtoResponse> responseList = List.of(response);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseList);
    }

    List<CropsDtoResponse> cropsResponse = farmService
        .getAllCrops().stream().filter(c -> c.getFarm().getId() == farmId)
        .map(c -> new CropsDtoResponse(c.getId(), c.getName(),
            c.getPlantedArea(), c.getPlantedDate(),
            c.getHarvestDate(), c.getFarm().getId()))
        .collect(Collectors.toList());

    return ResponseEntity.ok(cropsResponse);
  }
}
