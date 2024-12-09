package com.gaeng.springsecuritytest.service;

import com.gaeng.springsecuritytest.dto.CustomUserDetails;
import com.gaeng.springsecuritytest.entity.UserEntity;
import com.gaeng.springsecuritytest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userData = userRepository.findByUsername(username);

        if (userData == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없음" + username);

        }
        return new CustomUserDetails(userData);

    }
}
