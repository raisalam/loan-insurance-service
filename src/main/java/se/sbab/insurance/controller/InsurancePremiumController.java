package se.sbab.insurance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.sbab.insurance.model.request.InsurancePremiumRequest;
import se.sbab.insurance.model.response.InsurancePremiumResponse;
import se.sbab.insurance.service.InsurancePremiumService;

import javax.validation.Valid;

/**
 * The type Insurance premium controller.
 */
@RestController
@RequestMapping(value = "v1/insurance")
@Validated
@Tag(name = "Insurance Premium Controller",
    description = "A REST-Controller for calculating insurance offer")
@RequiredArgsConstructor
public class InsurancePremiumController {
  private final InsurancePremiumService insurancePremiumService;

  /**
   * Calculate insurance premium insurance premium response.
   *
   * @param insurancePremiumRequest the insurance premium request
   * @return the insurance premium response
   */
  @PostMapping(value = "/values", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Calculate insurance offer")
  public InsurancePremiumResponse calculateInsurancePremium(
      @Valid @RequestBody InsurancePremiumRequest insurancePremiumRequest) {
    return insurancePremiumService.calculateInsurancePremium(insurancePremiumRequest);

  }
}
