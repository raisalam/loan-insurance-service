package se.sbab.insurance.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Insurance premium response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsurancePremiumResponse {
  private double monthlyLifeInsurancePremium;
  private double monthlyIncomeInsurancePremium;
  private double totalMonthlyPremium;

}
