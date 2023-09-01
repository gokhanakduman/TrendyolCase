package com.trendyol.linkConverter.model;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.trendyol.linkConverter.visitor.storage.PersistentStorageVisitor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductLinkModel extends LinkModel {
	
	@JsonAlias({ "ContentId", "contentId" })
	private String contentId;
	
	@JsonAlias({ "CampaignId", "boutiqueId" })
	private String campaignId;
	
	@JsonAlias({ "MerchantId", "merchantId" })
	private String merchantId;

	@Override
	public void acceptPersistentStorageVisitor(PersistentStorageVisitor persistentStorageVisitor) {
		persistentStorageVisitor.visitProductLink(this);
	}
	
}
