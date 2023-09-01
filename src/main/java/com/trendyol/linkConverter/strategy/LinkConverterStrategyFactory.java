package com.trendyol.linkConverter.strategy;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.trendyol.linkConverter.model.LinkType;
import com.trendyol.linkConverter.strategy.exception.InvalidURIException;
import com.trendyol.linkConverter.strategy.exception.URIConversionException;
import com.trendyol.linkConverter.strategy.exception.InvalidURIException.InvalidURIReason;

@Component
public class LinkConverterStrategyFactory {
	
	
	List<Class<? extends LinkConverterStrategy>> strategyClassesChain;
	
	@Autowired
	public LinkConverterStrategyFactory(@Autowired @Qualifier("LinkConverterStrategyClassesChain") List<Class<? extends LinkConverterStrategy>> chain) {
		strategyClassesChain = chain;
	}
	
	public LinkConverterStrategy getLinkConverterStrategyFromUri(URI uri, LinkType sourceLinkType) throws InvalidURIException, URIConversionException {
		
		for (Class<? extends LinkConverterStrategy> linkConverterStrategyClass : strategyClassesChain) {
			try {
				LinkConverterStrategy strategy = linkConverterStrategyClass.getDeclaredConstructor().newInstance();
				if (strategy.isValidForUriAndLinkType(uri, sourceLinkType)) {
					strategy.applyStrategy(uri, sourceLinkType);
					return strategy;
				} else {
					strategy = null;
				}
			} catch (Exception e) {
				// do nothing, so code can find a valid strategy for that uri
			}
		}
		
		throw new InvalidURIException(sourceLinkType == LinkType.WEB ? InvalidURIReason.NOT_A_VALID_WEB_URI : InvalidURIReason.NOT_A_VALID_DEEPLINK, uri.toString());
	}
}
