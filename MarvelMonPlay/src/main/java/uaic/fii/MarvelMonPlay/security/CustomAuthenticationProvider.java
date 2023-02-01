package uaic.fii.MarvelMonPlay.security;

import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import uaic.fii.MarvelMonPlay.models.players.Player;
import uaic.fii.MarvelMonPlay.services.impl.PlayerServiceImpl;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Resource
    PlayerServiceImpl playerService;
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        Player player = null;
        try {
            player = playerService.findPlayerByUsername(userName);
            logger.info("log1");
            logger.info(player.getUsername()+"; "+player.getPassword()+"; ");
        }catch (UsernameNotFoundException exception) {
            throw new BadCredentialsException("invalid login details");
        }
        if(bCryptPasswordEncoder.matches(password, player.getPassword())){
            logger.info("match");
            return new UsernamePasswordAuthenticationToken(player.getUsername(), player.getPassword(), player.getAuthorities());
        }
        else{
            throw new BadCredentialsException("Invalid credentials");
        }
    }

    private Authentication createSuccessfulAuthentication(final Authentication authentication, final Player player) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(player.getUsername(), authentication.getCredentials(), player.getAuthorities());
        token.setDetails(authentication.getDetails());
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
