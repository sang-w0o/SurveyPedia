package com.surveypedia.subjectiveresults.service;

import com.surveypedia.domain.subjectiveresults.SubjectiveResults;
import com.surveypedia.domain.subjectiveresults.SubjectiveResultsRepository;
import com.surveypedia.subjectiveresults.exception.SubjectiveResultInsertException;
import com.surveypedia.tools.ObjectMaker;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SubjectiveResultsService {

    private final SubjectiveResultsRepository subjectiveResultsRepository;

    @Transactional
    @SuppressWarnings("unchecked")
    public org.json.simple.JSONObject insert(String request) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        try {
            JSONObject requestObject = new JSONObject(request);
            JSONArray arrayOfAnswers = requestObject.getJSONArray("answers");
            JSONArray arrayOfQNumbers = requestObject.getJSONArray("q_numbers");
            String respondent = requestObject.getString("respondent");
            int s_code = requestObject.getInt("s_code");
            for(int i = 0; i < arrayOfQNumbers.length(); i++) {
                String currentAnswer = arrayOfAnswers.getString(i);
                int currentQNumber = arrayOfQNumbers.getInt(i);
                SubjectiveResults result = SubjectiveResults.builder()
                        .scode(s_code)
                        .qnumber(currentQNumber)
                        .answer(currentAnswer)
                        .respondent(respondent).build();
                subjectiveResultsRepository.save(result);
                jsonObject.put("result", true);
            }
        } catch(Exception exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(new SubjectiveResultInsertException());
        }
        return jsonObject;
    }
}
