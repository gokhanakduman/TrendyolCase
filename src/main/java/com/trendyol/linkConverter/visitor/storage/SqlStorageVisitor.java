package com.trendyol.linkConverter.visitor.storage;

import javax.annotation.Resource;

import com.trendyol.linkConverter.model.DefaultLinkModel;
import com.trendyol.linkConverter.model.ProductLinkModel;
import com.trendyol.linkConverter.model.SearchLinkModel;
import com.trendyol.linkConverter.repository.DefaultLinkRepository;
import com.trendyol.linkConverter.repository.ProductLinkRepository;
import com.trendyol.linkConverter.repository.SearchLinkRepository;
import com.trendyol.linkConverter.repository.model.DefaultLinkEntityModel;
import com.trendyol.linkConverter.repository.model.ProductLinkEntityModel;
import com.trendyol.linkConverter.repository.model.SearchLinkEntityModel;

public class SqlStorageVisitor implements PersistentStorageVisitor {

	@Resource
	ProductLinkRepository productLinkRepository;
	
	@Resource
	SearchLinkRepository searchLinkRepository;
	
	@Resource
	DefaultLinkRepository defaultLinkRepository;
	
	// Async can be used for not blocking the response to user
	//@Async
	@Override
	public void visitProductLink(ProductLinkModel productLinkModel) {
		ProductLinkEntityModel productLinkEntityModel = ProductLinkEntityModel.initFromProductLinkModel(productLinkModel);
		productLinkRepository.save(productLinkEntityModel);
	}

	@Override
	public void visitSearchLink(SearchLinkModel searchLinkModel) {
		SearchLinkEntityModel searchLinkEntityModel = SearchLinkEntityModel.initFromSearchLinkModel(searchLinkModel);
		searchLinkRepository.save(searchLinkEntityModel);
	}

	@Override
	public void visitDefaultLink(DefaultLinkModel defaultLinkModel) {
		DefaultLinkEntityModel defaultLinkEntityModel = DefaultLinkEntityModel.initFromDefaultLinkModel(defaultLinkModel);
		defaultLinkRepository.save(defaultLinkEntityModel);
	}

}
