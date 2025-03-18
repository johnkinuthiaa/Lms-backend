package com.slippery.lmsexample.controller;

import com.slippery.lmsexample.dto.UserDto;
import com.slippery.lmsexample.models.User;
import com.slippery.lmsexample.service.UsersService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody User user) {
        var registeredUser =usersService.registerUser(user);
        return ResponseEntity.status(HttpStatusCode.valueOf(registeredUser.getStatusCode())).body(registeredUser);
    }
    @PutMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody User userDetails) {
        var loggedInUser =usersService.login(userDetails);
        return ResponseEntity.status(HttpStatusCode.valueOf(loggedInUser.getStatusCode())).body(loggedInUser);
    }
    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<UserDto> deleteUserAccount(@PathVariable Long userId) {
        var deleteAccount =usersService.deleteUserAccount(userId);
        return ResponseEntity.status(HttpStatusCode.valueOf(deleteAccount.getStatusCode())).body(deleteAccount);
    }
    @GetMapping("/{userId}/get")
    public ResponseEntity<UserDto> findUserAccountById(@PathVariable Long userId) {
        var account =usersService.findUserAccountById(userId);
        return ResponseEntity.status(HttpStatusCode.valueOf(account.getStatusCode())).body(account);
    }
    @GetMapping("/all")
    public ResponseEntity<UserDto> getAllUsers() {
        var allUsers =usersService.getAllUsers();
        return ResponseEntity.status(HttpStatusCode.valueOf(allUsers.getStatusCode())).body(allUsers);
    }
    @PutMapping("/{userId}/update")
    public ResponseEntity<UserDto> updateUserProfile(@PathVariable Long userId,@RequestBody User updateDetails) {
        var updatedUser =usersService.updateUserProfile(userId, updateDetails);
        return ResponseEntity.status(HttpStatusCode.valueOf(updatedUser.getStatusCode())).body(updatedUser);
    }
}
