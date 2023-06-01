package se.sbab.insurance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import se.sbab.insurance.model.request.InsurancePremiumRequest;

/**
 * The type Insurance premium controller integration test.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class InsurancePremiumControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;


  @Autowired
  private ObjectMapper objectMapper;

  /**
   * Test calculate insurance premium.
   *
   * @throws Exception the exception
   */
  @Test
  void testCalculateInsurancePremium() throws Exception {
    // Prepare the request body
    InsurancePremiumRequest request = new InsurancePremiumRequest();
    request.setAge(30);
    request.setInsurableAmount(5000);
    request.setLoanAmount(5000);

    // Prepare the request
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/v1/insurance/values")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request));

    // Perform the request and validate the response
    mockMvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$.monthlyLifeInsurancePremium").value(5100.0))
        .andExpect(MockMvcResultMatchers.jsonPath("$.monthlyIncomeInsurancePremium").value(5100.0))
        .andExpect(MockMvcResultMatchers.jsonPath("$.totalMonthlyPremium").value(10200.0))
        .andReturn().getResponse().getContentAsString();
  }
}

