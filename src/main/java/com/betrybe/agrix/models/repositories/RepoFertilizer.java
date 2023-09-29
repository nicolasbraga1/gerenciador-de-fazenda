package com.betrybe.agrix.models.repositories;

import com.betrybe.agrix.models.entities.Fertilizer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface RepoFertilizer.
 */
public interface RepoFertilizer extends JpaRepository<Fertilizer, Integer> {

}
