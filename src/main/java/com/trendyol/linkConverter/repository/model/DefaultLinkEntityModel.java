package com.trendyol.linkConverter.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.trendyol.linkConverter.model.DefaultLinkModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "default_link_access")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class DefaultLinkEntityModel extends BaseEntityModel{
	
	@Column(name = "request_path")
    private String requestPath;
	
	public static DefaultLinkEntityModel initFromDefaultLinkModel(DefaultLinkModel defaultLinkModel) {
		DefaultLinkEntityModel defaultLinkEntityModel = DefaultLinkEntityModel.builder()
				.requestPath(defaultLinkModel.getPath())
				.requestSource(defaultLinkModel.getSourceLinkTypeString())
				.requestUri(defaultLinkModel.getOriginalRequestURI().toString())
				.responseUri(defaultLinkModel.getResponseUriString())
				.build();
		return defaultLinkEntityModel;
	}
}
