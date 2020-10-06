package com.surveypedia.domain.choices;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter
public class ChoicesCompositeKey implements Serializable {

    private Integer s_code;
    private Integer q_number;
    private Integer choice_num;

    @Override
    public int hashCode() {
        return Objects.hash(s_code, q_number, choice_num);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass().isAssignableFrom(ChoicesCompositeKey.class)) {
            ChoicesCompositeKey key = (ChoicesCompositeKey)obj;
            return this.s_code == key.getS_code() && this.q_number == key.getQ_number() && this.choice_num == key.getChoice_num();
        } else return false;
    }
}
