package com.trendyol.linkConverter.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.trendyol.linkConverter.visitor.storage.PersistentStorageVisitor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchLinkModel extends LinkModel {

	@JsonAlias({ "q", "Query" })
	private String queryString;

	@Override
	public void acceptPersistentStorageVisitor(PersistentStorageVisitor persistentStorageVisitor) {
		persistentStorageVisitor.visitSearchLink(this);
	}
	
}
