package com.example.demochat.config;

import com.example.demochat.entity.Users;
import com.example.demochat.repository.UserDetailsRepository;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.time.LocalDateTime;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "login**", "/js/**", "/error**").permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and()
                .csrf().disable();
    }

    @Bean
    public PrincipalExtractor principalExtractor (UserDetailsRepository userDetailsRepository){
        return map -> {
            String id = (String) map.get("sub");
            Users users = userDetailsRepository.findById(id).orElseGet(() -> {
                Users newUsers = new Users();

                newUsers.setId(id);
                newUsers.setName((String) map.get("name"));
                newUsers.setEmail((String) map.get("email"));
                newUsers.setGender((String) map.get("gender"));
                newUsers.setLocale((String) map.get("locale"));
                newUsers.setUserpic((String) map.get("picture"));

                return newUsers;
            });
            users.setLastVisit(LocalDateTime.now());

            return userDetailsRepository.save(users);
        };
    }
}
