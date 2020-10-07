package com.surveypedia.domain.choiceresults;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@NoArgsConstructor
@Getter
@Entity
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
}
