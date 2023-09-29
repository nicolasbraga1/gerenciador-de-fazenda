package com.betrybe.agrix.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;

/**
 * Classe Farm.
 */
@Entity
@Table(name = "farm")
public class Farm {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private Double size;

  @OneToMany(mappedBy = "farm")
  @JsonIgnore
  private List<Crop> crops;

  public Farm() {}

  /**
   * Construtor da classe.
   */
  public Farm(Integer id, String name, Double size) {
    this.id = id;
    this.name = name;
    this.size = size;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getSize() {
    return size;
  }

  public void setSize(Double size) {
    this.size = size;
  }
}
