package se.sbab.insurance.service;

import org.springframework.http.ResponseEntity;
import se.sbab.insurance.model.request.KreApplicationRequest;
import se.sbab.insurance.model.response.KrePart;

import java.util.List;

public interface KreApplicationService {
     ResponseEntity<List<KrePart>> findParts(String token, KreApplicationRequest kreApplicationRequest);
}
