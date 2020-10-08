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
    @Column(name = "s_code")
    private Integer scode;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, name = "s_id")
    private Integer sid;

    @Column(nullable = false, name = "c_code")
    private String ccode;

    @Column(nullable = false, name = "s_public")
    private String spublic;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false, name = "s_title")
    private String stitle;

    @Column(nullable = false, name = "s_reported")
    private String sreported;

    @Builder
    public Survey(String stitle, String email, String ccode, String spublic, Integer s_id, Integer price, String sreported) {
        this.stitle = stitle;
        this.email = email;
        this.ccode = ccode;
        this.spublic = spublic;
        this.sid = s_id;
        this.price = price;
        this.sreported = sreported;
    }
}
