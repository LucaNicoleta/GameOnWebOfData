package uaic.fii.MarvelMonPlay.models;

public enum Action {
    INCREASE_POWER_ATTACK("Increase your pokemon power attack by 10"),
    INCREASE_DEFENSE("Increase your pokemon power attack by 10"),
    DECREASE_ENEMY_POWER_ATTACK("Increase your enemy's pokemon power attack by 10"),
    DECREASE_ENEMY_DEFENSE("Decrease your enemy's pokemon defense by 10");
    public final String description;

    private Action(String label) {
        this.description = label;
    }
}
