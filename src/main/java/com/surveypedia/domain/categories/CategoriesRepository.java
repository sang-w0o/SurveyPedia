package com.surveypedia.domain.categories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriesRepository extends JpaRepository<Categories, String> {

    List<Categories> findAll();
}
