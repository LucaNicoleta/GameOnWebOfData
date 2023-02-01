package uaic.fii.MarvelMonPlay.services.impl;

import org.eclipse.rdf4j.query.TupleQueryResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.repositories.MarvelRepository;

import static org.junit.jupiter.api.Assertions.*;

class MarvelServiceImplTest {

    private MarvelServiceImpl marvelService;
    private MarvelRepository marvelRepositoryMock;

    @BeforeEach
    void init(){
        marvelRepositoryMock = Mockito.mock(MarvelRepository.class);
        marvelService = new MarvelServiceImpl(marvelRepositoryMock);
    }

    @Test
    public void marvelFindThrowsException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            String name = "noMarvel";
            try (TupleQueryResult mockResult = Mockito.mock(TupleQueryResult.class)){
                Mockito.when(mockResult.hasNext()).thenReturn(false);
                Mockito.when(marvelRepositoryMock.findByName(name)).thenReturn(mockResult);
            }
            marvelService.findByName(name);
        });
    }

}