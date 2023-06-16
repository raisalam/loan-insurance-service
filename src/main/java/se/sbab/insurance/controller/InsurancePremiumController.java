package se.sbab.insurance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import se.sbab.insurance.model.request.KreApplicationRequest;
import se.sbab.insurance.model.response.KrePart;
import se.sbab.insurance.model.request.InsurancePremiumRequest;
import se.sbab.insurance.model.response.InsurancePremiumResponse;
import se.sbab.insurance.service.InsurancePremiumService;
import se.sbab.insurance.service.KreApplicationService;
import se.sbab.insurance.service.KreApplicationServiceImpl;

import javax.validation.Valid;
import java.util.List;

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
  private final KreApplicationService kreApplicationService;

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

  @GetMapping(value = "/kreparts", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Get kre parts")
  public ResponseEntity<List<KrePart>> getKreParts(@Valid @RequestBody KreApplicationRequest request) {
    return kreApplicationService.findParts("token", request);

  }
}
