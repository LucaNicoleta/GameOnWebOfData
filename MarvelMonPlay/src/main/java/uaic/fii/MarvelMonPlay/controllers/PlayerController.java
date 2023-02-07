package uaic.fii.MarvelMonPlay.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uaic.fii.MarvelMonPlay.exceptions.PlayerAlreadyRegisteredException;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;
import uaic.fii.MarvelMonPlay.services.impl.RegistrationService;
import uaic.fii.MarvelMonPlay.utils.RegisterDto;

@RestController
@RequestMapping(path = "/auth")
@AllArgsConstructor
public class PlayerController {

    private RegistrationService registrationService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/success")
    public String loginSuccessfully(){
        return "Login successfully!";
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/logout_success")
    public String logoutSuccessfully(){
        return "Logout successfully!";
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/failure")
    public String loginFailure(){
        return "Login failed!";
    }

    @PostMapping("/signup")
    public String registerUser(@RequestBody RegisterDto registerDto) throws PlayerAlreadyRegisteredException, ResourceNotFoundException {

        return registrationService.register(registerDto);

    }

}
