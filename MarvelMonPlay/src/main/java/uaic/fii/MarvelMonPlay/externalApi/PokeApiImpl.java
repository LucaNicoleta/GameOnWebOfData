package uaic.fii.MarvelMonPlay.externalApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.models.abilities.Ability;
import uaic.fii.MarvelMonPlay.models.characters.Pokemon;

import java.util.ArrayList;
import java.util.List;

@Service
public class PokeApiImpl implements PokeApi{
    public static final String POKE_API_CHARACTERS_ENDPOINT = "https://pokeapi.co/api/v2/pokemon";
    @Override
    public Pokemon getPokemonByName(String name) throws ResourceNotFoundException {
        final String NOT_FOUND_EXC_MSG = "Pokemon character's name: " + name + " has not been found";

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        final String url = POKE_API_CHARACTERS_ENDPOINT + "/" + name;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response;
        try{
            response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        } catch (HttpClientErrorException e){
            throw new ResourceNotFoundException(NOT_FOUND_EXC_MSG);
        }

        List<Ability> abilities = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            JsonNode abilitiesNode = rootNode.get("abilities");
            for(int i=0; i<abilitiesNode.size(); i++){
                abilities.add(new Ability(abilitiesNode.get(i).get("ability").get("name").asText(), "NoDescription"));
            }
            return new Pokemon(name, abilities);
        } catch (JsonProcessingException e) {
            throw new ResourceNotFoundException(NOT_FOUND_EXC_MSG);
        }
    }
}
