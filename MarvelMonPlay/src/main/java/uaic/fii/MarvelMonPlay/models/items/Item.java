package uaic.fii.MarvelMonPlay.models.items;

import org.apache.solr.common.annotation.JsonProperty;

import uaic.fii.MarvelMonPlay.models.Action;
import uaic.fii.MarvelMonPlay.models.Element;

public  class Item {

    public final String RES_IDENTIFIER; 
    @JsonProperty("element")
    private final Element element;
    @JsonProperty("action")
    private final Action action;

    public Item(String RES_IDENTIFIER, Element element, Action action) {
        this.RES_IDENTIFIER = RES_IDENTIFIER;
        this.element = element;
        this.action = action;
    }

    public Element getElement() {
        return element;
    }
 

    public Action getAction(){
        return action;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + element + '\'' +
                "action'="+ action.description + '\'' +
                '}';
    }
}
