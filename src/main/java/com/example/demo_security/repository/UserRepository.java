package com.example.demo_security.repository;

import com.example.demo_security.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> { //repository hoort bij User klasse in model

}
