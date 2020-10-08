package com.surveypedia.domain.choices;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter
public class ChoicesCompositeKey implements Serializable {

    private Integer scode;
    private Integer qnumber;
    private Integer choicenum;

    @Override
    public int hashCode() {
        return Objects.hash(scode, qnumber, choicenum);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass().isAssignableFrom(ChoicesCompositeKey.class)) {
            ChoicesCompositeKey key = (ChoicesCompositeKey)obj;
            return this.scode == key.getScode() && this.qnumber == key.getQnumber() && this.choicenum == key.getChoicenum();
        } else return false;
    }
}
