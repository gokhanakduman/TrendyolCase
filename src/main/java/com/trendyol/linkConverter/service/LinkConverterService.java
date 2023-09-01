package com.trendyol.linkConverter.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trendyol.linkConverter.model.LinkModel;
import com.trendyol.linkConverter.model.LinkType;
import com.trendyol.linkConverter.strategy.LinkConverterStrategy;
import com.trendyol.linkConverter.strategy.LinkConverterStrategyFactory;
import com.trendyol.linkConverter.strategy.exception.InvalidURIException;
import com.trendyol.linkConverter.strategy.exception.URIConversionException;
import com.trendyol.linkConverter.util.URIUtils;
import com.trendyol.linkConverter.visitor.storage.PersistentStorageVisitor;

@Service
public class LinkConverterService {
	
	@Autowired
	private PersistentStorageVisitor persistentStorageVisitor;
	
	@Autowired LinkConverterStrategyFactory linkConverterStrategyFactory;
	
	public String getConvertedUriString(String uriString, LinkType sourceLinkType) throws InvalidURIException, URIConversionException {
		URI uri = URIUtils.getUriFromStringForLinkType(uriString, sourceLinkType);
		LinkConverterStrategy strategy = null;
		strategy = linkConverterStrategyFactory. getLinkConverterStrategyFromUri(uri, sourceLinkType);
		LinkModel linkModel = strategy.getRequestModel();
		linkModel.acceptPersistentStorageVisitor(persistentStorageVisitor);
		return strategy.getResponseUriString();
	}
}
