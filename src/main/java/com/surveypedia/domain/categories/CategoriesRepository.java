package com.surveypedia.domain.categories;

import com.surveypedia.tools.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoriesRepository extends JpaRepository<Categories, String> {

    List<Categories> findAll();

    @Query(value = SQL.Category.GET_DESC_BY_CODE, nativeQuery = true)
    String getDescByC_code(String c_code);
}
