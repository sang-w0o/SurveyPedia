package com.surveypedia.categories.controller;

import com.surveypedia.categories.service.CategoriesService;
import com.surveypedia.tools.WriteToClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class CategoriesApiController {

    private final CategoriesService categoriesService;

    @GetMapping("/CategoryGetAll.do")
    public void getAll(HttpServletResponse response) {
        WriteToClient.send(response, categoriesService.findAll());
    }
}
