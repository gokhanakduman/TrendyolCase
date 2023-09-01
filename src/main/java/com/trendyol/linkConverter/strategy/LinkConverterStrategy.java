package com.trendyol.linkConverter.strategy;

import java.net.URI;

import com.trendyol.linkConverter.model.LinkModel;
import com.trendyol.linkConverter.model.LinkType;
import com.trendyol.linkConverter.strategy.exception.InvalidURIException;
import com.trendyol.linkConverter.strategy.exception.URIConversionException;

public abstract class LinkConverterStrategy {
	
	abstract public LinkModel getRequestModel();
	
	abstract public void applyStrategy(URI uri, LinkType sourceLinkType) throws InvalidURIException, URIConversionException;
	
	public String getResponseUriString() {
		return this.getRequestModel().getResponseUriString();
	}
	
	public boolean isValidForUriAndLinkType(URI uri, LinkType linkType) {
		return true;
	}
}
