package com.surveypedia.questions.service;

import com.surveypedia.domain.questions.Questions;
import com.surveypedia.domain.questions.QuestionsRepository;
import com.surveypedia.questions.exception.QuestionGetException;
import com.surveypedia.questions.exception.QuestionInsertException;
import com.surveypedia.tools.ObjectMaker;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionsService {

    private final QuestionsRepository questionsRepository;

    @Transactional
    @SuppressWarnings("unchecked")
    public org.json.simple.JSONObject insert(String request) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        try {
            JSONObject requestObject = new JSONObject(request);
            JSONArray arrayOfQuestionTitles = requestObject.getJSONArray("qList");
            JSONArray arrayOfQuestionTypes = requestObject.getJSONArray("qListType");
            int questionLength = arrayOfQuestionTitles.length();
            for (int i = 0; i < questionLength; i++) {
                Questions question = Questions.builder().qtitle(arrayOfQuestionTitles.getString(i))
                        .qtype(arrayOfQuestionTypes.getString(i))
                        .scode(requestObject.getInt("s_code"))
                        .qnumber(i + 1).build();
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

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public org.json.simple.JSONObject getQuestionListByS_code(HttpServletRequest request) {
        int s_code = Integer.parseInt(request.getParameter("s_code"));
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        org.json.simple.JSONArray jsonArray = ObjectMaker.getSimpleJSONArray();
        try {
            List<Questions> list = questionsRepository.findByScode(s_code);
            for (Questions question : list) {
                org.json.simple.JSONObject jTemp = ObjectMaker.getSimpleJSONObject();
                jTemp.putAll(question.convertMap());
                jsonArray.add(jTemp);
            }
            jsonObject.put("questions", jsonArray);
            jsonObject.put("result", true);
        } catch(Exception exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(new QuestionGetException());
        }
        return jsonObject;
    }
}
