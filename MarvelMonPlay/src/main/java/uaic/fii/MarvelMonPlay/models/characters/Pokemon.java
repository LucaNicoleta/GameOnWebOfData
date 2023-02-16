package uaic.fii.MarvelMonPlay.models.characters;

import uaic.fii.MarvelMonPlay.models.Event;
import uaic.fii.MarvelMonPlay.models.abilities.Ability;

import java.util.ArrayList;
import java.util.List;

public class Pokemon extends Character{
    public static final int MAX_HEALTH_POINTS = 100;
    public static final int DEFAULT_POWER_ATTACK = 10;
    public static final int DEFAULT_POWER_DEFENSE = 10;
  
    private int healthPoints;
    private int powerAttack;
    private int powerDefense;
    private String imageURL;

    public Pokemon(String RES_IDENTIFIER, String name, List<Ability> abilities, int healthPoints, int powerAttack, int powerDefense, String imageURL){
        super(RES_IDENTIFIER, name);
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
/* 
    public List<Ability> getAbilities() {
        return new ArrayList<>(abilities);
    }
*/
    public int getPowerAttack() {
        return powerAttack;
    }

    public void setPowerAttack(int powerAttack) {
        this.powerAttack = powerAttack;
    }

    public void increasePowerAttack() {
        this.powerAttack+=10;
    }
    public void decreasePowerAttack() {
        this.powerAttack-=10;
        if(this.powerAttack<0)
        this.powerAttack = 0;
    }
    public void increaseDefense() {
        this.powerDefense+=10;
    }
    public void decreaseDefense() {
        
        this.powerDefense-=10;
        if(this.powerDefense<0)
        this.powerDefense = 0;
    }
    public Event receiveAttack(int power, String owner){
        if(powerDefense>0)
        {
            if(powerDefense<power){
                int healthDamage = power - powerDefense;
                powerDefense = 0;
                healthPoints-=healthDamage; 
                
            }
            else{
                powerDefense-=power;
            }
        }
        else{
            if(healthPoints<=power)
              healthPoints = 0;
            else
            healthPoints-=power;

        }
        if(healthPoints<=0)
        return owner=="MC"?Event.LOST_FIGHT:Event.WON_FIGHT;
        return Event.CONTINUE_FIGHT;
    }
    public int getPowerDefense() {
        return powerDefense;
    }

    public void setPowerDefense(int powerDefense) {
        this.powerDefense = powerDefense;
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
                ", healthPoints=" + healthPoints +
                ", powerAttack=" + powerAttack +
                ", powerDefense=" + powerDefense +
                ", imageURL='" + imageURL + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
