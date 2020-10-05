package com.surveypedia.withdrawed.service;

import com.surveypedia.domain.members.Members;
import com.surveypedia.domain.members.MembersRepository;
import com.surveypedia.domain.withdrawed.Withdrawed;
import com.surveypedia.domain.withdrawed.WithdrawedRepository;
import com.surveypedia.members.exception.MemberLoginCheckException;
import com.surveypedia.members.exception.MemberLoginException;
import com.surveypedia.tools.ObjectMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class WithdrawedService {

    private final WithdrawedRepository withdrawedRepository;
    private final MembersRepository membersRepository;

    public org.json.simple.JSONObject withdrawInsert(String pass, HttpServletRequest request) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        Members member = (Members)request.getSession(false).getAttribute("userInfo");
        try {
            if(member == null) {
                throw new MemberLoginCheckException();
            }
            Members memberCheck = membersRepository.login(member.getEmail(), pass);
            if(memberCheck == null) {
                throw new MemberLoginException();
            }
            Withdrawed withdrawed = new Withdrawed(member.getEmail());
            withdrawedRepository.save(withdrawed);
            jsonObject.put("result", true);
            jsonObject.put("message", "정상적으로 탈퇴 처리 되었습니다.");
            request.getSession(false).invalidate();
        } catch(MemberLoginCheckException exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(exception);
        } catch(MemberLoginException exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(exception);
        }
        catch(Exception exception) {
            exception.printStackTrace();
            jsonObject = ObjectMaker.getJSONObjectWithException(new Exception("알 수 없는 이유로 탈퇴 처리에 실패했습니다."));
        }
        return jsonObject;
    }
}
