package uaic.fii.MarvelMonPlay.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uaic.fii.MarvelMonPlay.managers.PasswordManager;
import uaic.fii.MarvelMonPlay.models.players.Player;
import uaic.fii.MarvelMonPlay.repositories.PlayerRepository;
import uaic.fii.MarvelMonPlay.utils.LoginDto;
import uaic.fii.MarvelMonPlay.utils.RegisterDto;

@RestController
@RequestMapping("/auth")
public class PlayerController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PlayerRepository playerRepository;

    @PostMapping("/login")
    public ResponseEntity<String> loginPlayer(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User logged-in successfully!.", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDto registerDto){

        // add check for username exists in a DB
        if(playerRepository.existsPlayerByUsername(registerDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create player object
        Player player = new Player(
                registerDto.getRES_IDENTIFIER(), registerDto.getUsername(), new PasswordManager(
                        registerDto.getPassword()).getEncryptedPassword(), registerDto.getMarvelCharacter(),0);


        playerRepository.saveOrUpdate(player, true);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }

}
