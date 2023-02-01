package uaic.fii.MarvelMonPlay.services.impl;


import org.assertj.core.groups.Tuple;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import uaic.fii.MarvelMonPlay.endpoints.SparqlEndpoint;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.repositories.PokemonRepository;
import uaic.fii.MarvelMonPlay.repositories.impl.PokemonRepositoryImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PokemonServiceImplTest {

    private PokemonServiceImpl pokemonService;
    private PokemonRepository pokemonRepositoryMock;

    @BeforeEach
    void init(){
        pokemonRepositoryMock = Mockito.mock(PokemonRepository.class);
        pokemonService = new PokemonServiceImpl(pokemonRepositoryMock);
    }

    @Test
    public void pokemonFindThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            String name = "noPokemoon";
            try (TupleQueryResult mockResult = Mockito.mock(TupleQueryResult.class)){
                Mockito.when(mockResult.hasNext()).thenReturn(false);
                Mockito.when(pokemonRepositoryMock.findByName(name)).thenReturn(mockResult);
            }
            pokemonService.findByName(name);
        });
    }
}