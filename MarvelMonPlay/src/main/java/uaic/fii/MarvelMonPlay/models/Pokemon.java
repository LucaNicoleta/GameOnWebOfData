package uaic.fii.MarvelMonPlay.models;

import java.util.List;

public class Pokemon extends Character{
    private List<Ability> abilities;

    public Pokemon(String name, List<Ability> abilities) {
        super(name);
        this.abilities = abilities;
    }

    public String getName() {
        return name;
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
