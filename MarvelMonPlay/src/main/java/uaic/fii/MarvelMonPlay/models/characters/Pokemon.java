package uaic.fii.MarvelMonPlay.models.characters;

import uaic.fii.MarvelMonPlay.models.abilities.Ability;

import java.util.ArrayList;
import java.util.List;

public class Pokemon extends Character{
    public static final int MAX_HEALTH_POINTS = 100;
    public static final int DEFAULT_POWER_ATTACK = 10;
    public static final int DEFAULT_POWER_DEFENSE = 10;
    private List<Ability> abilities;
    private int healthPoints;
    private int powerAttack;
    private int powerDefense;
    private String imageURL;

    public Pokemon(String RES_IDENTIFIER, String name, List<Ability> abilities, int healthPoints, int powerAttack, int powerDefense, String imageURL){
        super(RES_IDENTIFIER, name);
        this.abilities = abilities;
        this.healthPoints = healthPoints;
        this.powerAttack = powerAttack;
        this.powerDefense = powerDefense;
        this.imageURL = imageURL;
    }

    public Pokemon(String RES_IDENTIFIER, String name, List<Ability> abilities, int powerAttack, int powerDefense, String imageURL){
        this(RES_IDENTIFIER, name, abilities, MAX_HEALTH_POINTS, powerAttack, powerDefense, imageURL);
    }

    public Pokemon(String RES_IDENTIFIER, String name, List<Ability> abilities, String imageURL) {
        this(RES_IDENTIFIER, name, abilities, DEFAULT_POWER_ATTACK, DEFAULT_POWER_DEFENSE, imageURL);
    }

    public Pokemon(String RES_IDENTIFIER, String name, int powerAttack, int powerDefense, String imageURL) {
        this(RES_IDENTIFIER, name, new ArrayList<>(), powerAttack, powerDefense, imageURL);
    }

    public Pokemon(String RES_IDENTIFIER, String name, String imageURL){
        this(RES_IDENTIFIER, name, new ArrayList<>(), DEFAULT_POWER_ATTACK, DEFAULT_POWER_DEFENSE, imageURL);
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

    public int getPowerAttack() {
        return powerAttack;
    }

    public void setPowerAttack(int powerAttack) {
        this.powerAttack = powerAttack;
    }

    public int getPowerDefense() {
        return powerDefense;
    }

    public void setPowerDefense(int powerDefense) {
        this.powerDefense = powerDefense;
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "abilities=" + abilities +
                ", healthPoints=" + healthPoints +
                ", powerAttack=" + powerAttack +
                ", powerDefense=" + powerDefense +
                ", imageURL='" + imageURL + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
