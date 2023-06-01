package se.sbab.insurance.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import se.sbab.insurance.dao.InsuranceDao;
import se.sbab.insurance.model.InsuranceTariff;
import se.sbab.insurance.model.request.InsurancePremiumRequest;
import se.sbab.insurance.model.response.InsurancePremiumResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * The type Insurance premium service test.
 */
class InsurancePremiumServiceTest {

  private static final String LIFE_INSURANCE_TYPE = "LIV";
  private static final String INCOME_INSURANCE_TYPE = "TRYGG";
  private InsurancePremiumService insurancePremiumService;

  @Mock
  private InsuranceDao insuranceDao;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    insurancePremiumService = new InsurancePremiumService(insuranceDao);
  }

  /**
   * Test calculate insurance premium.
   */
  @Test
  void testCalculateInsurancePremium() {
    // Prepare test data
    InsurancePremiumRequest request = new InsurancePremiumRequest();
    request.setAge(30);
    request.setLoanAmount(100000);
    request.setInsurableAmount(5000);

    List<InsuranceTariff> lifeInsuranceValues = new ArrayList<>();
    lifeInsuranceValues.add(new InsuranceTariff(LIFE_INSURANCE_TYPE, 0.001));
    when(insuranceDao.getInsuranceValues(eq(30), eq(LIFE_INSURANCE_TYPE))).thenReturn(
        lifeInsuranceValues);

    List<InsuranceTariff> incomeInsuranceValues = new ArrayList<>();
    incomeInsuranceValues.add(new InsuranceTariff(INCOME_INSURANCE_TYPE, 0.001));
    when(insuranceDao.getInsuranceValues(eq(30), eq(INCOME_INSURANCE_TYPE))).thenReturn(
        incomeInsuranceValues);

    // Perform the calculation
    InsurancePremiumResponse response = insurancePremiumService.calculateInsurancePremium(request);

    // Verify the results
    Assertions.assertEquals(100000, response.getMonthlyLifeInsurancePremium(), 0.001);
    Assertions.assertEquals(5000.0, response.getMonthlyIncomeInsurancePremium());
    Assertions.assertEquals(105000.0, response.getTotalMonthlyPremium(), 0.001);

    // Verify that the insuranceDao.getInsuranceValues() method was called
    verify(insuranceDao, times(1)).getInsuranceValues(eq(30), eq(LIFE_INSURANCE_TYPE));
    verify(insuranceDao, times(1)).getInsuranceValues(eq(30), eq(INCOME_INSURANCE_TYPE));
  }

  /**
   * Test calculate insurance premium no tariffs found.
   */
  @Test
  void testCalculateInsurancePremium_NoTariffsFound() {
    // Prepare test data
    InsurancePremiumRequest request = new InsurancePremiumRequest();
    request.setAge(30);
    request.setLoanAmount(100000);
    request.setInsurableAmount(5000);

    when(insuranceDao.getInsuranceValues(anyInt(), anyString())).thenReturn(new ArrayList<>());

    // Perform the calculation and verify that NoSuchElementException is thrown
    Assertions.assertThrows(NoSuchElementException.class,
        () -> insurancePremiumService.calculateInsurancePremium(request));

    // Verify that the insuranceDao.getInsuranceValues() method was called
    verify(insuranceDao, times(1)).getInsuranceValues(anyInt(), anyString());
  }
}

