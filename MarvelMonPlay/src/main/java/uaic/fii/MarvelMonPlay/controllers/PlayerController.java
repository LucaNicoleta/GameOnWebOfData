package uaic.fii.MarvelMonPlay.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uaic.fii.MarvelMonPlay.exceptions.PlayerAlreadyRegisteredException;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.managers.PasswordManager;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;
import uaic.fii.MarvelMonPlay.models.players.AppUserRole;
import uaic.fii.MarvelMonPlay.models.players.Player;
import uaic.fii.MarvelMonPlay.repositories.PlayerRepository;
import uaic.fii.MarvelMonPlay.services.impl.RegistrationService;
import uaic.fii.MarvelMonPlay.utils.LoginDto;
import uaic.fii.MarvelMonPlay.utils.RegisterDto;

@RestController
@RequestMapping(path = "/auth")
@AllArgsConstructor
public class PlayerController {

    private RegistrationService registrationService;

//    @PostMapping("/login")
//    public ResponseEntity<String> loginPlayer(@RequestBody LoginDto loginDto){
////        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
////                loginDto.getUsername(), loginDto.getPassword()));
////
////        SecurityContextHolder.getContext().setAuthentication(authentication);
////        return new ResponseEntity<>("User logged-in successfully!.", HttpStatus.OK);
//    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/test/{name}")
    public String getPokemon(@PathVariable("name") String name) throws ResourceNotFoundException {
        return name + "hello123";
    }

    @PostMapping("/signup")
    public String registerUser(@RequestBody RegisterDto registerDto) throws PlayerAlreadyRegisteredException {

        return registrationService.register(registerDto);

    }

}
