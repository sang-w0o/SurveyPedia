package com.surveypedia.domain.interests;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@NoArgsConstructor
@Getter
@Entity
@IdClass(InterestsCompositeKey.class)
public class Interests {

    @Id
    private String email;

    @Column(nullable = false, name = "s_code")
    private Integer scode;

    @Builder
    public Interests(String email, Integer scode) {
        this.email = email;
        this.scode = scode;
    }
}
