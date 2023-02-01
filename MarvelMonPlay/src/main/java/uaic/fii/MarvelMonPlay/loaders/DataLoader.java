package uaic.fii.MarvelMonPlay.loaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uaic.fii.MarvelMonPlay.externalApi.MarvelApi;
import uaic.fii.MarvelMonPlay.services.MarvelService;
import uaic.fii.MarvelMonPlay.services.PlayerService;

import java.util.UUID;

//TODO: This class is TEMPORARILY used for testing different components
@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private PlayerService playerService;

    @Autowired
    private MarvelService marvelService;

    @Autowired
    private MarvelApi marvelApi;

    @Override
    public void run(String... args) throws Exception {
    }
}
