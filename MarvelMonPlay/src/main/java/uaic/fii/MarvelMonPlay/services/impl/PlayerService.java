package uaic.fii.MarvelMonPlay.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uaic.fii.MarvelMonPlay.models.players.Player;
import uaic.fii.MarvelMonPlay.repositories.impl.PlayerRepositoryImpl;
import uaic.fii.MarvelMonPlay.utils.LoginDto;

@Service
@AllArgsConstructor
public class PlayerService implements  UserDetailsService {

    private final static  String USER_NOT_FOUND_MSG =
            "with username %s not found";
    private final PlayerRepositoryImpl playerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public Player loadUserByUsername(String username) throws UsernameNotFoundException {
        return playerRepository.findPlayerByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
    }

    public String signUpUser(Player player){
        if(playerRepository.existsPlayerByUsername(player.getUsername())){
            throw new IllegalStateException("username already exists");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(player.getPassword());
        player.setPassword(encodedPassword);
        playerRepository.save(player);
        return "it works hehe";
    }
}
