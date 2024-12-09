package com.gaeng.springbootjwt.controller;

import com.gaeng.springbootjwt.dto.JoinDTO;
import com.gaeng.springbootjwt.service.JoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public String joinProcess(JoinDTO joinDTO) {

        log.info(joinDTO.getUsername());
        joinService.joinProcess(joinDTO);

        return "ok";
    }
}
