package com.example.demo_security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter { //deze staat toe om in te breken op de config van je web security (soort adapter)

    private DataSource dataSource;

    @Autowired
    WebSecurityConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //zet hier je password encoder voor je encrypted passwords
    @Bean //bean is het resultaat van de encoder wordt opgeslagen in de library van springboot
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //aanpassen van authentication
    @Override //want deze zit ook in de superclass
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { //deze is voor authentication
        //configure methode heeft aantal "smaken", hangt af van de signature (in dit geval het argument AMB)

//        auth.inMemoryAuthentication() //kan aantal varianten hebben
//                .withUser("user").password("{noop}password").roles("USER") //{noop} betekent dat password niet ge-encode is
//                .and()
//                .withUser("admin").password("{noop}password").roles("USER", "ADMIN");
        //later zetten we users in tabel // staat nu in tabel dmv data.sql in resources en authority en user model

//        auth.jdbcAuthentication().dataSource(dataSource);
        //jdbc geeft aan dat hij gebruik maakt van een database, en springboot weet dan om naar de datasource te kijken, en naar de tabellen te zoeken. bij .inMemoryAuth keek hij enkel naar wat was opgegeven
        //dataSource kent ie nog niet en moet je toevoegen als attribuut en met autowired
        //dit is voor eindopdracht voldoende
        //mag ook iets uitgebreider:
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username=?")
                .authoritiesByUsernameQuery("SELECT username, authority FROM authorities AS a WHERE username=?")
        // in dit geval geef je de tabellen door achter de query, je kan dan zelf iets meer configureren, maar doet hetzelfde. In dit geval eigenlijk overbodig
        ;

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


