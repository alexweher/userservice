package com.example.userservice.controller;


import com.example.userservice.model.User;
import com.example.userservice.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {


    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserService userService;

    //создание пользователя
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        logger.info("Creating a new user: {}", user);
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(201).body(createdUser);
    }

    //Получение всех пользователей
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        logger.info("Fetching all users");
        List<User> users= userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    //Получение пользователя по Id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        logger.info("Fetching user with ID: {}", id);
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(()->{
                    logger.warn("User with ID: {} not found", id);
                     return ResponseEntity.notFound().build();
                });
    }

    //Получение пользователя по Email
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email){
        logger.info("Fetching user with email: {}", email);
        Optional<User> user = userService.getUserByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElseGet(()-> {
                    logger.warn("User with email: {} not found", email);
                    return ResponseEntity.notFound().build();
                });
    }

    // Обновление пользователя
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody User userDetails, BindingResult result) {
        if (result.hasErrors()) {
            logger.error("Validation errors while updating user with ID: {}", id);
            // Возвращение ответа с ошибками валидации
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        logger.info("Updating user with ID: {}", id);
        User updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    // удаление пользователя
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        logger.info("Deleting user with ID: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}