package com.surveypedia.domain.withdrawed;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity
public class Withdrawed {

    @Id
    private String email;

}
