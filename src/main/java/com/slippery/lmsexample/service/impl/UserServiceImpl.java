package com.slippery.lmsexample.service.impl;

import com.slippery.lmsexample.dto.UserDto;
import com.slippery.lmsexample.models.User;
import com.slippery.lmsexample.repository.UserRepository;
import com.slippery.lmsexample.service.UsersService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserServiceImpl implements UsersService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder =new BCryptPasswordEncoder(12);
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDto registerUser(User user) {
        UserDto response =new UserDto();
        if(userRepository.findByEmail(user.getEmail())!=null){
            response.setMessage("User with the Email "+user.getEmail()+" already exists");
            response.setStatusCode(409);
            return response;
        }
        if(userRepository.findByUsername(user.getUsername()) !=null){
            response.setMessage("User with the Username "+user.getEmail()+" already exists. please try again with another username");
            response.setStatusCode(409);
            return response;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnrolledOn(LocalDateTime.now());
        user.setCourseList(new ArrayList<>());
        user.setUserModules(new ArrayList<>());
        try{
            userRepository.save(user);
            response.setMessage("New user "+user.getUsername()+" created successfully");
            response.setUser(user);
        } catch (Exception e) {
            response.setMessage(e.getLocalizedMessage());
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public UserDto login(User userDetails) {
        UserDto response =new UserDto();
        String username =userRepository.findByEmail(userDetails.getEmail()).getUsername();
        if(username ==null){
            response.setMessage("No user with the email "+userDetails.getEmail()+" was found");
            response.setStatusCode(404);
            return response;
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,userDetails.getPassword()));
        if(authentication.isAuthenticated()){
            response.setMessage("Logged in successfully");
            response.setStatusCode(200);
        }else{
            response.setStatusCode(401);
            response.setMessage("Error logging you in! please check your login details ");
        }
        return response;
    }

    @Override
    public UserDto deleteUserAccount(Long userId) {
        UserDto response =new UserDto();
        var existingUser =findUserAccountById(userId);
        if(existingUser.getStatusCode() !=200){
            return existingUser;
        }
        userRepository.delete(existingUser.getUser());
        response.setMessage(existingUser.getMessage()+ " deleted successfully");
        response.setStatusCode(203);
        return response;
    }

    @Override
    public UserDto findUserAccountById(Long userId) {
        UserDto response =new UserDto();
        var existingUser =userRepository.findById(userId);
        if(existingUser.isEmpty()){
            response.setStatusCode(404);
            response.setMessage("No user with id"+userId+" was found");
            return response;
        }
        response.setUser(existingUser.get());
        response.setStatusCode(200);
        response.setMessage("User with id "+userId);
        return response;
    }

    @Override
    public UserDto getAllUsers() {
        UserDto response =new UserDto();
        var allUsers =userRepository.findAll();
        if(allUsers.isEmpty()){
            response.setMessage("UserList is empty! No users found at the moment");
            response.setStatusCode(404);
            return response;
        }
        response.setUsers(allUsers);
        response.setStatusCode(200);
        response.setMessage("All users in the system");
        return response;
    }

    @Override
    public UserDto updateUserProfile(Long userId, User updateDetails) {
        UserDto response =new UserDto();
        var existingUser =findUserAccountById(userId);
        if(existingUser.getStatusCode() !=200){
            return existingUser;
        }
        var user =existingUser.getUser();
        user.setPassword(updateDetails.getPassword());
        user.setEmail(updateDetails.getEmail());
        user.setGithubAccount(updateDetails.getGithubAccount());
        user.setLinkedinAccount(updateDetails.getLinkedinAccount());
        user.setUserDescription(updateDetails.getUserDescription());
        userRepository.save(user);
        response.setMessage("User updated");
        response.setStatusCode(200);
        return response;
    }
}
