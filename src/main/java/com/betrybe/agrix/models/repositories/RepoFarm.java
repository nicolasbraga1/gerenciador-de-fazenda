package com.betrybe.agrix.models.repositories;

import com.betrybe.agrix.models.entities.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface RepoFarm.
 */
public interface RepoFarm extends JpaRepository<Farm, Integer> {

}
