package com.surveypedia.domain.questions;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.HashMap;

@NoArgsConstructor
@Getter
@Entity
@IdClass(QuestionsCompositeKey.class)
public class Questions {

    @Id
    @Column(name = "s_code")
    private Integer scode;

    @Column(nullable = false, name = "q_number")
    private Integer qnumber;

    @Column(nullable = false, name = "q_title")
    private String qtitle;

    @Column(nullable = false, name = "q_type")
    private String qtype;

    @Builder
    public Questions(Integer scode, Integer qnumber, String qtitle, String qtype) {
        this.scode = scode;
        this.qnumber = qnumber;
        this.qtitle = qtitle;
        this.qtype = qtype;
    }

    public HashMap<String, Object> convertMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("s_code", scode);
        map.put("q_number", qnumber);
        map.put("q_title", qtitle);
        map.put("q_type", qtype);
        return map;
    }
}
