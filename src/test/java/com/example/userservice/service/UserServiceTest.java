package com.example.userservice.service;

import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);

        user.setEmail("test@example.com");

        user.setName("John Doe");
    }

        @Test
        public void testCreateUser(){
            when(userRepository.save(user))
                    .thenReturn(user);
            User createdUser = userService.createUser(user);

            assertNotNull(createdUser);

            assertEquals("test@example.com", createdUser.getEmail());
        }

        @Test
    public void testGetUserById(){
        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserById(1L);

        assertTrue(foundUser.isPresent());

        assertEquals("John Doe", foundUser.get().getName());

        }

}
