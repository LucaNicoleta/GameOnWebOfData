package uaic.fii.MarvelMonPlay.services.impl;

import lombok.AllArgsConstructor;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
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
        playerRepository.createAccount(player.getUsername(), encodedPassword, AppUserRole.USER);
        return "Player successfully registered.";
    }
    public String getCurrentScene(String res_identifier){
        try (TupleQueryResult tqr = playerRepository.getcurrentSceneForPlayer(res_identifier)) {
            if(tqr.hasNext()){
                BindingSet bindingSet = tqr.next();

                String sceneResIdentifier = bindingSet.getValue("scene").toString().substring(11);
                
                return sceneResIdentifier;
            }
        }
        return null;
    }
    public     void updatePlayerCurrentScene(String res_session, String new_scene_res){
        playerRepository.updateProgressInGame(res_session, new_scene_res);
    }

    public void createNewSessionForPlayer(String res_identifier){
        playerRepository.createNewSessionForPlayer(res_identifier);
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
                Value marvelCharacterValue = bindingSet.getValue("marvelCharacter");
                Marvel marvel = null;
                if(marvelCharacterValue != null){
                    marvel = getMarvel(marvelCharacterValue.stringValue());
                }
                Value sessionValue = bindingSet.getValue("session");
                String playerResIdentifier = bindingSet.getValue("player").stringValue();
                playerResIdentifier = playerResIdentifier.substring(playerResIdentifier.indexOf("#")+1);
                String session_res = "";
                if(sessionValue!=null)
                session_res=sessionValue.toString().substring(11);
                //Level level = new Level(Stage.values()[levelIndex], null); //TODO:get level scene
                int appUserRoleIndex = Integer.parseInt(bindingSet.getValue("appUserRole").stringValue());
                AppUserRole appUserRole = AppUserRole.values()[appUserRoleIndex];
                String password = bindingSet.getValue("encryptedPassword").stringValue();
                return new Player(playerResIdentifier, username, password, marvel, session_res, appUserRole);
            }
        }
        throw new UsernameNotFoundException("Player with username \"" + username + "\" could not be found");
    }

    private Marvel getMarvel(String marvelCharacterResIdentifier) {
        Marvel marvel;
        try {
            String resIdentifier = marvelCharacterResIdentifier.substring(marvelCharacterResIdentifier.indexOf("#")+1);
            marvel = marvelService.findByResIdentifier(resIdentifier);
        } catch (ResourceNotFoundException e) {
           marvel = null;
        }
        return marvel;
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