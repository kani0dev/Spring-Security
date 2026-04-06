package kani.springsecurity.Infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http){
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity request){
        return request
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(http -> http
                               // .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                                //.requestMatchers(HttpMethod.GET).permitAll()
                        //  permiti posts em user para criar usuarios nao é boa pratica e deve ser refatora para logica de login e singup
                                //.requestMatchers(HttpMethod.POST, "/users/").permitAll()
                                .anyRequest().permitAll()
                        )
                .httpBasic(Customizer.withDefaults())
                .build();
    }
    // TODO: Implemente the sha256 algotithm for incryptation.
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
