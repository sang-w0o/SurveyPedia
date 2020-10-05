package com.surveypedia.members.controller;

import com.surveypedia.members.dto.MemberLoginRequestDto;
import com.surveypedia.members.dto.MemberPassUpdateRequestDto;
import com.surveypedia.members.service.MemberService;
import com.surveypedia.tools.WriteToClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class MembersApiController {

    private final MemberService memberService;

    @PostMapping("/Login.do")
    public void login(@RequestBody MemberLoginRequestDto requestDto, HttpServletRequest request, HttpServletResponse response) {
        WriteToClient.send(response, memberService.login(requestDto.getEmail(), requestDto.getPass(), request));
    }

    @PostMapping("/Logout.do")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        WriteToClient.send(response, memberService.logout(request));
    }

    @PutMapping("/MemberPassChange.do")
    public void changePassword(@RequestBody MemberPassUpdateRequestDto requestDto, HttpServletRequest request, HttpServletResponse response) {
        WriteToClient.send(response, memberService.changePassword(requestDto, request));
    }
}
