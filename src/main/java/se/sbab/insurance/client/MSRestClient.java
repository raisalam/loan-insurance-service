package se.sbab.insurance.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class MSRestClient {

    private final ObjectMapper objectMapper;

    public  <T> T get(String url, String jwtToken, TypeReference<T> type) {
        // Perform the HTTP GET request and retrieve the response
        String jsonResponse = executeGet(url, jwtToken);

        // Convert the JSON response to the desired type
        T result = null;
        try {
            result = objectMapper.readValue(jsonResponse, type);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private  String executeGet(String url, String jwtToken) {
        System.out.println("Going to call real api with url "+url);
        // TODO
        // Perform realtime api call here
        // Below I am mocking and expecting result and returning the same

        int randomValue = generateRandomNumber();
        String randomName = generateRandomName();
        return "{\"value\": " + randomValue + ", \"name\":\"" + randomName + "\"}";

    }

    private static final String[] NAMES = {
            "Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Henry", "Ivy", "Jack", "Kate", "Leo", "Mia", "Nathan", "Olivia", "Peter", "Quinn", "Ruby", "Sam", "Tina"
    };

    public static int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(90000) + 10000; // Generates a random number between 10000 and 99999 (5-digit range)
    }

    public static String generateRandomName() {
        Random random = new Random();
        int randomIndex = random.nextInt(NAMES.length);
        return NAMES[randomIndex];
    }
}
