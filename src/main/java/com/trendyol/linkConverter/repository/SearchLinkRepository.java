package com.trendyol.linkConverter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trendyol.linkConverter.repository.model.SearchLinkEntityModel;

@Repository
public interface SearchLinkRepository extends JpaRepository<SearchLinkEntityModel, String> {
	SearchLinkEntityModel findTopByOrderByIdDesc();
}
