package se.sbab.insurance.service;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import se.sbab.insurance.client.MSRestClient;
import se.sbab.insurance.model.request.KreApplicationRequest;
import se.sbab.insurance.model.response.KrePart;

import java.util.ArrayList;
import java.util.List;

import static se.sbab.insurance.constant.ApplicationConstant.*;

@Service
@RequiredArgsConstructor
public class KreApplicationServiceImpl implements KreApplicationService {
    private final MSRestClient msRestClient;

    @Override
    public ResponseEntity<List<KrePart>> findParts(String token, KreApplicationRequest kreApplicationRequest){
        List<Long> kreIds = kreApplicationRequest.getKreIds();
        List<KrePart> responses = new ArrayList<>();

        for (Long kreId : kreIds) {
            var findLoanPartsUri = UriComponentsBuilder
                    .fromUriString(BASE_URL + GET_KRE_APPLICATION_BY_ID)
                    .buildAndExpand(kreId)
                    .toUriString();

            // addInsurancePremium();

            var kreParts = msRestClient.get(findLoanPartsUri, token, new TypeReference<KrePart>() {
            });
            responses.add(kreParts);
        }


        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responses);

    }
}
