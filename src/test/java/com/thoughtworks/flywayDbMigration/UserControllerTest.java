package com.thoughtworks.flywayDbMigration;

import com.thoughtworks.flywayDbMigration.Exception.UserNotPresentException;
import com.thoughtworks.flywayDbMigration.controller.UserController;
import com.thoughtworks.flywayDbMigration.dto.UserDTO;
import com.thoughtworks.flywayDbMigration.model.User;
import com.thoughtworks.flywayDbMigration.service.UserService;
import org.hamcrest.Matchers;
import org.hibernate.mapping.Any;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;

@RunWith(SpringRunner.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private Long id;

    private ResponseEntity<User> responseEntity;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        MockitoAnnotations.initMocks(this);
        this.id = 1L;

    }

    @Test
    public void givenARequestToGetAllUsers_WhenPresent_ShouldReturnAListOfUser() throws Exception {

        List<User> users = Arrays.asList(new User(1l, "kumud001", "kumud", "garg", "DLF", "TW", "IT", "30000"));
        Mockito.when(userService.getAllUsers()).thenReturn(users);
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userName", Matchers.is("kumud001")));

    }

    @Test
    public void givenAExistedUserId_WhenPresent_ShouldReturnAParticularUser() throws Exception {
        User user = new User(1L, "kumud001", "kumud", "garg", "DLF", "TW", "IT", "30000");
        Mockito.when(userService.getById(1l)).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/" + 1L).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName", Matchers.is("kumud001")));

    }

    @Test
    public void givenAWrongUserId_WhenNotPresent_ShouldThrowCustomException() throws Exception {
        User user = new User(1L, "kumud001", "kumud", "garg", "DLF", "TW", "IT", "30000");
        Mockito.when(userService.getById(2L)).thenThrow(UserNotPresentException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/" + 2L).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void givenANewUser_WhenAdded_ShouldReturn200HttpStatusCode() throws Exception {
        String jsonString = "{\"userName\": \"manish008\", \"firstName\": \"Manish\", \"lastName\": \"singh\", \"address\": \"elf\", \"companyName\": \"tw\",  \"jobType\": \"it\",  \"salary\": \"30000\"}";

        UserDTO userDTO = new UserDTO("manish008", "Manish", "singh", "elf", "tw", "it", "30000");

        Mockito.when(userService.addUser(userDTO)).thenReturn(1l);
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void givenAExistedUserId_WhenEdited_ShouldReturnEditUser() throws Exception {
        String jsonString = "{\"userName\": \"manish008\", \"firstName\": \"Manish\", \"lastName\": \"singh\", \"address\": \"elf\", \"companyName\": \"tw\",  \"jobType\": \"it\",  \"salary\": \"30000\"}";

        UserDTO userDTO = new UserDTO("manish008", "Manish", "singh", "elf", "tw", "it", "30000");
        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAddress(userDTO.getAddress());
        user.setCompanyName(userDTO.getCompanyName());
        user.setJobType(userDTO.getJobType());
        user.setSalary(userDTO.getSalary());


        Mockito.when(userService.editUser(1L,userDTO)).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.put("/users/" + 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Manish")));

    }

    @Test
    public void givenANonExistedUserId_WhenEdited_ShouldThrowCustomException() throws Exception {
        String jsonString = "{\"userName\": \"manish008\", \"firstName\": \"Manish\", \"lastName\": \"singh\", \"address\": \"elf\", \"companyName\": \"tw\",  \"jobType\": \"it\",  \"salary\": \"30000\"}";

        UserDTO userDTO = new UserDTO("manish008", "Manish", "singh", "elf", "tw", "it", "30000");
        User user = new User();
        user.setId(1L);
        user.setUserName(userDTO.getUserName());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAddress(userDTO.getAddress());
        user.setCompanyName(userDTO.getCompanyName());
        user.setJobType(userDTO.getJobType());
        user.setSalary(userDTO.getSalary());


        Mockito.when(userService.editUser(2L,userDTO)).thenThrow(UserNotPresentException.class);
        mockMvc.perform(MockMvcRequestBuilders.put("/users/" + 2L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    public void givenAExistedUserId_WhenPresent_ShouldBeDeleted() throws Exception {
        User user = new User(1L, "kumud001", "kumud", "garg", "DLF", "TW", "IT", "30000");
        Mockito.when(userService.getById(1L)).thenReturn(user);
        doNothing().when(userService).deleteUser(user.getId());
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/" + 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void givenANonExistedUserId_WhenNotPresent_ShouldThrowCustomException() throws Exception {
        User user = new User(1L, "kumud001", "kumud", "garg", "DLF", "TW", "IT", "30000");
        Mockito.when(userService.getById(2L)).thenReturn(null);
        doThrow(UserNotPresentException.class).when(userService).deleteUser(2L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/" + 2L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }











}
