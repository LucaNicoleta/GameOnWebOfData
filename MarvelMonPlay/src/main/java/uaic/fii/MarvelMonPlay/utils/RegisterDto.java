package uaic.fii.MarvelMonPlay.utils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegisterDto {
    private final String username;
    private final String password;
    private final String marvelResIdentifier;
    private final String  RES_IDENTIFIER;
}
