package uaic.fii.MarvelMonPlay.models.scenes;

import uaic.fii.MarvelMonPlay.models.Event;

public class Option {
    public OptionsEnum optValue;
    public String content;
    public Event eventTriggered;
    public String RES_IDENTIFIER;

    public Option(String RES_IDENTIFIER, String content , Event eventTriggered, OptionsEnum op){
    this.RES_IDENTIFIER = RES_IDENTIFIER;
    this.content = content;
    this.eventTriggered = eventTriggered;
    this.optValue = op;
    }
}
