package com.example.UserService.Service;

import com.example.UserService.DTO.UserRequest;
import com.example.UserService.DTO.UserResponse;
import com.example.UserService.Entity.User;
import com.example.UserService.Enum.Role;
import com.example.UserService.Enum.Status;
import com.example.UserService.Repository.UserRepo;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public User deleteUser(Long userId) {
        User extingUser=userRepo.findById(userId).orElseThrow(()->new RuntimeException("user not found"));
        userRepo.delete(extingUser);
        return extingUser;
    }


    public UserResponse addUser(UserRequest userRequest) {

        if(userRepo.existsByEmail(userRequest.getEmail())){
            throw  new RuntimeException("User is already present");
        }
        User user=new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setRole(Role.USER);
        user.setStatus(Status.ACTIVE);
        user.setCreatedAt(LocalDateTime.now());
        User savedUser=userRepo.save(user);
        return userToResponseDTO(savedUser);
    }
    public List<UserResponse> getAllUser() {
        return userRepo.findAll().stream().map(this::userToResponseDTO).toList();
    }

    public  UserResponse getByUserId(Long userId) {
        User presentUser=userRepo.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        return  userToResponseDTO(presentUser);
    }

    public  UserResponse updateUser(Long userId, UserRequest userRequest) {
        User presentUser=userRepo.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        presentUser.setName(userRequest.getName());
        presentUser.setEmail(userRequest.getEmail());
        presentUser.setPassword(userRequest.getPassword());

        return userToResponseDTO(presentUser) ;
    }

    private UserResponse userToResponseDTO(User savedUser) {
        UserResponse response=new UserResponse();
        response.setId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());
        response.setRole(String.valueOf(savedUser.getRole()));
        response.setStatus(String.valueOf(savedUser.getStatus()));

        return response;
    }


    public boolean isUserActive(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return "ACTIVE".equals(user.getStatus());
    }
}
