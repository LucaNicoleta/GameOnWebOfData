package uaic.fii.MarvelMonPlay.services.impl;

import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uaic.fii.MarvelMonPlay.exceptions.AbilityNotFoundException;
import uaic.fii.MarvelMonPlay.exceptions.PokemonNotFoundException;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.models.Event;
import uaic.fii.MarvelMonPlay.models.abilities.Ability;
import uaic.fii.MarvelMonPlay.models.characters.Pokemon;
import uaic.fii.MarvelMonPlay.repositories.PokemonRepository;
import uaic.fii.MarvelMonPlay.services.AbilityService;
import uaic.fii.MarvelMonPlay.services.PokemonService;

import java.util.ArrayList;
import java.util.List;

@Service
public class PokemonServiceImpl implements PokemonService {
    private final PokemonRepository pokemonRepository;

    @Autowired
    private AbilityService abilityService;

    public PokemonServiceImpl(PokemonRepository pokemonRepository){
        this.pokemonRepository = pokemonRepository;
    }
    @Override
    public List<Pokemon> findAll() {
        List<Pokemon> pokemonList = new ArrayList<>();
        try (TupleQueryResult tupleQueryResult = pokemonRepository.findAll()) {
            while (tupleQueryResult.hasNext()) {
                BindingSet bindingSet = tupleQueryResult.next();
                String name = bindingSet.getValue("name").stringValue();
                String abilitiesValue = bindingSet.getValue("abilities").stringValue();
                List<Ability> abilityList = getAbilities((abilitiesValue));
                Value healthPointsValue = bindingSet.getValue("healthPoints");
                Value powerAttackValue = bindingSet.getValue("powerAttack");
                Value powerDefensevalue = bindingSet.getValue("powerDefense");

                int healthPoints = healthPointsValue == null ? Pokemon.MAX_HEALTH_POINTS : Integer.parseInt(healthPointsValue.stringValue());
                int powerAttack = powerAttackValue == null ? Pokemon.DEFAULT_POWER_ATTACK : Integer.parseInt(powerAttackValue.stringValue());
                int powerDefense = powerDefensevalue == null ? Pokemon.DEFAULT_POWER_DEFENSE : Integer.parseInt(powerDefensevalue.stringValue());

                Value imageURLValue = bindingSet.getValue("imageURL");
                String imageURL = imageURLValue == null ? "" : imageURLValue.stringValue();

                pokemonList.add(new Pokemon("to_be_changed", name, abilityList, healthPoints, powerAttack, powerDefense, imageURL));
            }
        }
        return pokemonList;
    }

    @Override
    @Deprecated
    public Pokemon findByName(String name) throws ResourceNotFoundException {
        Pokemon pokemon = null;
        try(TupleQueryResult tqr = pokemonRepository.findByName(name)){
            if(tqr.hasNext()){
                BindingSet bindingSet = tqr.next();
                String abilitiesValue = bindingSet.getValue("abilities").stringValue();
                List<Ability> abilityList = getAbilities((abilitiesValue));
                Value healthPointsValue = bindingSet.getValue("healthPoints");
                Value powerAttackValue = bindingSet.getValue("powerAttack");
                Value powerDefensevalue = bindingSet.getValue("powerDefense");
                String res = bindingSet.getValue("p").toString().substring(11);

                int healthPoints = healthPointsValue == null ? Pokemon.MAX_HEALTH_POINTS : Integer.parseInt(healthPointsValue.stringValue());
                int powerAttack = powerAttackValue == null ? Pokemon.DEFAULT_POWER_ATTACK : Integer.parseInt(powerAttackValue.stringValue());
                int powerDefense = powerDefensevalue == null ? Pokemon.DEFAULT_POWER_DEFENSE : Integer.parseInt(powerDefensevalue.stringValue());

                Value imageURLValue = bindingSet.getValue("imageURL");
                String imageURL = imageURLValue == null ? "" : imageURLValue.stringValue();

                pokemon = new Pokemon(res, name, abilityList, healthPoints, powerAttack, powerDefense, imageURL);
            }
        }
        if(pokemon == null) {
            throw new ResourceNotFoundException("Pokemon " + name + " not found");
        }

        return pokemon;
    }

    @Override
    public Pokemon findByNameAndOwner(String name, String marvel_res) {
        Pokemon pokemon = null;
        try(TupleQueryResult tqr = pokemonRepository.findByNameAndOwner(name, marvel_res)){
            if(tqr.hasNext()){
                BindingSet bindingSet = tqr.next();
                
                String abilitiesValue = bindingSet.getValue("abilities").stringValue();
                List<Ability> abilityList = getAbilities((abilitiesValue));
                Value healthPointsValue = bindingSet.getValue("healthPoints");
                Value powerAttackValue = bindingSet.getValue("powerAttack");
                Value powerDefensevalue = bindingSet.getValue("powerDefense");
                String res = bindingSet.getValue("p").toString().substring(11);
                int healthPoints = healthPointsValue == null ? Pokemon.MAX_HEALTH_POINTS : Integer.parseInt(healthPointsValue.stringValue());
                int powerAttack = powerAttackValue == null ? Pokemon.DEFAULT_POWER_ATTACK : Integer.parseInt(powerAttackValue.stringValue());
                int powerDefense = powerDefensevalue == null ? Pokemon.DEFAULT_POWER_DEFENSE : Integer.parseInt(powerDefensevalue.stringValue());

                Value imageURLValue = bindingSet.getValue("imageURL");
                String imageURL = imageURLValue == null ? "" : imageURLValue.stringValue();

                pokemon = new Pokemon(res, name, abilityList, healthPoints, powerAttack, powerDefense, imageURL);
            }
        }


        return pokemon;
    }

