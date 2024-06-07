package com.crio.coderhack.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.crio.coderhack.entity.UserEntity;
import com.crio.coderhack.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping("/users")
    public List<UserEntity> getMemes(){

      return userService.getUsers();  

    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserEntity> getUser(@PathVariable("userId") Long userId){

       UserEntity res= userService.getUserById(userId);

        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    @PostMapping("/users")
    @Validated
    public ResponseEntity<?>  addUser(@RequestBody UserEntity userDto){
        UserEntity savedUser;

        if(userService.isDuplicate(userDto)){
           
               return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Duplicate userId found");

        }else{
              savedUser= userService.addUser(userDto);

        }

        return ResponseEntity.status(HttpStatus.CREATED)
                    .body(savedUser);

    }


    @PutMapping("/users/{userId}")
    public ResponseEntity<?> updateScore(
        @PathVariable("userId") Long userId,
        @RequestBody UserEntity user){

             if (user.getScore() == null || user.getScore() < 1 || user.getScore() > 100) {
            return ResponseEntity.badRequest().body("Score must be between 1 and 100");
            }



             Optional<UserEntity> updatedUser = userService.updateScore(userId, user.getScore());

             if(updatedUser.isPresent()){

                return ResponseEntity.status(HttpStatus.OK).body(updatedUser.get());

             }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
             }
        }


    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Long userId){

        String msg=userService.deleteUser(userId);
        if("OK".equals(msg)){
            return ResponseEntity.status(HttpStatus.OK).body(msg);

        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);

        }


    }






    
}
