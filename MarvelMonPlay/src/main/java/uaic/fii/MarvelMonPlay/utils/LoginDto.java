package uaic.fii.MarvelMonPlay.utils;

import lombok.Data;

@Data
public class LoginDto {
    private String usernameOrEmail;
    private String password;
}
