package com.thoughtworks.flywayDbMigration.service;

import com.thoughtworks.flywayDbMigration.Exception.UserNotPresentException;
import com.thoughtworks.flywayDbMigration.dto.UserDTO;
import com.thoughtworks.flywayDbMigration.model.User;
import com.thoughtworks.flywayDbMigration.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

//    @Autowired
//    private RabbitMQSender rabbitMQSender;

    public List<User> getAllUsers() {
        List<User> allUsers = userRepo.findAll();

        return allUsers;
    }

    public User getById(Long id) {
        User user = userRepo.findById(id).get();
        return user;
    }

    public Long addUser(UserDTO userDTO) {
        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAddress(userDTO.getAddress());
        user.setCompanyName(userDTO.getCompanyName());
        user.setJobType(userDTO.getJobType());
        user.setSalary(userDTO.getSalary());
        userRepo.save(user);
        //rabbitMQSender.send(userDTO);
        return user.getId();
    }

    public User editUser(Long id, UserDTO userDTO) {

        Optional<User> user = userRepo.findById(id);
        if (!user.isPresent()) {
                throw new UserNotPresentException("NO User Detalis Present", HttpStatus.NO_CONTENT);
            }
        userRepo.save(user.get());
            return user.get();
        }
}