package com.surveypedia.domain.surveys;

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
}
