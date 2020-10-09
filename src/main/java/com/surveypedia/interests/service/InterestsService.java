package com.surveypedia.interests.service;

import com.surveypedia.domain.interests.Interests;
import com.surveypedia.domain.interests.InterestsRepository;
import com.surveypedia.interests.dto.InterestInsertRequestDto;
import com.surveypedia.interests.exception.InterestCheckException;
import com.surveypedia.interests.exception.InterestInsertException;
import com.surveypedia.tools.ObjectMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class InterestsService {

    private final InterestsRepository interestsRepository;

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public org.json.simple.JSONObject interestCheck(HttpServletRequest request) {
        String email = request.getParameter("email");
        int s_code = Integer.parseInt(request.getParameter("s_code"));
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        try {
            Interests interest = interestsRepository.findByEmailAndScode(email, s_code);
            if(interest != null) throw new InterestCheckException();
            jsonObject.put("result", true);
        } catch(InterestCheckException exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(exception);
        }
        return jsonObject;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public org.json.simple.JSONObject insert(InterestInsertRequestDto requestDto) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        try {
            Interests interest = Interests.builder()
                    .email(requestDto.getEmail())
                    .scode(requestDto.getS_code())
                    .build();
            interestsRepository.save(interest);
            jsonObject.put("result", true);
            jsonObject.put("message", "즐겨찾기에 추가되었습니다.");
        } catch(Exception exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(new InterestInsertException());
        }
        return jsonObject;
    }
}
