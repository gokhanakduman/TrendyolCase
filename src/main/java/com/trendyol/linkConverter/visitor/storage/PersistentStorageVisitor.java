package com.trendyol.linkConverter.visitor.storage;

import com.trendyol.linkConverter.model.*;

public interface PersistentStorageVisitor {
	void visitProductLink(ProductLinkModel productLinkModel);
	void visitSearchLink(SearchLinkModel productLinkModel);
	void visitDefaultLink(DefaultLinkModel defaultLinkModel);
}
