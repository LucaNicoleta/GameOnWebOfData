package uaic.fii.MarvelMonPlay.services.impl;

import lombok.AllArgsConstructor;
import org.apache.zookeeper.Op;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uaic.fii.MarvelMonPlay.exceptions.PlayerAlreadyRegisteredException;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;
import uaic.fii.MarvelMonPlay.models.levels.Level;
import uaic.fii.MarvelMonPlay.models.levels.Stage;
import uaic.fii.MarvelMonPlay.models.players.AppUserRole;
import uaic.fii.MarvelMonPlay.models.players.Player;
import uaic.fii.MarvelMonPlay.repositories.impl.PlayerRepositoryImpl;
import uaic.fii.MarvelMonPlay.services.MarvelService;
import uaic.fii.MarvelMonPlay.services.PlayerService;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepositoryImpl playerRepository;
    @Autowired
    private MarvelService marvelService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String signUpUser(Player player) throws PlayerAlreadyRegisteredException {
        checkIfPlayerAlreadyRegistered(player);
        String encodedPassword = bCryptPasswordEncoder.encode(player.getPassword());
        player.setPassword(encodedPassword);
        playerRepository.save(player, true);
        return "Player successfully registered.";
    }

    private void checkIfPlayerAlreadyRegistered(Player player) throws PlayerAlreadyRegisteredException {
        String username = player.getUsername();
        try{
            findPlayerByUsername(player.getUsername());
            throw new PlayerAlreadyRegisteredException("Player with username \"" + username + "\" is already registered.");
        } catch (UsernameNotFoundException ignored){}
    }

    @Override
    public Player findPlayerByUsername(String username){
        try (TupleQueryResult tqr = playerRepository.findPlayerByUsername(username)) {
            if(tqr.hasNext()){
                BindingSet bindingSet = tqr.next();
                String marvelCharacterResIdentifier = bindingSet.getValue("marvelCharacter").stringValue();
                Marvel marvel;
                try {
                    String resIdentifier = marvelCharacterResIdentifier.substring(marvelCharacterResIdentifier.indexOf("#")+1);
                    marvel = marvelService.findByResIdentifier(resIdentifier);
                } catch (ResourceNotFoundException e) {
                   marvel = null;
                }
                String playerResIdentifier = bindingSet.getValue("player").stringValue();
                playerResIdentifier = playerResIdentifier.substring(playerResIdentifier.indexOf("#")+1);
                int levelIndex = Integer.parseInt(bindingSet.getValue("level").stringValue());
                Level level = new Level(Stage.values()[levelIndex], null); //TODO:get level scene
                int appUserRoleIndex = Integer.parseInt(bindingSet.getValue("appUserRole").stringValue());
                AppUserRole appUserRole = AppUserRole.values()[appUserRoleIndex];
                String password = bindingSet.getValue("encryptedPassword").stringValue();
                return new Player(playerResIdentifier, username, password, marvel, level, appUserRole);
            }
        }
        throw new UsernameNotFoundException("Player with username \"" + username + "\" could not be found");
    }

    @Override
    public void save(Player player, boolean cascadeSave) {
        playerRepository.save(player, cascadeSave);
    }

    @Override
    public void update(Player player, boolean cascadeSave) {
        playerRepository.update(player, cascadeSave);
    }

    @Override
    public void updateForRestart(String PLAYER_RES_IDENTIFIER) throws ResourceNotFoundException {
        playerRepository.updateForRestart(PLAYER_RES_IDENTIFIER);
    }

    @Override
    public void setMarvelCharacter(String PLAYER_RES_IDENTIFIER, Marvel marvel, boolean cascadeSave) {
        playerRepository.setMarvelCharacter(PLAYER_RES_IDENTIFIER, marvel, cascadeSave);
    }

    @Override
    public void updateLevel(String PLAYER_RES_IDENTIFIER, Level level) {
        playerRepository.updateLevel(PLAYER_RES_IDENTIFIER, level);
    }

    @Override
    public void delete(Player player) {
        playerRepository.delete(player);
    }
}