package se.sbab.insurance.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.sbab.insurance.dao.InsuranceDao;
import se.sbab.insurance.model.InsuranceTariff;
import se.sbab.insurance.model.request.InsurancePremiumRequest;
import se.sbab.insurance.model.response.InsurancePremiumResponse;

import java.util.*;

/**
 * The type Insurance premium service.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class InsurancePremiumService {
  private static final String LIFE_INSURANCE_TYPE = "LIV";
  private static final String INCOME_INSURANCE_TYPE = "TRYGG";

  private final InsuranceDao insuranceDao;



  /**
   * Calculate insurance premium insurance premium response.
   *
   * @param request the request
   * @return the insurance premium response
   */
  public InsurancePremiumResponse calculateInsurancePremium(final InsurancePremiumRequest request) {
    List<InsuranceTariff> lifeInsuranceValues =
        getInsuranceValues(request.getAge(), request.getLoanAmount(), LIFE_INSURANCE_TYPE);
    List<InsuranceTariff> incomeInsuranceValues =
        getInsuranceValues(request.getAge(), request.getInsurableAmount(), INCOME_INSURANCE_TYPE);

    double monthlyIncomeInsurancePremium =
        getMonthlyInsurancePremium(request.getInsurableAmount(), incomeInsuranceValues,
            INCOME_INSURANCE_TYPE);
    double monthlyLifeInsurancePremium =
        getMonthlyInsurancePremium(request.getLoanAmount(), lifeInsuranceValues,
            LIFE_INSURANCE_TYPE);

    double totalMonthlyPremium = monthlyIncomeInsurancePremium + monthlyLifeInsurancePremium;
    String monthlyLifeInsurancePremium1 = String.format("%.2f", monthlyLifeInsurancePremium);
    String monthlyIncomeInsurancePremium1 = String.format("%.2f", monthlyIncomeInsurancePremium);
    String totalMonthlyPremium1 = String.format("%.2f", totalMonthlyPremium);

    return InsurancePremiumResponse.builder()
        .monthlyLifeInsurancePremium(Double.valueOf(monthlyLifeInsurancePremium1))
        .monthlyIncomeInsurancePremium(Double.valueOf(monthlyIncomeInsurancePremium1))
        .totalMonthlyPremium(Double.valueOf(totalMonthlyPremium1)).build();

  }

  private List<InsuranceTariff> getInsuranceValues(final Integer age, final Integer amount,
      final String insuranceType) {

    if (amount == 0) {
      return Collections.emptyList();
    }
    log.info("No such tariff is found for age {} and insuranceType {} ", age, insuranceType);
    List<InsuranceTariff> tariffs = insuranceDao.getInsuranceValues(age, insuranceType);

    if (tariffs.isEmpty()) {
      throw new NoSuchElementException(
          String.format("No such tariff is found for age {} and insuranceType {} ", age,
              insuranceType));
    }
    return tariffs;
  }

  private double getMonthlyInsurancePremium(final Integer insurableAmount,
      final List<InsuranceTariff> insuranceValues, final String incomeInsuranceType) {

    if (insuranceValues.isEmpty()) {
      return 0d;
    }
    return insuranceValues.stream().filter(i -> i.getType().equals(incomeInsuranceType))
        .map(InsuranceTariff::getTariff).findFirst().orElseThrow(NoSuchElementException::new)
        + insurableAmount;
  }

}

