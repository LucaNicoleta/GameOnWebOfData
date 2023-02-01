package uaic.fii.MarvelMonPlay.externalApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.models.abilities.Ability;
import uaic.fii.MarvelMonPlay.models.characters.Pokemon;
import uaic.fii.MarvelMonPlay.services.PokemonService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PokeApiImpl implements PokeApi{
    public static final String POKE_API_CHARACTERS_ENDPOINT = "https://pokeapi.co/api/v2/pokemon";

    private final PokemonService pokemonService;
    private final Logger logger = LoggerFactory.getLogger(PokeApiImpl.class);

    public PokeApiImpl(PokemonService pokemonService){
        this.pokemonService = pokemonService;
    }
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
                String abilityName = abilitiesNode.get(i).get("ability").get("name").asText();
                abilities.add(new Ability(UUID.randomUUID().toString(), abilityName, "NoDescription"));
            }
            String imageURL = rootNode.get("sprites").get("other").get("official-artwork").get("front_default").asText();
            Pokemon pokemon = new Pokemon(UUID.randomUUID().toString(), name, abilities, imageURL);
            saveIfNotExistsIntoDatabase(pokemon);
            return pokemon;
        } catch (JsonProcessingException e) {
            throw new ResourceNotFoundException(NOT_FOUND_EXC_MSG);
        }
    }

    private void saveIfNotExistsIntoDatabase(Pokemon pokemon) {
        String name = pokemon.getName();
        try {
            pokemonService.findByName(name);
            logger.info("Pokemon " + name + " NOT saved into database");
        } catch (ResourceNotFoundException e) {
            pokemonService.save(pokemon, true);
            logger.info("Pokemon " + name + " saved into database");
        }
    }
}
