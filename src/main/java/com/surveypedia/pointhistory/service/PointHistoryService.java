package com.surveypedia.pointhistory.service;

import com.surveypedia.domain.members.Members;
import com.surveypedia.domain.members.MembersRepository;
import com.surveypedia.domain.pointhistory.PointHistory;
import com.surveypedia.domain.pointhistory.PointHistoryRepository;
import com.surveypedia.domain.pointhistory.PointHistoryType;
import com.surveypedia.domain.surveys.SurveysRepository;
import com.surveypedia.pointhistory.dto.PointHistoryPurchaseRequestDto;
import com.surveypedia.pointhistory.dto.PointHistoryRespondentInsertRequestDto;
import com.surveypedia.pointhistory.exception.PointHistoryAlreadyPurchasedException;
import com.surveypedia.pointhistory.exception.PointHistoryInsertException;
import com.surveypedia.pointhistory.exception.PointHistoryNotEnoughPointException;
import com.surveypedia.pointhistory.exception.PointHistoryWriterIsBuyerException;
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
        HashMap<String, Object> pointHistoryMap = null;
        ArrayList<HashMap<String, Object>> finalList = new ArrayList<>();
        Members member = (Members)request.getSession(false).getAttribute("userInfo");
        String email = member.getEmail();
        List<PointHistory> list = pointHistoryRepository.findByEmail(email);
        for(PointHistory pointHistory : list) {
            pointHistoryMap = new HashMap<>();
            SurveyHistoryInfoDto infoDto = surveysRepository.getSurveyInfoByS_code(pointHistory.getScode()).stream().map(SurveyHistoryInfoDto::new).collect(Collectors.toList()).get(0);
            pointHistoryMap.put("email", email);
            pointHistoryMap.put("s_title", infoDto.getS_title());
            pointHistoryMap.put("ph_type", pointHistory.getPhtype().toString());
            pointHistoryMap.put("pointchange", pointHistory.getPointchange() + "");
            finalList.add(pointHistoryMap);
        }
        org.json.simple.JSONArray jsonArray = ObjectMaker.getSimpleJSONArray();
        for(HashMap<String, Object> map : finalList) {
            JSONObject jTemp = ObjectMaker.getSimpleJSONObject();
            jTemp.putAll(map);
            jsonArray.add(jTemp);
        }
        jsonObject.put("list", jsonArray);
        jsonObject.put("totalPoint", membersRepository.getPoint(email));
        return jsonObject;
    }

    public org.json.simple.JSONObject buyAndSell(PointHistoryPurchaseRequestDto requestDto) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        try {
            if(surveysRepository.findByEmailAndScode(requestDto.getBuyer(), requestDto.getS_code()) != null) {
                throw new PointHistoryWriterIsBuyerException();
            }
            if(pointHistoryRepository.findByEmailAndScodeAndPhtype(requestDto.getBuyer(), requestDto.getS_code(), PointHistoryType.B) != null) {
                throw new PointHistoryAlreadyPurchasedException();
            } else {
                int currentPoint = pointHistoryRepository.getPoint(requestDto.getBuyer());
                if(currentPoint - requestDto.getPrice() >= 0) {
                    PointHistory buyerHistory = new PointHistory(requestDto.getBuyer(), requestDto.getS_code(), -requestDto.getPrice(), PointHistoryType.B);
                    PointHistory sellerHistory = new PointHistory(requestDto.getSeller(),requestDto.getS_code(), requestDto.getPrice(), PointHistoryType.S);
                    pointHistoryRepository.save(buyerHistory);
                    pointHistoryRepository.save(sellerHistory);
                    jsonObject.put("result", true);
                    jsonObject.put("message", "구매가 정상적으로 완료되었습니다.");
                }
                else {
                    throw new PointHistoryNotEnoughPointException();
                }
            }
        } catch(PointHistoryWriterIsBuyerException | PointHistoryAlreadyPurchasedException | PointHistoryNotEnoughPointException exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(exception);
        }
        return jsonObject;
    }

    public org.json.simple.JSONObject insertRespondent(PointHistoryRespondentInsertRequestDto requestDto) {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        try {
            PointHistory history = new PointHistory(requestDto.getRespondent(), requestDto.getS_code(), 5, PointHistoryType.P);
            pointHistoryRepository.save(history);
            jsonObject.put("result", true);
        } catch(Exception exception) {
            jsonObject = ObjectMaker.getJSONObjectWithException(new PointHistoryInsertException());
        }
        return jsonObject;
    }
}