    @Override
    public Pokemon findByResIdentifier(String resIdentifier) throws AbilityNotFoundException, PokemonNotFoundException {
        try (TupleQueryResult tqr = pokemonRepository.findByResIdentifier(resIdentifier)) {
            if(tqr.hasNext()){
                BindingSet bs = tqr.next();
                Value nameValue = bs.getValue("name");
                Value healthPointsValue = bs.getValue("healthPoints");
                Value powerAttackValue = bs.getValue("powerAttack");
                Value powerDefenseValue = bs.getValue("powerDefense");
                Value imageURLValue = bs.getValue("imageURL");

                String name = nameValue.stringValue();
                int healthPoints = Integer.parseInt(healthPointsValue.stringValue());
                int powerAttack = Integer.parseInt(powerAttackValue.stringValue());
                int powerDefense = Integer.parseInt(powerDefenseValue.stringValue());
                String imageURL = imageURLValue.stringValue();
                return new Pokemon(resIdentifier, name, null, healthPoints, powerAttack, powerDefense, imageURL);
            }
            throw new PokemonNotFoundException("Pokemon " + resIdentifier + " has not been found");
        }
    }

    @Override
    public List<Ability> getPokemonAbilities(String pokemonResIdentifier) throws AbilityNotFoundException {
        List<Ability> abilities = new ArrayList<>();
        List<String> abilityIdentifiers = abilityService.findAbilityIdentifiersByPokemonResIdentifier(pokemonResIdentifier);
        for(String abilityIdentifier : abilityIdentifiers){
            Ability ability = abilityService.findByResIdentifier(abilityIdentifier);
            abilities.add(ability);
        }

        return abilities;
    }

    @Deprecated
    private List<Ability> getAbilities(String abilities) {
        String[] abilitiesSplit = abilities.split(",");
        List<Ability> abilityList = new ArrayList<>();
        for (String s : abilitiesSplit) {
            String abilityName = s.substring(s.indexOf("#") + 1);
            abilityList.add(new Ability(abilityName, abilityName, "NoDescription"));
        }
        return abilityList;
    }

    @Override
    public void save(Pokemon pokemon, boolean cascadeSaveOrUpdate) {
        pokemonRepository.save(pokemon, cascadeSaveOrUpdate);
    }

    @Override
    public void update(Pokemon pokemon, boolean cascadeUpdate) {
        pokemonRepository.update(pokemon, cascadeUpdate);
    }

    @Override
    public void updateDefense(String RES_IDENTIFIER, int value) {
        pokemonRepository.updateDefense(RES_IDENTIFIER, value);
    }

    @Override
    public void updateHealth(String RES_IDENTIFIER, int value) {
        pokemonRepository.updateHealth(RES_IDENTIFIER, value);
    }

    @Override
    public void updateAttack(String RES_IDENTIFIER, int value) {
        pokemonRepository.updateAttack(RES_IDENTIFIER, value);
    }

    @Override
    public void delete(Pokemon pokemon) {
        pokemonRepository.delete(pokemon);
    }
    @Override
    public void pickPokemonForFight(String marvel_res,String pokemon_res, String pokemon_enemy_res){
        pokemonRepository.choosePokemonToFight(pokemon_res, pokemon_enemy_res);
    }
    @Override
    public Event fightRound(Pokemon pokemon_mc, Pokemon pokemon_npc)
    {
        Event firstFight = pokemon_mc.receiveAttack(pokemon_npc.getPowerAttack(),"MC");
        if(firstFight == Event.LOST_FIGHT || firstFight == Event.WON_FIGHT)
        return firstFight;
        update(pokemon_npc, true);
        update(pokemon_mc, true);
        Event secFight = pokemon_npc.receiveAttack(pokemon_mc.getPowerAttack(), "NPC");
        update(pokemon_npc, true);
        update(pokemon_mc, true);
        return secFight;
    }
    @Override
    public List<Pokemon> findPokemonsInFight(String MarvelRes){
        List<Pokemon> pokemons = new ArrayList<>();
        try (TupleQueryResult tqr = pokemonRepository.findPokemonsInFight(MarvelRes)) {
            if(tqr.hasNext()){
                BindingSet bs = tqr.next();
                Value pokemon_mc_Value = bs.getValue("p");
                Value pokemon_npc_Value = bs.getValue("e");
                String pokemon_mc = pokemon_mc_Value.stringValue().substring(11);
                String pokemon_npc = pokemon_npc_Value.stringValue().substring(11);
                pokemons.add(findByResIdentifier(pokemon_mc));
                pokemons.add(findByResIdentifier(pokemon_npc));
                
            }
            //throw new PokemonNotFoundException("enemy for pokemon owned by " + Res_Marvel + " has not been found");
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        return pokemons; 
    }
    @Override
    public String findCurrentPokemonEnemy(String Res_Marvel){
        try (TupleQueryResult tqr = pokemonRepository.findCurrentPokemonEnemy(Res_Marvel)) {
            if(tqr.hasNext()){
                BindingSet bs = tqr.next();
                Value nameValue = bs.getValue("e");

                String name = nameValue.stringValue().substring(11);
                return name;
            }
            return null;
            //throw new PokemonNotFoundException("enemy for pokemon owned by " + Res_Marvel + " has not been found");
        }
    }
}
