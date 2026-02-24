package com.example.UserService.Controller;

import com.example.UserService.DTO.UserRequest;
import com.example.UserService.DTO.UserResponse;
import com.example.UserService.Entity.User;
import com.example.UserService.Service.UserService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.hibernate.annotations.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/UserService")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getmesg")
    public String getMessage(){
        return "Welcome to jaldi App";
    }


    @PostMapping("/Adduser")
    public ResponseEntity<UserResponse> addUser(@Valid @RequestBody UserRequest userRequest){
        return ResponseEntity.ok(userService.addUser(userRequest));
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<List<UserResponse>>getAllUser(){
        return ResponseEntity.ok(userService.getAllUser());
    }

    @DeleteMapping("/delete-{userId}")
    public  ResponseEntity<User>deleteUser(@PathVariable Long userId){
        User deletedUser = userService.deleteUser(userId);
        return  ResponseEntity.ok(deletedUser);
    }
     @GetMapping("/getbyid-{userId}")
    public ResponseEntity<UserResponse>getByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getByUserId(userId));
    }

    @PutMapping("/updateUser-{userId}")
    public ResponseEntity<UserResponse>updateUser(@PathVariable Long userId
            ,@RequestBody UserRequest userRequest){
        return ResponseEntity.ok(userService.updateUser(userId,userRequest));
    }

    @GetMapping("/internal/validate/{id}")
    public boolean validateUser(@PathVariable Long id) {
        return userService.isUserActive(id);
    }
}
