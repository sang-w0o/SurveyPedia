package com.surveypedia.domain.categories;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.HashMap;

@NoArgsConstructor
@Getter
@Entity
public class Categories {

    @Id
    @Column(name = "c_code")
    private String ccode;

    @Column(nullable = false, name = "c_desc")
    private String cdesc;

    public HashMap<String, String> convertMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("c_code", ccode);
        map.put("c_desc", cdesc);
        return map;
    }
}
