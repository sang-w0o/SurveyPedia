package com.surveypedia.withdrawed.controller;

import com.surveypedia.tools.WriteToClient;
import com.surveypedia.withdrawed.dto.WithdrawedInsertRequestDto;
import com.surveypedia.withdrawed.service.WithdrawedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class WithdrawedApiController {

    private final WithdrawedService withdrawedService;

    @PostMapping("/MemberWithdrawInsert.do")
    public void withdrawInsert(@RequestBody WithdrawedInsertRequestDto requestDto, HttpServletRequest request, HttpServletResponse response) {
        WriteToClient.send(response, withdrawedService.withdrawInsert(requestDto.getPass(), request));
    }
}
