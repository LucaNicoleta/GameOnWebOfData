package uaic.fii.MarvelMonPlay.models;

public class Ability {
    private String name;

    public Ability(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Ability{" +
                "name='" + name + '\'' +
                '}';
    }
}
