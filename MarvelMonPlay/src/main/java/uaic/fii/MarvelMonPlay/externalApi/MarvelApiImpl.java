package uaic.fii.MarvelMonPlay.externalApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.models.Marvel;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class MarvelApiImpl implements MarvelApi {
    public static final String MARVEL_API_CHARACTERS_ENDPOINT = "https://gateway.marvel.com/v1/public/characters";

    @Override
    public Marvel getMarvelCharacter(String characterNameStartsWith) throws ResourceNotFoundException {
        final String NOT_FOUND_EXC_MSG = "Marvel character's name starting with: " + characterNameStartsWith + " has not been found";

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String finalURL;
        try {
            finalURL = UriComponentsBuilder.fromHttpUrl(getMarvelCharactersEndpointWithAuthentication())
                    .queryParam("nameStartsWith", characterNameStartsWith)
                    .build()
                    //.encode() encoded spaces are not taken into consideration by the marvel api and produces unexpected results
                    .toUriString();
        } catch (NoSuchAlgorithmException e) {
            throw new ResourceNotFoundException(NOT_FOUND_EXC_MSG);
        }

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(finalURL, HttpMethod.GET, entity, String.class);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            int totalResults = jsonNode.get("data").get("total").asInt();
            if(totalResults == 0){
                throw new ResourceNotFoundException(NOT_FOUND_EXC_MSG);
            }
            JsonNode results = jsonNode.get("data").get("results");
            String name = results.get(0).get("name").asText();
            String description = results.get(0).get("description").asText();
            JsonNode thumbnailNode = results.get(0).get("thumbnail");
            String imageUrl = thumbnailNode.get("path").asText() + "." + thumbnailNode.get("extension").asText();
            return new Marvel(name, imageUrl, description);
        } catch (JsonProcessingException e) {
            throw new ResourceNotFoundException(NOT_FOUND_EXC_MSG);
        }
    }

    private String getMarvelCharactersEndpointWithAuthentication() throws NoSuchAlgorithmException {
        long timestamp = System.currentTimeMillis();
        String ts = Long.toString(timestamp);
        String privateKey = System.getenv("MARVEL_PRIVATE_APIKEY");
        String publicKey = System.getenv("MARVEL_PUBLIC_APIKEY");
        String data = ts + privateKey + publicKey;
        MessageDigest md = MessageDigest.getInstance("md5");
        byte[] hash = md.digest(data.getBytes());
        String hashString = DatatypeConverter.printHexBinary(hash).toLowerCase();

        return UriComponentsBuilder.fromHttpUrl(MARVEL_API_CHARACTERS_ENDPOINT)
                .queryParam("ts", ts)
                .queryParam("apikey", publicKey)
                .queryParam("hash", hashString)
                .build()
                .encode()
                .toUriString();
    }
}