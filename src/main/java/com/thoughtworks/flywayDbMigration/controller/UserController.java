package com.thoughtworks.flywayDbMigration.controller;

import com.thoughtworks.flywayDbMigration.Exception.UserNotPresentException;
import com.thoughtworks.flywayDbMigration.dto.UserDTO;
import com.thoughtworks.flywayDbMigration.model.User;
import com.thoughtworks.flywayDbMigration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUSer(){
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id){
        try {
            User user = userService.getById(id);
            return  new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (UserNotPresentException ex){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping
    public ResponseEntity<Long> addUser(@RequestBody UserDTO userDTO){
        Long id = userService.addUser(userDTO);
        return ResponseEntity.ok(id);

    }

    @PutMapping("/{id}")
    public ResponseEntity<User> editUser(@PathVariable Long id, @RequestBody UserDTO userDTO){
        try {
            User user = userService.editUser(id, userDTO);
            return ResponseEntity.ok(user);
        }
        catch (UserNotPresentException ex){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){

        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User Deleted Successfully");
        }
        catch (UserNotPresentException ex){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }


}
