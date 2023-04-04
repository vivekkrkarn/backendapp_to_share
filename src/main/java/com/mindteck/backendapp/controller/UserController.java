package com.mindteck.backendapp.controller;

import com.mindteck.backendapp.dto.UserDto;
import com.mindteck.backendapp.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    UserServiceImpl userService;

    @GetMapping("/getall")
    public ResponseEntity<?> getAllUsers() {

        try {
            List<UserDto> allUsers = userService.getAllUsers();
            if (allUsers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(allUsers, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("exception in UserController, getAllUsers() method, error message is = {} ", e.getMessage());
        }
        return null;
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id) {

        try {
            UserDto userById = userService.getUserById(id);
            if (userById == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(userById, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("exception in UserController, getById() method, error message is = {} ", e.getMessage());
        }
        return null;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {

        try {
            UserDto user = userService.createUser(userDto);

            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("exception in UserController, createUser() method, error message is = {} ", e.getMessage());
        }
        return null;
    }



    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDto) {
        try {
            UserDto user = userService.updateUser(userDto);

            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("exception in UserController, updateUser() method, error message is = {} ", e.getMessage());
        }
        return null;
    }





    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        String result = userService.deleteUser(id);

        try {
            if (result == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else if (result.equals("DELETE FAILED")) {
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
            else if (result.equals("DELETE SUCCESS")){
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("exception in UserController, deleteUser() method, error message is = {} ", e.getMessage());
        }
        return null;
    }


}
