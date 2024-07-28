package pl.com.itsystems.cookbook.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                request -> request
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/categories/new/**", "/categories/edit/**", "/categories/delete/**").hasRole("ADMIN")
                        .requestMatchers("/categories", "/categories/*").permitAll()
                        .requestMatchers(("/admin-panel/**")).hasRole("ADMIN")
                        .requestMatchers("/recipes", "/recipes/*").permitAll()
                        .requestMatchers("/recipes/edit/*", "/recipes/new/*").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/recipes/delete/*").hasRole("ADMIN")
                        .requestMatchers("/img/*").permitAll()
                        .requestMatchers("/register-confirmation").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/logout-success").permitAll()
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .anyRequest().authenticated()
        );
        http.formLogin(login -> login.loginPage("/login").permitAll());
        http.logout(logout ->
                logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout/**", HttpMethod.GET.name()))
                        .logoutSuccessUrl("/logout-success")
        );
        http.csrf().disable();
        //ustawienie h2-console
        http.headers().frameOptions().sameOrigin();
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
