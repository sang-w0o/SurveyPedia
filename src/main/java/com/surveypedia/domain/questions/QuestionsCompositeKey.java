package com.surveypedia.domain.questions;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter
public class QuestionsCompositeKey implements Serializable {

    private Integer s_code;
    private Integer q_number;

    @Override
    public int hashCode() {
        return Objects.hash(s_code, q_number);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass().isAssignableFrom(QuestionsCompositeKey.class)) {
            QuestionsCompositeKey key = (QuestionsCompositeKey)obj;
            return this.s_code == key.getS_code() && this.q_number == key.getQ_number();
        }
        else return false;
    }
}
