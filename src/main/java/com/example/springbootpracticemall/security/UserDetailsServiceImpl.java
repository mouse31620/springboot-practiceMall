package com.example.springbootpracticemall.security;

import com.example.springbootpracticemall.model.entity.User;
import com.example.springbootpracticemall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
        // 轉換成 Spring security指定格式
        return new CustomUserDetails(user);
    }
}
