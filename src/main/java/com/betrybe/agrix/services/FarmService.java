package com.betrybe.agrix.services;

import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Farm;
import com.betrybe.agrix.models.repositories.RepoCrop;
import com.betrybe.agrix.models.repositories.RepoFarm;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Classe FarmService.
 */
@Service
public class FarmService {
  private final RepoFarm repoFarm;
  private final RepoCrop repoCrop;

  @Autowired
  public FarmService(RepoFarm repoFarm, RepoCrop repoCrop) {
    this.repoFarm = repoFarm;
    this.repoCrop = repoCrop;
  }

  public Farm createFarm(Farm farm) {
    return repoFarm.save(farm);
  }

  public List<Farm> getAllFarms() {
    return repoFarm.findAll();
  }

  public Optional<Farm> getById(Integer id) {
    return repoFarm.findById(id);
  }

  public Crop createCrop(Crop crop) {
    return repoCrop.save(crop);
  }

  public List<Crop> getAllCrops() {
    return repoCrop.findAll();
  }
}
