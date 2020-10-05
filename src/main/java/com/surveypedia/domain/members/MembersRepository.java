package com.surveypedia.domain.members;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MembersRepository extends JpaRepository<Members, String> {


    @Query(value = "SELECT * FROM members WHERE email = ? AND pass = PASSWORD(?)", nativeQuery = true)
    Members login(String email, String pass);
}
