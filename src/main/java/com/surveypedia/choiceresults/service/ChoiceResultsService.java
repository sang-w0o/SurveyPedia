package com.surveypedia.choiceresults.service;

import com.surveypedia.choiceresults.exception.ChoiceResultInsertException;
import com.surveypedia.domain.choiceresults.ChoiceResults;
import com.surveypedia.domain.choiceresults.ChoiceResultsRepository;
import com.surveypedia.tools.ObjectMaker;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChoiceResultsService {

    private final ChoiceResultsRepository choiceResultsRepository;

    public org.json.simple.JSONObject insert(String request) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        try {
            JSONObject requestObject = new JSONObject(request);
            String respondent = requestObject.getString("respondent");
            JSONArray arrayOfQNumbers = requestObject.getJSONArray("q_numbers");
            JSONArray arrayOfChoices = requestObject.getJSONArray("choices");
            int s_code = requestObject.getInt("s_code");
            for (int i = 0; i < arrayOfQNumbers.length(); i++) {
                int currentQNumber = arrayOfQNumbers.getInt(i);
                int currentChoice = arrayOfChoices.getInt(i);
                ChoiceResults result = ChoiceResults.builder()
                        .scode(s_code)
                        .qnumber(currentQNumber)
                        .choicenum(currentChoice)
                        .respondent(respondent).build();
                choiceResultsRepository.save(result);
                jsonObject.put("result", true);
            }
        } catch(Exception exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(new ChoiceResultInsertException());
        }
        return jsonObject;
    }
}
