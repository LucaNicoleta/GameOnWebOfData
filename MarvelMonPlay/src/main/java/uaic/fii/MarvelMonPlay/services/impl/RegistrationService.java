package uaic.fii.MarvelMonPlay.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uaic.fii.MarvelMonPlay.exceptions.PlayerAlreadyRegisteredException;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.models.levels.Level;
import uaic.fii.MarvelMonPlay.models.levels.Stage;
import uaic.fii.MarvelMonPlay.models.players.AppUserRole;
import uaic.fii.MarvelMonPlay.models.players.Player;
import uaic.fii.MarvelMonPlay.utils.RegisterDto;

@Service
@AllArgsConstructor
public class RegistrationService {
    private PlayerServiceImpl playerService;
    private SceneService sceneService;

    public String register(RegisterDto registerDto) throws PlayerAlreadyRegisteredException, ResourceNotFoundException {
        return playerService.signUpUser(
            new Player(registerDto.getRES_IDENTIFIER(),
                registerDto.getUsername(),
                registerDto.getPassword(),"",
                AppUserRole.USER)
        );
    }
}
