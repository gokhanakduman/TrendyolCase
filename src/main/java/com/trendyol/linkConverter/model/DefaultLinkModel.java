package com.trendyol.linkConverter.model;

import com.trendyol.linkConverter.visitor.storage.PersistentStorageVisitor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultLinkModel extends LinkModel {

	private String path;

	@Override
	public void acceptPersistentStorageVisitor(PersistentStorageVisitor persistentStorageVisitor) {
		persistentStorageVisitor.visitDefaultLink(this);
	}
	
	
}
