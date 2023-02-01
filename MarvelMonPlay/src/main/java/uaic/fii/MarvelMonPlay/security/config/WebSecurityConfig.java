package uaic.fii.MarvelMonPlay.security.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import uaic.fii.MarvelMonPlay.security.CustomAuthenticationProvider;
import uaic.fii.MarvelMonPlay.services.impl.PlayerServiceImpl;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

    private final PlayerServiceImpl playerService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authenticationProvider(authProvider()).formLogin().defaultSuccessUrl("/auth/success").failureUrl("/auth/failure").and()
                .authorizeHttpRequests().requestMatchers("/auth/**").permitAll()
                        .and()
                        .authorizeHttpRequests().anyRequest().authenticated();
        http.csrf().disable();
        return http.build();
    }

    @Bean
    public CustomAuthenticationProvider authProvider() {
        return new CustomAuthenticationProvider();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
