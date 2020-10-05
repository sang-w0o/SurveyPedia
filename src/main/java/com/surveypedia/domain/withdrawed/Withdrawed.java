package com.surveypedia.domain.withdrawed;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Withdrawed {

    @Id
    private String email;

    public Withdrawed(String email) {
        this.email = email;
    }

}
