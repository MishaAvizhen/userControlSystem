package com.avizhen.security.impl;


import com.avizhen.entity.User;
import com.avizhen.enums.Status;
import com.avizhen.repository.UserRepository;
import com.avizhen.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null || user.getStatus().equals(Status.INACTIVE)) {
            throw new UsernameNotFoundException("No user present with username: " + username);
        }
        return new CustomUserDetails(user);
    }

}