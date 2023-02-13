package uaic.fii.MarvelMonPlay.repositories.impl;

import org.eclipse.rdf4j.query.TupleQueryResult;
import org.springframework.stereotype.Repository;

import uaic.fii.MarvelMonPlay.endpoints.SparqlEndpoint;
import uaic.fii.MarvelMonPlay.models.Element;
import uaic.fii.MarvelMonPlay.models.scenes.NextSceneRef;
import uaic.fii.MarvelMonPlay.models.scenes.Option;
import uaic.fii.MarvelMonPlay.models.scenes.OptionsEnum;
import uaic.fii.MarvelMonPlay.models.scenes.Scene;
import uaic.fii.MarvelMonPlay.utils.IRIFactory;
@Repository
public class SceneRepository {
    private final SparqlEndpoint sparqlEndpoint;

    public SceneRepository(SparqlEndpoint sparqlEndpoint) {
        this.sparqlEndpoint = sparqlEndpoint;
    }

    public TupleQueryResult findSceneInfo(String SceneIdentifier) {
        return sparqlEndpoint.executeQuery(
                "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
                        "PREFIX vgo: <http://purl.org/net/VideoGameOntology#>" +
                        "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
                        "select ?type ?content ?imageUrl ?Marvel ?req  where {" +
                        "    IRI:" + SceneIdentifier + " IRI:sceneType ?type ." +
                        "    IRI:" + SceneIdentifier + " IRI:hasImageUrl ?imageUrl ." +
                        "    IRI:" + SceneIdentifier + " IRI:hasContent ?content ." +
                        "    IRI:" + SceneIdentifier + " IRI:hasMarvelCharacter ?Marvel ." +
                        "    IRI:" + SceneIdentifier + " IRI:requires ?req ." +

                        "}");
    }

    public TupleQueryResult findSceneWithReqElement(NextSceneRef ref, String ResMarvel) {

        StringBuilder scenes = new StringBuilder();
        for (String s : ref.posibleScenesRES) {
            scenes.append("?s=IRI:" + s + "||");
        }
        scenes.delete(scenes.length() - 2, scenes.length());
        //System.out.println(scenes);
        return sparqlEndpoint.executeQuery(
                "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
                        "PREFIX vgo: <http://purl.org/net/VideoGameOntology#>" +
                        "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
                        "select ?s  where {" +

                        " ?s   foaf:a IRI:Scene ." +
                        "    ?m foaf:a IRI:Marvel ." +
                        "    ?m IRI:belongsToElement ?e ." +
                        "   ?s IRI:requires ?e ." +
                        " Filter(?m=IRI:" + ResMarvel + "&&(" + scenes + "))" +
                        "}");
    }

    public TupleQueryResult findSceneWithReqPokemon(NextSceneRef ref, String ResMarvel) {
        System.out.println();
        StringBuilder scenes = new StringBuilder();
        StringBuilder scenes2 = new StringBuilder();

        for (String s : ref.posibleScenesRES) {
            scenes.append("?s1=IRI:" + s + "||");
            scenes2.append("?s2=IRI:" + s + "||");

        }
        scenes.delete(scenes.length() - 2, scenes.length());
        scenes2.delete(scenes2.length() - 2, scenes2.length());

        System.out.println(scenes);
        return sparqlEndpoint.executeQuery(
                "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
                        "PREFIX vgo: <http://purl.org/net/VideoGameOntology#>" +
                        "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
                        "select ?s  where {" +
                        "?s1   foaf:a IRI:Scene . " +
                        "?s1 IRI:requires ?pn . " +
                        "?s2 foaf:a IRI:Scene . " +
                        "?s2 IRI:requires \"NONE\" . " +
                        "?m foaf:a IRI:Marvel ." +
                        "?i IRI:hasInventoryPokemon ?p . " +
                        "?p IRI:hasName ?pn " +
                        "Filter(?m=IRI:" + ResMarvel + "&&(" + scenes + ")"+"&&(" + scenes2 + "))" +
                        "bind(IF(EXISTS{?m IRI:hasInventory ?i},?s1,?s2) as ?s)"+
                        "}");
    }

