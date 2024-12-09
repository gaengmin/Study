package com.gaeng.springsecuritytest.service;

import com.gaeng.springsecuritytest.dto.JoinDto;
import com.gaeng.springsecuritytest.entity.UserEntity;
import com.gaeng.springsecuritytest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public void joinProcessService(JoinDto joinDto){

        //DB검증(회원 중복이쓴지 검증)
        boolean isUser = userRepository.existsByUsername(joinDto.getUsername());
        if (isUser) {
            return;
        }

        UserEntity data = new UserEntity();

        data.setUsername(joinDto.getUsername());
        data.setPassword(bCryptPasswordEncoder.encode(joinDto.getPassword())); //비밀번호 인코딩
        data.setRole("ROLE_USER");

        userRepository.save(data);
    }
}
