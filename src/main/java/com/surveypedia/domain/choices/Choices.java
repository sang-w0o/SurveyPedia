package com.surveypedia.domain.choices;

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
@IdClass(ChoicesCompositeKey.class)
public class Choices {

    @Id
    @Column(name = "s_code")
    private Integer scode;

    @Column(nullable = false, name = "q_number")
    private Integer qnumber;

    @Column(nullable = false, name = "choice_num")
    private Integer choicenum;

    @Column(nullable = false, name = "choice_content")
    String choicecontent;

    @Builder
    public Choices(Integer scode, Integer qnumber, Integer choicenum, String choicecontent) {
        this.scode = scode;
        this.qnumber = qnumber;
        this.choicecontent = choicecontent;
        this.choicenum = choicenum;
    }

    public HashMap<String, Object> convertMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("choice_num", choicenum);
        map.put("choice_content", choicecontent);
        return map;
    }
}
