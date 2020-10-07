package com.surveypedia.domain.pointhistory;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "pointhistory")
public class PointHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ph_code;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, name = "s_code")
    private Integer scode;

    @Column(nullable = false)
    private Integer pointchange;

    @Column(nullable = false, name = "ph_type")
    @Enumerated(EnumType.STRING)
    private PointHistoryType phtype;

    public PointHistory(String email, Integer s_code, Integer pointchange, PointHistoryType ph_type) {
        this.email = email;
        this.scode = s_code;
        this.pointchange = pointchange;
        this.phtype = ph_type;
    }
}
