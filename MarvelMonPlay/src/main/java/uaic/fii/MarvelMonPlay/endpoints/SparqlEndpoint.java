package uaic.fii.MarvelMonPlay.endpoints;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.rio.RDFFormat;

import java.io.File;
import java.io.IOException;

public interface SparqlEndpoint {
    void executeUpdate(String update);
    TupleQueryResult executeQuery(String query);
    void saveTriple(String subject, String predicate, String object);
    void saveTriple(Model model);

    /**
     * Example of calling this function: importOwlFromFile(ResourceUtils.getFile("classpath:ontology.owl"), RDFFormat.RDFXML)
     * where ontology.owl file exists in the resource folder and its RDF format is RDFXML
     */
    void importOwlFromFile(File ontologyFile, RDFFormat format) throws IOException;
}
