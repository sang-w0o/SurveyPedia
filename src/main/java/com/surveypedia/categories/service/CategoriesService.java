package com.surveypedia.categories.service;

import com.surveypedia.domain.categories.Categories;
import com.surveypedia.domain.categories.CategoriesRepository;
import com.surveypedia.tools.ObjectMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public org.json.simple.JSONObject findAll() {
        org.json.simple.JSONObject jsonObject = ObjectMaker.getSimpleJSONObject();
        List<Categories> categories = categoriesRepository.findAll();
        org.json.simple.JSONArray jsonArray = ObjectMaker.getSimpleJSONArray();
        for(Categories category : categories) {
            org.json.simple.JSONObject jTemp = ObjectMaker.getSimpleJSONObject();
            jTemp.putAll(category.convertMap());
            jsonArray.add(jTemp);
        }
        jsonObject.put("categories", jsonArray);
        return jsonObject;
    }
}
