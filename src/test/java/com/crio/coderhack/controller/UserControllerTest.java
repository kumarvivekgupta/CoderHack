package com.crio.coderhack.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.lang.reflect.Array;
import java.net.URI;
import java.util.List;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriComponentsBuilder;
import org.mockito.quality.Strictness;

import com.crio.coderhack.CoderhackApplication;
import com.crio.coderhack.entity.UserEntity;
import com.crio.coderhack.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

// @SpringBootTest(classes = {CoderhackApplication.class})
// @MockitoSettings(strictness = Strictness.STRICT_STUBS)
// @AutoConfigureMockMvc
// @DirtiesContext
// @ActiveProfiles("test")
// //@WebMvcTest(UserController.class)
// public class UserControllerTest {


//     private ObjectMapper objectMapper;

   
//     private MockMvc mvc;

//   @MockBean
//   private UserService userService;


//   @InjectMocks
//   private UserController userController;

//   @BeforeEach
//   public void setup() {
//     objectMapper = new ObjectMapper();

//     MockitoAnnotations.initMocks(this);

//     mvc = MockMvcBuilders.standaloneSetup(userController).build();
//   }

//   @Test
//   public void getUsersTest() throws Exception {
//     // mocks not required, since validation will fail before that.
//     URI uri = UriComponentsBuilder
//         .fromPath("/users")
//         .build().toUri();

//     assertEquals("/users", uri.toString());
    


//     MockHttpServletResponse response = mvc.perform(
//         get(uri.toString()).accept(APPLICATION_JSON_UTF8)
//     ).andReturn().getResponse();


//     assertEquals(HttpStatus.OK.value(), response.getStatus());
//   }


    
// }

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
     private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setUp() {
        // Mock the userService response
        List<UserEntity> users = List.of(new UserEntity("John"),new UserEntity( "Jane"));
        when(userService.getUsers()).thenReturn(users);
        // when(userService.addUser(new UserEntity("John"))).thenReturn(new UserEntity("John"));
    }

    @Test
    public void testGetUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("John"));
    }

    @Test
    public void testCreateUser() throws Exception {
        String userJson = objectMapper.writeValueAsString(new UserEntity("John"));

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isCreated());
    }
}
