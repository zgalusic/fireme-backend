package com.lost.paradise.firemebackend.service;

import com.lost.paradise.firemebackend.exception.NotFoundException;
import com.lost.paradise.firemebackend.model.UsersEntity;
import com.lost.paradise.firemebackend.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Page<UsersEntity> findAll(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    public Optional<UsersEntity> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public Optional<UsersEntity> findByEmail(String email){
        return userRepository.findByeMail(email);
    }

    public Optional<UsersEntity> findById(Integer id){
        return userRepository.findById(id);
    }

    public UsersEntity createUser(UsersEntity usersEntity){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        usersEntity.setLastUpdate(timestamp);
        usersEntity.setCreated(timestamp);

        return userRepository.save(usersEntity);
    }

    public UsersEntity updateUser(UsersEntity usersEntity) throws NotFoundException{
        Optional<UsersEntity> optionalUsersEntity = userRepository.findById(usersEntity.getId());

        UsersEntity currentUser = optionalUsersEntity.orElseThrow(()->new NotFoundException("User with id: " + usersEntity.getId() + " was not found"));
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        currentUser.setLastUpdate(timestamp);
        BeanUtils.copyProperties(usersEntity, currentUser);

        return userRepository.save(usersEntity);
    }

    public void deleteUser(int id){
        userRepository.deleteById(id);
    }

    public long getUserCount(){
        return userRepository.count();
    }


}
