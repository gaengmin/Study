package com.gaeng.springsecuritytest.controller;

import com.gaeng.springsecuritytest.dto.JoinDto;
import com.gaeng.springsecuritytest.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @GetMapping("/join")
    public String joinP() {

        return "join";
    }


    //회원가입 성공시 로그인 || 실패시 회원가입페이지
    @PostMapping("/joinProc")
    public String joinProcess(JoinDto joinDTO) {

        System.out.println(joinDTO.getUsername());

        joinService.joinProcessService(joinDTO);


        return "redirect:/login";
    }


}
