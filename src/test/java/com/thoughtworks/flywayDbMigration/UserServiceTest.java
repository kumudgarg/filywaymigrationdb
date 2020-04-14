package com.thoughtworks.flywayDbMigration;

import com.thoughtworks.flywayDbMigration.Exception.UserNotPresentException;
import com.thoughtworks.flywayDbMigration.dto.UserDTO;
import com.thoughtworks.flywayDbMigration.model.User;
import com.thoughtworks.flywayDbMigration.repository.UserRepo;
import com.thoughtworks.flywayDbMigration.service.RabbitMQSender;
import com.thoughtworks.flywayDbMigration.service.UserService;
import org.aspectj.lang.annotation.Before;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    List<User> users;
    
    private User user;
    private UserDTO userDTO;
    private Long id;

    @Mock
    private RabbitMQSender rabbitMQSender;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.user=new User();
        this.userDTO = new UserDTO("kumud001", "kumud", "garg", "DLF", "TW", "IT", "30000");
        this.id=1l;
        this.users = new ArrayList<>();
        this.users.add(new User());
        this.users.add(new User());

    }

    @Test
    void givenARequestToShowAllUsers_shouldReturnAListOfUsers() {
        when(userRepo.findAll()).thenReturn(this.users);
        List<User> users = userService.getAllUsers();
        Assert.assertEquals(2, users.size());
    }

    @Test
    void givenARequestToShowAllUsers_WhenUserListEmpty_shouldThrowCustomException() {
        try {
            List<User> emptyUserList = new ArrayList<>();
            when(userRepo.findAll()).thenReturn(emptyUserList);
            List<User> users = userService.getAllUsers();
        } catch (UserNotPresentException ex) {
            Assert.assertEquals(204, ex.statusCode.value());
        }
    }

    @Test
    void givenANewUser_ShouldGetAddedToTheDb() {
       // user.setId(this.id);
        user.setUserName(userDTO.getUserName());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setJobType(userDTO.getJobType());
        user.setAddress(userDTO.getAddress());
        user.setSalary(userDTO.getSalary());
        user.setCompanyName(userDTO.getCompanyName());
        Long userID = userService.addUser(userDTO);
        verify(userRepo).save(user);
        verify(rabbitMQSender).send(userDTO);

        //System.out.println(userID);
        //Assert.assertEquals(java.util.Optional.of(1L), userID);

    }

    @Test
    void givenARequestToAccessParticularUserInfo_WhenGivesUserId_ShouldReturnUserInfo() {
        User user = new User(1l,"kumud001", "kumud", "garg", "DLF", "TW", "IT", "30000");
        when(userRepo.findById(this.id)).thenReturn(java.util.Optional.of(user));
        User user1 = userService.getById(this.id);
        Assert.assertEquals("kumud",user1.getFirstName());
    }

    @Test
    void givenARequestToAccessParticularUserInfo_WhenNoUserPresent_ShouldThrowCustomException() {
        try {
            User user = new User(1l, "kumud001", "kumud", "garg", "DLF", "TW", "IT", "30000");
            when(userRepo.findById(2l)).thenReturn(null);
            User user1 = userService.getById(2l);
        }
        catch (UserNotPresentException ex){
            Assert.assertEquals(204, ex.statusCode.value());
        }


    }

    @Test
    void givenAExitedUserIDAndUserDto_WhenEdited_ShouldReturnEditedUSerInfo() {
        when(userRepo.findById(id)).thenReturn(java.util.Optional.ofNullable(this.user));
        user.setUserName(userDTO.getUserName());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setJobType(userDTO.getJobType());
        user.setAddress(userDTO.getAddress());
        user.setSalary(userDTO.getSalary());
        user.setCompanyName(userDTO.getCompanyName());
        User user = userService.editUser(id, userDTO);
        verify(userRepo).save(this.user);
        Assert.assertEquals("kumud", user.getFirstName());

    }

    @Test
    void givenAExitedUserIDAndUserDto_WhenNoUserPresent_ShouldThrowCustomException() {
        try {
            User user = new User(1l, "kumud001", "kumud", "garg", "DLF", "TW", "IT", "30000");
            when(userRepo.findById(2l)).thenReturn(null);
            User user1 = userService.editUser(2l, userDTO);
            verify(userRepo.save(user1));
        }
        catch (UserNotPresentException ex){
            Assert.assertEquals(204, ex.statusCode.value());
        }

    }

    @Test
    void GivenAExistedUserId_WhenUserPresent_ShouldDeleteFromDb() {
        User user = new User(1L, "kumud001", "kumud", "garg", "DLF", "TW", "IT", "30000");
        when(userRepo.findById(1L)).thenReturn(java.util.Optional.ofNullable(user));
         userService.deleteUser(1L);
        verify(userRepo).delete(user);
    }

    @Test
    void GivenANotExistedUserId_WhenUserNotPresent_ShouldThrowCustomException() {
        try {
                when(userRepo.findById(1L)).thenReturn(null);
                userService.deleteUser(1L);
        }
        catch (UserNotPresentException ex){
            Assert.assertEquals(204, ex.statusCode.value());
        }
    }
}
