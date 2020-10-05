package com.surveypedia.members.service;

import com.surveypedia.domain.members.Members;
import com.surveypedia.domain.members.MembersRepository;
import com.surveypedia.domain.withdrawed.Withdrawed;
import com.surveypedia.domain.withdrawed.WithdrawedRepository;
import com.surveypedia.members.dto.MemberPassUpdateRequestDto;
import com.surveypedia.members.exception.MemberException;
import com.surveypedia.members.exception.MemberLoginException;
import com.surveypedia.members.exception.MemberWithdrawLoginException;
import com.surveypedia.tools.ObjectMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MembersRepository membersRepository;
    private final WithdrawedRepository withdrawedRepository;

    @Transactional
    @SuppressWarnings("unchecked")
    public org.json.simple.JSONObject login(String email, String pass, HttpServletRequest request) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        if(email.equals("admin@surveypro.com")) {
            jsonObject.put("errno", 2);
            jsonObject.put("message", "관리자 페이지로 이동합니다.");
            return jsonObject;
        }
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

    public org.json.simple.JSONObject logout(HttpServletRequest request){
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        try {
            HttpSession session = request.getSession(false);
            session.setAttribute("result", false);
            session.invalidate();
            jsonObject.put("errno", 0);
            jsonObject.put("message", "로그아웃이 완료되었습니다.");
        } catch(Exception exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(exception);
        }
        return jsonObject;
    }

    public org.json.simple.JSONObject changePassword(MemberPassUpdateRequestDto requestDto, HttpServletRequest request) {
        Members member = (Members)request.getSession(false).getAttribute("userInfo");
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        try {
            membersRepository.changePass(requestDto.getChangepass(), member.getEmail());
            jsonObject.put("result", true);
            jsonObject.put("message", "비밀번호가 정상 변경되었습니다. 다시 로그인 해 주세요.");
            request.getSession(false).invalidate();
        } catch(Exception exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(exception);
            jsonObject.put("result", false);
        }
        return jsonObject;
    }

}
