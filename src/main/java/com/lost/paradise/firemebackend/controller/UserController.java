package com.lost.paradise.firemebackend.controller;

import com.lost.paradise.firemebackend.model.UsersEntity;
import com.lost.paradise.firemebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<Page<UsersEntity>> findAllUsers(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size){

        ResponseEntity<Page<UsersEntity>> usersEntityListResponse = null;

        try {
            int pageNo = page <= 0 ? 0 : page - 1;

            Pageable pageable = PageRequest.of(pageNo, size);
            Page<UsersEntity> usersEntityList = userService.findAll(pageable);

            usersEntityListResponse = new ResponseEntity<>(usersEntityList, HttpStatus.OK);
        } catch (Exception e){
            usersEntityListResponse = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return usersEntityListResponse;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<UsersEntity> createUser(@RequestBody UsersEntity usersEntity){

        ResponseEntity<UsersEntity> usersEntityResponse = null;

        try{

            UsersEntity createdUsersEntity = userService.createUser(usersEntity);

            usersEntityResponse = new ResponseEntity<>(createdUsersEntity, HttpStatus.CREATED);

        } catch(Exception e){
            usersEntityResponse = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return usersEntityResponse;
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<UsersEntity> findUserById(@PathVariable("id") int id){

        ResponseEntity<UsersEntity> usersEntityResponse = null;

        try{

            Optional<UsersEntity> usersEntity = userService.findById(id);

            usersEntityResponse = usersEntity.isPresent() ? new ResponseEntity<>(usersEntity.get(), HttpStatus.OK) : new ResponseEntity<>(new UsersEntity(), HttpStatus.NOT_FOUND);

        } catch(Exception e){
            usersEntityResponse = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return usersEntityResponse;
    }


    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public ResponseEntity<UsersEntity> updateUser(@PathVariable("id") int id, @RequestBody UsersEntity usersEntity){

        ResponseEntity<UsersEntity> usersEntityResponse = null;

        try{

            UsersEntity updatedUsersEntity = userService.updateUser(usersEntity);

            usersEntityResponse = new ResponseEntity<>(updatedUsersEntity, HttpStatus.OK);

        } catch(Exception e){
            usersEntityResponse = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return usersEntityResponse;
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<UsersEntity> deleteUser(@PathVariable("id") int id){

        ResponseEntity<UsersEntity> usersEntityResponse = null;

        try{

            userService.deleteUser(id);

            usersEntityResponse = new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch(Exception e){
            usersEntityResponse = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return usersEntityResponse;
    }

    @RequestMapping(value = "/users/count", method = RequestMethod.GET)
    public ResponseEntity<Long> userCount(){

        ResponseEntity<Long> responseEntity = null;

        try{

            Long userCount = userService.getUserCount();

            responseEntity = new ResponseEntity<>(userCount, HttpStatus.OK);

        } catch(Exception e){
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

}
