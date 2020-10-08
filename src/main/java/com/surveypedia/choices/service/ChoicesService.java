package com.surveypedia.choices.service;

import com.surveypedia.choices.exception.ChoiceGetException;
import com.surveypedia.choices.exception.ChoiceInsertException;
import com.surveypedia.domain.choices.Choices;
import com.surveypedia.domain.choices.ChoicesRepository;
import com.surveypedia.tools.ObjectMaker;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
                    Choices choice = Choices.builder().scode(s_code)
                            .qnumber(q_number)
                            .choicecontent(choiceListArray.getString(j))
                            .choicenum(j).build();
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

    public org.json.simple.JSONObject getChoices(HttpServletRequest request) {
        int s_code = Integer.parseInt(request.getParameter("s_code"));
        int q_number = Integer.parseInt(request.getParameter("q_number"));
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        org.json.simple.JSONArray jsonArray = ObjectMaker.getSimpleJSONArray();
        try {
            List<Choices> list = choicesRepository.findByScodeAndQnumber(s_code, q_number);
            for (Choices choice : list) {
                org.json.simple.JSONObject jTemp = ObjectMaker.getSimpleJSONObject();
                jTemp.putAll(choice.convertMap());
                jsonArray.add(jTemp);
            }
            jsonObject.put("choiceList", jsonArray);
            jsonObject.put("result", true);
        } catch (Exception exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(new ChoiceGetException());
        }
        return jsonObject;
    }
}