    public TupleQueryResult findSceneWithReqOption(NextSceneRef ref, String option) {
        System.out.println();
        StringBuilder scenes = new StringBuilder();
        for (String s : ref.posibleScenesRES) {
            scenes.append("?s=IRI:" + s + "||");
        }
        scenes.delete(scenes.length() - 2, scenes.length());
        System.out.println(scenes);
        return sparqlEndpoint.executeQuery(
                "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
                        "PREFIX vgo: <http://purl.org/net/VideoGameOntology#>" +
                        "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
                        "select ?s  where { " +
                        "?s   foaf:a IRI:Scene . " +
                        "?s IRI:requires ?o . " +
                        "Filter(?o=IRI:" + option + "&&(" + scenes + "))" +
                        "}");
    }

    public TupleQueryResult findSceneOptions(String SceneIdentifier) {
        return sparqlEndpoint.executeQuery(
                "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
                        "PREFIX vgo: <http://purl.org/net/VideoGameOntology#>" +
                        "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
                        "select ?option ?content ?event ?value  where {" +
                        "?option foaf:a IRI:Option ." +
                        "IRI:" + SceneIdentifier + " IRI:hasOption ?option ." +
                        "?option IRI:optionValue ?value . " +
                        "?option IRI:hasContent ?content  . " +
                        "?option IRI:triggerEvent ?event  . }");
    }

    private static boolean isElement(String el) {
        for (Element x : Element.values()) {
            if (el.equals(x.name()))
                return true;
        }
        return false;
    }

    private static boolean isOption(String o) {
        for (OptionsEnum x : OptionsEnum.values()) {
            if (o.equals(x.name()))
                return true;
        }
        return false;
    }

    public void save(Scene scene) {
        StringBuilder s = new StringBuilder();
        for (Option o : scene.options)
            s.append("    IRI:" + scene.RES_IDENTIFIER + " IRI:hasOption IRI:" + o.RES_IDENTIFIER + " .");
        String req;
        if (isElement(scene.requirement) || isOption(scene.requirement))
            req = "IRI:" + scene.requirement;
        else
            req = "\"" + scene.requirement + "\"";
        sparqlEndpoint.executeUpdate(
                "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
                        "PREFIX vgo: <http://purl.org/net/VideoGameOntology#>" +
                        "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
                        "insert data {" +
                        "IRI:" + scene.RES_IDENTIFIER + " foaf:a " + "IRI:Scene" + " ." +
                        "    IRI:" + scene.RES_IDENTIFIER + " IRI:sceneType \"" + scene.sceneType.name() + "\" ." +
                        "    IRI:" + scene.RES_IDENTIFIER + " IRI:hasImageUrl \"" + scene.imageURL + "\" ." +
                        "    IRI:" + scene.RES_IDENTIFIER + " IRI:hasContent \"" + scene.text + "\" ." +
                        "    IRI:" + scene.RES_IDENTIFIER + " IRI:requires " + req + " ." +
                        (scene.MarvelRES != " "
                                ? "    IRI:" + scene.RES_IDENTIFIER + " IRI:hasMarvelCharacter IRI:" + scene.MarvelRES
                                        + " ."
                                : "")
                        +

                        s.toString() +
                        "}");
    }

    public void setRef(String scene, String RefRES) {

        sparqlEndpoint.executeUpdate(
                "PREFIX IRI: <" + IRIFactory.BASE_ONTOLOGY_IRI + ">" +
                        "PREFIX vgo: <http://purl.org/net/VideoGameOntology#>" +
                        "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
                        "insert data {" +
                        "IRI:" + scene + " foaf:a " + "IRI:Scene" + " ." +
                        "IRI:" + scene + " IRI:hasNextScenesRef IRI:" + RefRES + " ." +

                        "}");
    }

}
