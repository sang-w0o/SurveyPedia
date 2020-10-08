package com.surveypedia.domain.subjectiveresults;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "subjectiveresults")
@IdClass(SubjectiveResultsCompositeKey.class)
public class SubjectiveResults {

    @Id
    @Column(name = "s_code")
    private Integer scode;

    @Column(nullable = false, name = "q_number")
    private Integer qnumber;

    @Column(nullable = false)
    private String respondent;

    @Column(nullable = false)
    private String answer;

    @Builder
    public SubjectiveResults(Integer scode, Integer qnumber, String respondent, String answer) {
        this.scode = scode;
        this.qnumber = qnumber;
        this.respondent = respondent;
        this.answer = answer;
    }
}
