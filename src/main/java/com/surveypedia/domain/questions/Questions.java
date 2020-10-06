package com.surveypedia.domain.questions;

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
@IdClass(QuestionsCompositeKey.class)
public class Questions {

    @Id
    private Integer s_code;

    @Column(nullable = false)
    private Integer q_number;

    @Column(nullable = false)
    private String q_title;

    @Column(nullable = false)
    private String q_type;

    @Builder
    public Questions(Integer s_code, Integer q_number, String q_title, String q_type) {
        this.s_code = s_code;
        this.q_number = q_number;
        this.q_title = q_title;
        this.q_type = q_type;
    }
}
