package uaic.fii.MarvelMonPlay.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uaic.fii.MarvelMonPlay.exceptions.PlayerAlreadyRegisteredException;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;
import uaic.fii.MarvelMonPlay.models.levels.Level;
import uaic.fii.MarvelMonPlay.models.players.AppUserRole;
import uaic.fii.MarvelMonPlay.models.players.Player;
import uaic.fii.MarvelMonPlay.utils.RegisterDto;

@Service
@AllArgsConstructor
public class RegistrationService {
    PlayerServiceImpl playerService;

    //TODO: change String to Marvel type
    public String register(RegisterDto registerDto) throws PlayerAlreadyRegisteredException {
        return playerService.signUpUser( new Player(registerDto.getRES_IDENTIFIER(),
                registerDto.getUsername(),
                registerDto.getPassword(),
                new Marvel(" "," ", " ", " "),
                Level.WATER,
                AppUserRole.USER));
    }
}
