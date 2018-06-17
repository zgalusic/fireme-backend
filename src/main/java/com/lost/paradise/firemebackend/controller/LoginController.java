package com.lost.paradise.firemebackend.controller;

import com.lost.paradise.firemebackend.model.UsersEntity;
import com.lost.paradise.firemebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<UsersEntity> login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password){

        ResponseEntity<UsersEntity> usersEntityResponse = null;

        try{
            Optional<UsersEntity> optionalUsersEntity = userService.findByEmail(username);

            UsersEntity usersEntity = optionalUsersEntity.orElse(new UsersEntity());

            usersEntityResponse = usersEntity.getId() != 0 ? new ResponseEntity<>(usersEntity, HttpStatus.OK) : new ResponseEntity<>(usersEntity, HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            usersEntityResponse = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return usersEntityResponse;
    }

}
