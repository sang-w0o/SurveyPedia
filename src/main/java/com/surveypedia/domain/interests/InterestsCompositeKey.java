package com.surveypedia.domain.interests;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter
public class InterestsCompositeKey implements Serializable {

    private String email;
    private Integer scode;

    @Override
    public int hashCode() {
        return Objects.hash(email, scode);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass().isAssignableFrom(InterestsCompositeKey.class)) {
            InterestsCompositeKey key = (InterestsCompositeKey)obj;
            return email.equals(key.getEmail()) && scode == key.getScode();
        } else return false;
    }
}
