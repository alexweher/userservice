package com.example.userservice.service;


import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.apache.kafka.common.errors.ResourceNotFoundException;
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


    // Метод обновления пользователя
    public User updateUser(Long id, User userDetails) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(()-> new  ResourceNotFoundException("User not found with id: " + id));

        existingUser.setName(userDetails.getName());
        existingUser.setEmail(userDetails.getEmail());

        return userRepository.save(existingUser);
    }

    // удаление пользователя
    public void deleteUser(Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(
                        "User not found with id: " + id));
        userRepository.delete(existingUser);
    }
}
