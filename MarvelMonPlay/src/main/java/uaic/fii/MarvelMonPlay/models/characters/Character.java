package uaic.fii.MarvelMonPlay.models.characters;

import com.fasterxml.jackson.annotation.JsonProperty;

import uaic.fii.MarvelMonPlay.models.Element;

public class Character {
    public final String RES_IDENTIFIER;
    @JsonProperty("name")
    protected final String name;
    @JsonProperty("element")
    protected String element="";
    public Character(String RES_IDENTIFIER, String name) {
        this.RES_IDENTIFIER = RES_IDENTIFIER;
        this.name = name;
    }
    public void setElement(Element el){
        this.element = el.name();
    }
    public String getName() {
        return name;
    }
    public String getElement() {
        return element;
    }
}
