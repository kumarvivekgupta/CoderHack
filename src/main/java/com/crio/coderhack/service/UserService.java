package com.crio.coderhack.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

import com.crio.coderhack.entity.UserEntity;
import com.crio.coderhack.exception.DuplicateUserException;
import com.crio.coderhack.exception.UserNotFoundException;
import com.crio.coderhack.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import java.util.Optional;
import java.util.Set;

import javax.swing.text.html.parser.Entity;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;


    public List<UserEntity> getUsers(){
        return userRepository.findAllByOrderByScoreAsc();
    }

    public UserEntity getUserById(Long id) throws UserNotFoundException{
        
        return   userRepository.findById(id).orElseThrow(UserNotFoundException::new);  
          
    }

    public UserEntity addUser(UserEntity user){

        List<UserEntity> r=userRepository.findAll();
        long i=r.size();
        ++i;
        user.setId(i);
        user.setScore(0);
        user.setBadge(null);

       return userRepository.save(user);

    }
    public boolean isDuplicate(UserEntity user){
         Optional<UserEntity> dup=userRepository.findByUsername(user.getUsername());

        if(dup.isPresent())return true;

        return false;

    }

    public Optional<UserEntity> updateScore(Long id,Integer score){

        Optional<UserEntity> user=userRepository.findById(id);

        if(user.isPresent()){
            user.get().setScore(score);
             Set<String> badges = user.get().getBadge();
            if (badges == null) {
                badges = new HashSet<>();
            }
             
            if(score>=1 && score<30){
                badges.add("Code Ninja");

            }else if(score>=30 && score<60){
                 badges.add("Code Champ");

            }else{
                 badges.add("Code Master");

            }
            user.get().setBadge(badges);
            userRepository.save(user.get());
            return user;


        }else{
            return Optional.empty();
        }

    }

    public String deleteUser(Long userId){

        if(userRepository.findById(userId).isPresent()){

             userRepository.deleteById(userId);

             return "OK";

        }else{
            return "User Not Found";
        }

       
    }


   


    
}
