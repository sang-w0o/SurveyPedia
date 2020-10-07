package com.surveypedia.domain.choiceresults;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter
public class ChoiceResultsCompositeKey implements Serializable {

    private Integer s_code;
    private Integer q_number;
    private String respondent;

    @Override
    public int hashCode() {
        return Objects.hash(s_code, q_number, respondent);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass().isAssignableFrom(ChoiceResultsCompositeKey.class)) {
            ChoiceResultsCompositeKey key = (ChoiceResultsCompositeKey) obj;
            return s_code == key.getS_code() && q_number == key.getQ_number() && respondent.equals(key.getRespondent());
        } else return false;
    }
}
