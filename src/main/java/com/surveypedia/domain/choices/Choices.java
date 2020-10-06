package com.surveypedia.domain.choices;

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
@IdClass(ChoicesCompositeKey.class)
public class Choices {

    @Id
    private Integer s_code;

    @Column(nullable = false)
    private Integer q_number;

    @Column(nullable = false)
    private Integer choice_num;

    @Column(nullable = false)
    String choice_content;

    @Builder
    public Choices(Integer s_code, Integer q_number, Integer choice_num, String choice_content) {
        this.s_code = s_code;
        this.q_number = q_number;
        this.choice_content = choice_content;
        this.choice_num = choice_num;
    }
}
