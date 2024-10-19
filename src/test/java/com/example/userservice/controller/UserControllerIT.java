package com.example.userservice.controller;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testCreateUser() throws Exception {
        // JSON с данными пользователя, которого мы хотим создать
        String userJson = "{\"email\":" +
                " \"test@example.com\"," +
                " \"name\":" +
                " \"John Doe\"}";

        //Выполнение Post запроса для создания пользователя и проверка ответа
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())  // Проверка, что статус ответа 201 (Created)
                .andExpect(jsonPath("$.email").value("test@example.com"))  // Проверка содержимого ответа
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testGetUserById() throws Exception {
        // Создаем нового пользователя
        String userJson = "{\"email\": \"search@example.com\", \"name\": \"Jane Doe\"}";

        MvcResult result = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andReturn();

        // Получаем ID созданного пользователя
        String response = result.getResponse().getContentAsString();
        Long userId = JsonPath.parse(response).read("$.id", Long.class);

        // Ищем пользователя по ID
        mockMvc.perform(get("/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("search@example.com"))
                .andExpect(jsonPath("$.name").value("Jane Doe"));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        // Выполняем GET-запрос для получения всех пользователей
        // Сначала создаём пользователя
        String userJson = "{\"email\": \"test@example.com\", \"name\": \"John Doe\"}";
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated());

        // Теперь тестируем получение всех пользователей
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())  // Проверяем, что запрос успешен
                .andExpect(jsonPath("$").isArray())  // Проверяем, что ответ — это массив
                .andExpect(jsonPath("$[0].email").value("test@example.com"));  // Проверяем, что email первого пользователя совпадает
    }
}