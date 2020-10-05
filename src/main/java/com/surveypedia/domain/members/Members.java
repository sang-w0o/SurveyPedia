package com.surveypedia.domain.members;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Entity
public class Members {

    @Id
    private String email;

    @Column(nullable = false)
    private String pass;

    @Column(nullable = false)
    private String nick;

    @Column(nullable = false)
    private String g_name;
}
