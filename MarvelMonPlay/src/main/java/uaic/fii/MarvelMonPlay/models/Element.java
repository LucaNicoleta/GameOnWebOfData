package uaic.fii.MarvelMonPlay.models;

public enum Element {
    WATER("Water"), AIR("Air"), EARTH("Earth"), FIRE("Fire"); 
    public final String label;

    private Element(String label) {
        this.label = label;
    }
}
