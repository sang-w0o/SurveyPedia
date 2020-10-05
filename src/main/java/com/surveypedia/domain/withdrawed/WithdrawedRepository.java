package com.surveypedia.domain.withdrawed;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawedRepository extends JpaRepository<Withdrawed, String> {

    Withdrawed findByEmail(String email);

    Withdrawed save(Withdrawed withdrawed);
}
