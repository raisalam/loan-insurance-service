package se.sbab.insurance.dao;

import org.springframework.stereotype.Repository;
import se.sbab.insurance.model.InsuranceTariff;

import java.util.List;

/**
 * The type Insurance dao.
 */
@Repository
public class InsuranceDao {
  /**
   * Gets insurance values.
   *
   * @param age the age
   * @param insuranceType the insurance type
   * @return the insurance values
   */
  public List<InsuranceTariff> getInsuranceValues(final Integer age, final String insuranceType) {

    return List.of(InsuranceTariff.builder().tariff(100d).type(insuranceType).build(),
        InsuranceTariff.builder().tariff(200d).type(insuranceType).build());

  }
}
