package com.betrybe.agrix.services;

import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.repositories.RepoCrop;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Classe CropService.
 */
@Service
public class CropService {
  private final RepoCrop repoCrop;

  @Autowired
  public CropService(RepoCrop repoCrop) {
    this.repoCrop = repoCrop;
  }

  public List<Crop> getAllCrops() {
    return repoCrop.findAll();
  }

  public Optional<Crop> getById(Integer id) {
    return repoCrop.findById(id);
  }

  public List<Crop> getByDate(LocalDate start, LocalDate end) {
    return repoCrop.findByDate(start, end);
  }

  public Crop createCrop(Crop crop) {
    return repoCrop.save(crop);
  }
}
