package com.surveypedia.members.service;

import com.surveypedia.domain.members.Members;
import com.surveypedia.domain.members.MembersRepository;
import com.surveypedia.domain.withdrawed.Withdrawed;
import com.surveypedia.domain.withdrawed.WithdrawedRepository;
import com.surveypedia.members.exception.MemberException;
import com.surveypedia.members.exception.MemberLoginException;
import com.surveypedia.members.exception.MemberWithdrawLoginException;
import com.surveypedia.tools.ObjectMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MembersRepository membersRepository;
    private final WithdrawedRepository withdrawedRepository;

    @Transactional
    @SuppressWarnings("unchecked")
    public org.json.simple.JSONObject login(String email, String pass, HttpServletRequest request) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        try {
            Withdrawed withdrawed = withdrawedRepository.findByEmail(email);
            if (withdrawed != null) throw new MemberWithdrawLoginException();
            Members member = membersRepository.login(email, pass);
            if(member != null) {
                request.getSession().setAttribute("userInfo", member);
                request.getSession().setAttribute("result", true);
                jsonObject.put("errno", 0);
                jsonObject.put("message", "로그인 성공");
            } else throw new MemberLoginException();
        } catch(MemberException exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(exception);
        }
        return jsonObject;

    }

}
