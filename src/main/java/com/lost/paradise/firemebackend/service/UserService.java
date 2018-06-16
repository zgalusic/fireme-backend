package com.lost.paradise.firemebackend.service;

import com.lost.paradise.firemebackend.model.UsersEntity;
import com.lost.paradise.firemebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UsersEntity> findAll(Pageable pageable){
        return userRepository.findAll(pageable).getContent();
    }

}
