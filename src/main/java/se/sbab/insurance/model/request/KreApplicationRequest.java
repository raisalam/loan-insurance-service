package se.sbab.insurance.model.request;

import lombok.Data;

import java.util.List;

@Data
public class KreApplicationRequest {

    private List<Long> kreIds;
}
