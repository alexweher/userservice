package com.example.userservice.service;


import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //Метод для создания пользователя
    public User createUser(User user){
        return userRepository.save(user);
    }

    //Метод ля получения всех пользователей
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    //Метод получения пользователя по ID
    public Optional<User> getUserById(Long id){
        return  userRepository.findById(id);
    }

    //Метод для поиска пользователя по email
    public Optional<User> getUserByEmail(String email){
        return  userRepository.findByEmail(email);
    }
}
