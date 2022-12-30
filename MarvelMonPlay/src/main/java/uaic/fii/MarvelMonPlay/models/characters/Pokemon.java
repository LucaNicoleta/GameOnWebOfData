package uaic.fii.MarvelMonPlay.models.characters;

import uaic.fii.MarvelMonPlay.models.abilities.Ability;

import java.util.List;

//TODO: Init healthPoints in constructor
public class Pokemon extends Character{
    public static final int MAX_HEALTH_POINTS = 100;
    private List<Ability> abilities;
    private int healthPoints = MAX_HEALTH_POINTS;

    public Pokemon(String name, List<Ability> abilities) {
        super(name);
        this.abilities = abilities;
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
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "name='" + name + '\'' +
                ", abilities=" + abilities +
                '}';
    }
}
