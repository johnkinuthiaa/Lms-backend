package com.slippery.lmsexample.service;

import com.slippery.lmsexample.dto.UserDto;
import com.slippery.lmsexample.models.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UsersService {
    UserDto registerUser(User user);
    UserDto login(User userDetails);
    UserDto deleteUserAccount(Long userId);
    UserDto findUserAccountById(Long userId);
    UserDto getAllUsers();
    UserDto updateUserProfile(Long userId,User updateDetails);
    UserDto uploadProfilePhoto(Long userId,MultipartFile image) throws IOException;
}
