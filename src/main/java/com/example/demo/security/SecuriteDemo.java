package com.example.demo.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.List;

@EnableWebSecurity
public class SecuriteDemo extends WebSecurityConfigurerAdapter {


/*    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

//        auth.inMemoryAuthentication()
//                .withUser("erdinc")
//                .password("root")                     // une façon de s'identifier
//                .roles("USER")
//                .and()
//                .withUser("admin")
//                .password("root")
//                .roles("ADMIN");*/

        @Autowired   // Autowired = injection de dépendances
        private DataSource dataSource;

        @Autowired
        private UserDetailsServiceDemo userDetailsServiceDemo;

        @Autowired
        private JwtUtils jwtUtils;

        @Autowired
        private JwtRequestFilter jwtRequestFilter;

        @Override
        protected  void configure(AuthenticationManagerBuilder auth) throws  Exception {

            //Claims claims = jwtUtils.getTokenBody();

            /*auth.jdbcAuthentication()
                    .dataSource(dataSource)
                    .usersByUsernameQuery(
                            "SELECT nom, mot_de_passe , 1 " +         // 1 signifie un état
                            "FROM utilisateur " +
                            "WHERE nom = ?")
                    .authoritiesByUsernameQuery(
                            "SELECT u.nom, r.nom FROM utilisateur u " +
                                    "JOIN role_utilisateur ru ON u.id = ru.utilisateur_id " +
                                    "JOIN role r ON r.id = ru.role_id " +
                                    "WHERE u.nom = ?");*/

            auth.userDetailsService(userDetailsServiceDemo);
        }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

            http.cors().and().csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/connexion","/inscription").permitAll()
                    .antMatchers("/admin/**").hasRole("ADMIN")          // ** pour dire toutes les requetes qui commence par /admin
                    .antMatchers("/**").hasAnyRole("USER","ADMIN")
                    .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // permet d'extraire le token avant de vérifier si est USER ou ADMIN
    }


    @Bean
    public org.springframework.web.cors.reactive.CorsConfigurationSource configurationCrossOrigin(){
        CorsConfiguration maConfiguration = new CorsConfiguration();
        maConfiguration.setAllowedOrigins(List.of("*"));
        maConfiguration.setAllowedMethods(List.of("HEAD","GET", "POST", "PUT", "DELETE", "PATCH"));
        maConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", maConfiguration);
        return source;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
            return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager getAuthentificationManager() throws Exception {
            return super.authenticationManager();
        }
}
