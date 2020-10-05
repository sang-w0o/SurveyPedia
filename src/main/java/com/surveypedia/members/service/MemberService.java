package com.surveypedia.members.service;

import com.surveypedia.domain.members.Members;
import com.surveypedia.domain.members.MembersRepository;
import com.surveypedia.domain.withdrawed.Withdrawed;
import com.surveypedia.domain.withdrawed.WithdrawedRepository;
import com.surveypedia.members.dto.MemberInsertRequestDto;
import com.surveypedia.members.dto.MemberPassUpdateRequestDto;
import com.surveypedia.members.exception.*;
import com.surveypedia.tools.ObjectMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    public org.json.simple.JSONObject checkEmail(String email) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        try {
            Members member = membersRepository.findByEmail(email);
            if(member != null) throw new MemberEmailAlreadyInUseException();
            jsonObject.put("result", true);
            jsonObject.put("message", "사용 가능한 이메일 입니다.");
        } catch(MemberEmailAlreadyInUseException exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(exception);
        }
        return jsonObject;
    }

    public org.json.simple.JSONObject checkNick(String nick) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        try {
            Members member = membersRepository.findByNick(nick);
            if(member != null) throw new MemberNickAlreadyInUseException();
            jsonObject.put("result", true);
            jsonObject.put("message", "사용 가능한 별명 입니다.");
        } catch(MemberNickAlreadyInUseException exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(exception);
        }
        return jsonObject;
    }
    public org.json.simple.JSONObject signUp(MemberInsertRequestDto requestDto) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        try {
            membersRepository.signUp(requestDto.getEmail(), requestDto.getPass(), requestDto.getNick());
            jsonObject.put("result", true);
            jsonObject.put("message", "회원 가입에 성공했습니다. 로그인 해 주세요!");
        } catch(Exception exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(new Exception("알 수 없는 오류로 회원 가입에 실패했습니다."));
        }
        return jsonObject;
    }

    public org.json.simple.JSONObject getPoint(HttpServletRequest request) {
        Members member = (Members)request.getSession(false).getAttribute("userInfo");
        Integer point = membersRepository.getPoint(member.getEmail());
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        jsonObject.put("result", true);
        jsonObject.put("point", point);
        return jsonObject;
    }
}
