package com.trendyol.linkConverter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trendyol.linkConverter.repository.model.DefaultLinkEntityModel;

@Repository
public interface DefaultLinkRepository extends JpaRepository<DefaultLinkEntityModel, String> {
	DefaultLinkEntityModel findTopByOrderByIdDesc();
}
