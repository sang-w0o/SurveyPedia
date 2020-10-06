package com.surveypedia.choices.service;

import com.surveypedia.choices.exception.ChoiceInsertException;
import com.surveypedia.domain.choices.Choices;
import com.surveypedia.domain.choices.ChoicesRepository;
import com.surveypedia.tools.ObjectMaker;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChoicesService {

    private final ChoicesRepository choicesRepository;

    public org.json.simple.JSONObject insert(String request) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        try {
            JSONObject requestObject = new JSONObject(request);
            JSONArray arrayOfChoices = requestObject.getJSONArray("choiceList");
            int s_code = requestObject.getInt("s_code");
            for(int i = 0; i < arrayOfChoices.length(); i++) {
                JSONArray choiceListArray = (JSONArray)arrayOfChoices.get(i);
                int q_number = choiceListArray.getInt(0);
                for(int j = 1; j < choiceListArray.length(); j++) {
                    Choices choice = Choices.builder().s_code(s_code)
                            .q_number(q_number)
                            .choice_content(choiceListArray.getString(j))
                            .choice_num(j).build();
                    choicesRepository.save(choice);
                }
                jsonObject.put("result", true);
            }
        } catch(Exception exception) {
            exception.printStackTrace();
            jsonObject = ObjectMaker.getJSONObjectWithException(new ChoiceInsertException());
        }
        return jsonObject;
    }
}
