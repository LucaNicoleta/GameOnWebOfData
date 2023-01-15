package uaic.fii.MarvelMonPlay.models.characters;

import uaic.fii.MarvelMonPlay.models.abilities.Ability;

import java.util.ArrayList;
import java.util.List;

public class Pokemon extends Character{
    public static final int MAX_HEALTH_POINTS = 100;
    private List<Ability> abilities;
    private int healthPoints = MAX_HEALTH_POINTS;

    public Pokemon(String RES_IDENTIFIER, String name, List<Ability> abilities) {
        super(RES_IDENTIFIER, name);
        this.abilities = abilities;
    }

    public Pokemon(String RES_IDENTIFIER, String name){
        super(RES_IDENTIFIER, name);
        abilities = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getHealthPoints(){
        return healthPoints;
    }
    public void setHealthPoints(int healthPoints){
        this.healthPoints = healthPoints;
    }

    public List<Ability> getAbilities() {
        return new ArrayList<>(abilities);
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    public void addAbility(Ability ability){
        abilities.add(ability);
    }

    public void removeAbility(Ability ability){
        abilities.remove(ability);
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "name='" + name + '\'' +
                ", abilities=" + abilities +
                '}';
    }
}
