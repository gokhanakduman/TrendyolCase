package com.trendyol.linkConverter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trendyol.linkConverter.repository.model.ProductLinkEntityModel;

@Repository
public interface ProductLinkRepository extends JpaRepository<ProductLinkEntityModel, String> {
	ProductLinkEntityModel findTopByOrderByIdDesc();
}
