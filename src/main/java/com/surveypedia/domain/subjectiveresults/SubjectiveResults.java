package com.surveypedia.domain.subjectiveresults;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@NoArgsConstructor
@Getter
@Entity
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
}
