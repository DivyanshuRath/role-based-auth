package com.deloitte.myproject.controller;

import com.deloitte.myproject.model.User;
import com.deloitte.myproject.service.UserService;
import com.deloitte.myproject.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.deloitte.myproject.util.UserTestUtil.createUser;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User user = new User();

    private List<User> userList = new ArrayList<>();

    @BeforeEach
    public void init() {
        user = createUser(1L,"name1");
        userList.add(createUser(2L,"name2"));
        userList.add(createUser(3L,"name3"));
    }


    @Test
    public void getUserTest() throws Exception {

        when(userService.getUser())
        .thenReturn(userList);

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk());

        Assertions.assertEquals(2,userList.size());
    }

    @Test
    public void getUserByIdTest() throws Exception {

        when(userService.getUserById(any(Long.class)))
                .thenReturn(user);

        this.mockMvc.perform(get("/user/{id}",1L)).andExpectAll(
                status().isOk()
        );

        Assertions.assertNotNull(user);
    }

    @Test
    void saveUserTestSuccess() throws Exception {
        when(userService.saveUser(any(User.class)))
        .thenReturn(user);

        mockMvc.perform(post("/user")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(user)))
        .andExpectAll(status().isOk());

        Assertions.assertNotNull(user);
        }

    @Test
    public void updateUserTest() throws Exception {

        when(userService.updateUser(any(Long.class), any(User.class)))
                .thenReturn(user);

        mockMvc.perform(get("/user/{id}",1L)
            .content(objectMapper.writeValueAsString(user)))
            .andExpectAll(status().isOk());

        Assertions.assertNotNull(user);
    }

    @Test
    public void deleteUserTest() throws Exception {
        doNothing()
                .when(userService).deleteUserById(any(Long.class));
        mockMvc.perform(get("/user/{id}",1L))
        .andExpectAll(status().isOk());
        }
    @Test
    public void deleteUserTestFailure()throws Exception{

        doThrow(new ResourceNotFoundException("User Not Found"))
        .when(userService).deleteUserById(any(Long.class));
        mockMvc.perform(delete("/user/{id}",1L))
        .andExpectAll(status().is4xxClientError());
        }

    @Test
    public void updateUserTestFailure()throws Exception{

        doThrow(new ResourceNotFoundException("User Not Found"))
        .when(userService).updateUser(1L,user);
        mockMvc.perform(put("/user/{id}",1L))
        .andExpectAll(status().is4xxClientError());
        }

    @Test
    public void getUserByIdTestFailure()throws Exception{

        doThrow(new ResourceNotFoundException("User Not Found"))
        .when(userService).getUserById(1L);
        mockMvc.perform(get("/user/{id}",1L))
        .andExpectAll(status().is4xxClientError());
        }

}