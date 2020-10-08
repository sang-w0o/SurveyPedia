package com.surveypedia.domain.choiceresults;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "choiceresults")
@IdClass(ChoiceResultsCompositeKey.class)
public class ChoiceResults {

    @Id
    @Column(name = "s_code")
    private Integer scode;

    @Column(nullable = false, name = "q_number")
    private Integer qnumber;

    @Column(nullable = false, name = "choice_num")
    private Integer choicenum;

    @Column(nullable = false)
    private String respondent;

    @Builder
    public ChoiceResults(Integer scode, Integer qnumber, Integer choicenum, String respondent) {
        this.scode = scode;
        this.qnumber = qnumber;
        this.choicenum = choicenum;
        this.respondent = respondent;
    }
}
