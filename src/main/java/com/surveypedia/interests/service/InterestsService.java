package com.surveypedia.interests.service;

import com.surveypedia.domain.interests.Interests;
import com.surveypedia.domain.interests.InterestsRepository;
import com.surveypedia.interests.exception.InterestCheckException;
import com.surveypedia.tools.ObjectMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class InterestsService {

    private final InterestsRepository interestsRepository;

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
}
