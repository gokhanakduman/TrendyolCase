package com.trendyol.linkConverter.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.trendyol.linkConverter.model.SearchLinkModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "search_link_access")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class SearchLinkEntityModel extends BaseEntityModel{

	@Column(name = "query")
    private String query;

	
	public static SearchLinkEntityModel initFromSearchLinkModel(SearchLinkModel searchLinkModel) {
		SearchLinkEntityModel searchLinkEntityModel = SearchLinkEntityModel.builder()
				.query(searchLinkModel.getQueryString())
				.requestSource(searchLinkModel.getSourceLinkTypeString())
				.requestUri(searchLinkModel.getOriginalRequestURI().toString())
				.responseUri(searchLinkModel.getResponseUriString())
				.build();
		return searchLinkEntityModel;
	}
	
}
