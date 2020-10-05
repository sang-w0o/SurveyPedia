package com.surveypedia.domain.pointhistory;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "pointhistory")
public class PointHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ph_code;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Integer s_code;

    @Column(nullable = false)
    private Integer pointchange;

    @Column(nullable = false)
    private String ph_type;
}
