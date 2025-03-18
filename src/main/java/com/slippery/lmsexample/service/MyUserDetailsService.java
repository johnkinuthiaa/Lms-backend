package com.slippery.lmsexample.service;

import com.slippery.lmsexample.models.User;
import com.slippery.lmsexample.models.UserPrincipal;
import com.slippery.lmsexample.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User existingUser =userRepository.findByUsername(username);
        if(existingUser ==null){
            throw new UsernameNotFoundException("User not found");
        }
        return new UserPrincipal(existingUser);
    }
}
