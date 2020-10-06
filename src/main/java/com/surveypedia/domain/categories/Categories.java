package com.surveypedia.domain.categories;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.HashMap;

@Getter
@Entity
public class Categories {

    @Id
    private String c_code;

    @Column
    private String c_desc;

    public HashMap<String, String> convertMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("c_code", c_code);
        map.put("c_desc", c_desc);
        return map;
    }
}
