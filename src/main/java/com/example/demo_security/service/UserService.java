package com.example.demo_security.service;

import com.example.demo_security.model.User;
import com.example.demo_security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    //vrij om te kiezen wat voor service je wilt doen, welke methodes (getUser, deleteUser, updateUser etc)
    //als je geen interface hebt, hoef je ook niet te overriden

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Autowired //met autowired koppelen we nu de passwordencoder aan een attribuut (in dit geval de createUser hier onder), zodat wanneer een user wordt gecreeerd het opgegven password automatisch ook wordt ge-encrypt. Dit kan je ook eventueel in de constructor methode bovenaan doen (onder userRepository), dan hoef je niet twee keer @Autowired te gebruiken (zie les 5, 2.15.46)
    PasswordEncoder passwordEncoder;

    public String createUser(User user) {
        String password = user.getPassword(); //password ophalen
        String encrypted = passwordEncoder.encode(password); //encrypted op plek zetten van het opgegeven password
        user.setPassword(encrypted); //weer op de plek neerzetten

        User newUser = userRepository.save(user);
        return newUser.getUsername();
    }

}
