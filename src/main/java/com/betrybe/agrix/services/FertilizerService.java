package com.betrybe.agrix.services;

import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Fertilizer;
import com.betrybe.agrix.models.repositories.RepoFertilizer;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Classe FertilizerService.
 */
@Service
public class FertilizerService {
  private final RepoFertilizer repoFertilizer;
  private final CropService cropService;

  @Autowired
  public FertilizerService(RepoFertilizer repoFertilizer, CropService cropService) {
    this.repoFertilizer = repoFertilizer;
    this.cropService = cropService;
  }

  public Fertilizer makeFertilizer(Fertilizer fertilizer) {
    return repoFertilizer.save(fertilizer);
  }

  public List<Fertilizer> getAllFertilizers() {
    return repoFertilizer.findAll();
  }

  public Optional<Fertilizer> getById(Integer id) {
    return repoFertilizer.findById(id);
  }

  /**
   * Método para colocar um fertilizante na plantação.
   */
  public Optional<Fertilizer> makeCrop(Integer cropId, Integer fertilizerId) {
    Crop crop = cropService.getById(cropId).get();
    Fertilizer fertilizer = this.getById(fertilizerId).get();
    crop.getFertilizers().add(fertilizer);
    return Optional.of(repoFertilizer.save(fertilizer));
  }
}
