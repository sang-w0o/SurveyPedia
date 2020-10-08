package com.surveypedia.domain.questions;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter
public class QuestionsCompositeKey implements Serializable {

    private Integer scode;
    private Integer qnumber;

    @Override
    public int hashCode() {
        return Objects.hash(scode, qnumber);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass().isAssignableFrom(QuestionsCompositeKey.class)) {
            QuestionsCompositeKey key = (QuestionsCompositeKey)obj;
            return this.scode == key.getScode() && this.qnumber == key.getQnumber();
        }
        else return false;
    }
}
