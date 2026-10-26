package oit.is.z3428.kaizi.janken.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class JankenAuthConfiguration {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {

    UserDetails user1 = User.withUsername("user1")
        .password(passwordEncoder.encode("isdev"))
        .roles("USER")
        .build();

    UserDetails user2 = User.withUsername("user2")
        .password(passwordEncoder.encode("isdev"))
        .roles("USER")
        .build();

    return new InMemoryUserDetailsManager(user1, user2);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/janken/**").authenticated()
            .anyRequest().permitAll())
        .formLogin(form -> form.permitAll())
        .logout(logout -> logout.logoutSuccessUrl("/"));

    return http.build();
  }
}
