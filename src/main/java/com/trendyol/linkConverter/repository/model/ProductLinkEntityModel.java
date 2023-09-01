package com.trendyol.linkConverter.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.trendyol.linkConverter.model.ProductLinkModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "product_link_access")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ProductLinkEntityModel extends BaseEntityModel{

	@Column(name = "content_id", nullable = false)
    private String contentId;
	
	@Column(name = "campaign_id")
    private String campaignId;

	@Column(name = "merchant_id")
    private String merchantId;

	public static ProductLinkEntityModel initFromProductLinkModel(ProductLinkModel productLinkModel) {
		ProductLinkEntityModel productLinkEntityModel = ProductLinkEntityModel.builder()
				.contentId(productLinkModel.getContentId())
				.campaignId(productLinkModel.getCampaignId())
				.merchantId(productLinkModel.getMerchantId())
				.requestSource(productLinkModel.getSourceLinkTypeString())
				.requestUri(productLinkModel.getOriginalRequestURI().toString())
				.responseUri(productLinkModel.getResponseUriString())
				.build();
		return productLinkEntityModel;
	}
}
