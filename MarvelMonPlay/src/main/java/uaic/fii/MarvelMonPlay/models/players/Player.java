package uaic.fii.MarvelMonPlay.models.players;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uaic.fii.MarvelMonPlay.managers.PasswordManager;
import uaic.fii.MarvelMonPlay.models.characters.Marvel;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Player implements UserDetails {
    public String RES_IDENTIFIER;
    private String username;
    private String password;
    private Marvel marvelCharacter;
    private int level;
    private AppUserRole appUserRole;

    public Player(String RES_IDENTIFIER, String username, String password, Marvel marvelCharacter, int level, AppUserRole appUserRole) {
        this.username = username;
        this.password = password;
        this.marvelCharacter = marvelCharacter;
        this.level = level;
        this.appUserRole = appUserRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Marvel getMarvelCharacter() {
        return marvelCharacter;
    }

    public void setMarvelCharacter(Marvel marvelCharacter) {
        this.marvelCharacter = marvelCharacter;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
