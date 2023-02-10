package uaic.fii.MarvelMonPlay.endpoints;

import org.eclipse.rdf4j.model.*;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class SparqlEndpointImpl implements SparqlEndpoint {
    @Value("${spring.rdf4j.sparql.endpoint-url}")
    private String endpointUrl;
    @Value("${spring.rdf4j.sparql.update-endpoint-url}")
    private String updateEndpointUrl;

    private final org.eclipse.rdf4j.repository.Repository repository;

    public SparqlEndpointImpl(@Value("${spring.rdf4j.sparql.endpoint-url}") String endpointUrl,
                              @Value("${spring.rdf4j.sparql.update-endpoint-url}") String updateEndpointUrl) {
        this.repository = new SPARQLRepository(endpointUrl, updateEndpointUrl);
        repository.init();
    }

    @Override
    public void executeUpdate(String update) {
       // System.out.println(update);
        try (RepositoryConnection conn = repository.getConnection()) {
            conn.prepareUpdate(QueryLanguage.SPARQL, update).execute();
        }
    }

    //TODO: close the repository connection
    @Override
    public TupleQueryResult executeQuery(String query) {
        RepositoryConnection connection = repository.getConnection();
        TupleQuery tupleQuery = connection.prepareTupleQuery(QueryLanguage.SPARQL, query);
        TupleQueryResult result = tupleQuery.evaluate();
        //connection.close();
        return result;
    }

    @Override
    public void saveTriple(String subject, String predicate, String object) {
        RepositoryConnection conn = repository.getConnection();
        ValueFactory vf = SimpleValueFactory.getInstance();
        conn.begin();
        conn.add(vf.createIRI(subject), vf.createIRI(predicate), vf.createIRI(object));
        conn.commit();
        conn.close();
    }

    @Override
    public void saveModel(Model model) {
        RepositoryConnection conn = repository.getConnection();
        conn.begin();
        conn.add(model);
        conn.commit();
        conn.close();
    }

    @Override
    public void importOwlFromFile(File ontologyFile, RDFFormat format) throws IOException {
        RepositoryConnection conn = repository.getConnection();
        conn.add(ontologyFile, "", format);
        conn.close();
    }
}
