package com.example.demo_security.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter { //deze staat toe om in te breken op de config van je web security (soort adapter)

    //aanpassen van authentication
    @Override //want deze zit ook in de superclass
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { //deze is voor authentication
        //configure methode heeft aantal "smaken", hangt af van de signature (in dit geval het argument AMB)

        auth.inMemoryAuthentication() //kan aantal varianten hebben
                .withUser("user").password("{noop}password").roles("USER") //{noop} betekent dat password niet ge-encode is
                .and()
                .withUser("admin").password("{noop}password").roles("USER", "ADMIN");
        //later zetten we users in tabel
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { //dit is voor authorisation

        http
                //http instellingen die je wilt doen
                .httpBasic() //basic authentication
                .and()

                .authorizeRequests()
                //onderscheid tussen groepen maken, allemaal paden maken, afhankelijk van applicatie
                .antMatchers("/secure").hasRole("ADMIN") //soort wild card, manier om endpoints te definieeren
                .antMatchers("/users_only").hasRole("USER")
                .antMatchers(HttpMethod.GET, "customers/**").hasRole("USER") //met ** ga je de diepte in, object path + wat eronder zit (bv customers met id)
                .antMatchers("customers/**").hasRole("ADMIN")
                .antMatchers("/all").permitAll()

                //.anyRequest().permitAll() //iedereen mag alles
                .and()

                .cors() //cross origin resource sharing, zodat je tussen websites wat kan doen, wordt voor ons ge-enabled
                .and()

                //deze staan allemaal UIT
                .csrf().disable() //cross side ref token (hacking kwetsbaarheid voorkomen tussen browsers), niet nodig voor RESTful
                .formLogin().disable() //is een automatische login pagina, heb je voor api ook niet nodig
                .sessionManagement() //horen er ook bij. ID voor een session. Maar ook deze staat uit, want hebben we niet nodig (iets met cookies en sessies)
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Stateless, hebben we niet nodig
        ;

    }
}


