package com.surveypedia.domain.subjectiveresults;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter
public class SubjectiveResultsCompositeKey implements Serializable {

    private Integer s_code;
    private Integer q_number;
    private String respondent;

    @Override
    public int hashCode() {
        return Objects.hash(s_code, q_number, respondent);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass().isAssignableFrom(SubjectiveResultsCompositeKey.class)) {
            SubjectiveResultsCompositeKey key = (SubjectiveResultsCompositeKey)obj;
            return s_code == key.getS_code() && q_number == key.getQ_number() && respondent.equals(key.getRespondent());
        } else return false;
    }
}
