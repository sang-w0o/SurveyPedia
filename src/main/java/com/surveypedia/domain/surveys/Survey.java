package com.surveypedia.domain.surveys;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer s_code;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Integer s_id;

    @Column(nullable = false)
    private String c_code;

    @Column(nullable = false)
    private String s_public;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String s_title;

    @Column(nullable = false)
    private String s_reported;

    @Builder
    public Survey(String s_title, String email, String c_code, String s_public, Integer s_id, Integer price, String s_reported) {
        this.s_title = s_title;
        this.email = email;
        this.c_code = c_code;
        this.s_public = s_public;
        this.s_id = s_id;
        this.price = price;
        this.s_reported = s_reported;
    }
}
