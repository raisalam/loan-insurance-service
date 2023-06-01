package se.sbab.insurance.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Insurance tariff.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceTariff {
  private String type;
  private Double tariff;
}
