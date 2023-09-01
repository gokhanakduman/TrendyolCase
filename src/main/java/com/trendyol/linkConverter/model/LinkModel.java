package com.trendyol.linkConverter.model;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.trendyol.linkConverter.visitor.storage.PersistentStorageVisitor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class LinkModel {
	
	@JsonIgnore
	protected LinkType sourceLinkType;
	
	@JsonIgnore
	protected URI originalRequestURI;
	
	@JsonIgnore
	protected String responseUriString;
	
	abstract public void acceptPersistentStorageVisitor(PersistentStorageVisitor persistentStorageVisitor);
	
	@JsonIgnore
	public String getSourceLinkTypeString(){
		if (sourceLinkType == null) {
			return null;
		}
		return sourceLinkType.name();
	}
	
}
