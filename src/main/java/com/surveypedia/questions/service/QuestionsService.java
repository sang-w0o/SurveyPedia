package com.surveypedia.questions.service;

import com.surveypedia.domain.questions.Questions;
import com.surveypedia.domain.questions.QuestionsRepository;
import com.surveypedia.questions.exception.QuestionInsertException;
import com.surveypedia.tools.ObjectMaker;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuestionsService {

    private final QuestionsRepository questionsRepository;

    public org.json.simple.JSONObject insert(String request) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        try {
            JSONObject requestObject = new JSONObject(request);
            JSONArray arrayOfQuestionTitles = requestObject.getJSONArray("qList");
            JSONArray arrayOfQuestionTypes = requestObject.getJSONArray("qListType");
            int questionLength = arrayOfQuestionTitles.length();
            for (int i = 0; i < questionLength; i++) {
                Questions question = Questions.builder().q_title(arrayOfQuestionTitles.getString(i))
                        .q_type(arrayOfQuestionTypes.getString(i))
                        .s_code(requestObject.getInt("s_code"))
                        .q_number(i + 1).build();
                questionsRepository.save(question);
            }
            jsonObject.put("result", true);
            jsonObject.put("s_code", requestObject.getInt("s_code"));
        } catch(Exception exception) {
            exception.printStackTrace();
            jsonObject = ObjectMaker.getJSONObjectWithException(new QuestionInsertException());
        }
        return jsonObject;
    }
}
