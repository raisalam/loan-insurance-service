package se.sbab.insurance.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * The type Insurance premium request.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsurancePremiumRequest {

  @NotNull
  @Min(18) @Max(64)
  private Integer age;

  @NotNull
  private Integer loanAmount;

  @NotNull
  @Min(8) @Max(15000)
  private Integer insurableAmount;
}
