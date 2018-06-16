package com.lost.paradise.firemebackend.controller;

import com.lost.paradise.firemebackend.model.UsersEntity;
import com.lost.paradise.firemebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<UsersEntity>> findAllUsers(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "3") int size){

        ResponseEntity<List<UsersEntity>> usersEntityListResponse = null;

        try {
            int pageNo = page <= 0 ? 0 : page - 1;

            Pageable pageable = PageRequest.of(pageNo, size);
            List<UsersEntity> usersEntityList = userService.findAll(pageable);

            usersEntityListResponse = new ResponseEntity<>(usersEntityList, HttpStatus.OK);
        } catch (Exception e){
            usersEntityListResponse = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return usersEntityListResponse;
    }

}
