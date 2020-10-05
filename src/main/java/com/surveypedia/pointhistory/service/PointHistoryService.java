package com.surveypedia.pointhistory.service;

import com.surveypedia.domain.members.Members;
import com.surveypedia.domain.members.MembersRepository;
import com.surveypedia.domain.pointhistory.PointHistory;
import com.surveypedia.domain.pointhistory.PointHistoryRepository;
import com.surveypedia.domain.surveys.SurveysRepository;
import com.surveypedia.members.exception.MemberLoginCheckException;
import com.surveypedia.pointhistory.dto.PointHistoryListDto;
import com.surveypedia.surveys.dto.SurveyHistoryInfoDto;
import com.surveypedia.tools.ObjectMaker;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PointHistoryService {

    private final PointHistoryRepository pointHistoryRepository;
    private final SurveysRepository surveysRepository;
    private final MembersRepository membersRepository;

    @SuppressWarnings("unchecked")
    public org.json.simple.JSONObject getPointHistoryList(HttpServletRequest request) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        HashMap<String, String> pointHistoryMap = null;
        ArrayList<HashMap<String, String>> finalList = new ArrayList<>();
        Members member = (Members)request.getSession(false).getAttribute("userInfo");
        String email = member.getEmail();
        List<PointHistory> list = pointHistoryRepository.findByEmail(email);
        for(PointHistory pointHistory : list) {
            pointHistoryMap = new HashMap<>();
            SurveyHistoryInfoDto infoDto = surveysRepository.getSurveyInfoByS_code(pointHistory.getS_code()).stream().map(SurveyHistoryInfoDto::new).collect(Collectors.toList()).get(0);
            pointHistoryMap.put("email", email);
            pointHistoryMap.put("s_title", infoDto.getS_title());
            pointHistoryMap.put("ph_type", pointHistory.getPh_type());
            pointHistoryMap.put("pointchange", pointHistory.getPointchange() + "");
            finalList.add(pointHistoryMap);
        }
        org.json.simple.JSONArray jsonArray = ObjectMaker.getSimpleJSONArray();
        for(HashMap<String, String> map : finalList) {
            JSONObject jTemp = ObjectMaker.getSimpleJSONObject();
            jTemp.putAll(map);
            jsonArray.add(jTemp);
        }
        jsonObject.put("list", jsonArray);
        jsonObject.put("totalPoint", membersRepository.getPoint(email));
        return jsonObject;
    }
}
