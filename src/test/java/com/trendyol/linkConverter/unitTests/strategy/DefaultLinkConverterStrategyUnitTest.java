package com.trendyol.linkConverter.unitTests.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;

import com.trendyol.linkConverter.model.LinkTuple;
import com.trendyol.linkConverter.model.LinkType;
import com.trendyol.linkConverter.strategy.DefaultLinkConverterStrategy;
import com.trendyol.linkConverter.strategy.exception.InvalidURIException;
import com.trendyol.linkConverter.strategy.exception.URIConversionException;
import com.trendyol.linkConverter.util.TestConstants;

public class DefaultLinkConverterStrategyUnitTest {
	
	@Test
	void checkApplyStrategy() throws InvalidURIException, URIConversionException {
		DefaultLinkConverterStrategy strategy = new DefaultLinkConverterStrategy();

		for(LinkTuple tuple : TestConstants.VALID_DEFAULT_WEB_TO_DEEP_LINK_TUPLES) {
			strategy.applyStrategy(tuple.getWebUri(), LinkType.WEB);
			assertEquals(strategy.getResponseUriString(), tuple.deeplink);
		}
		
		for(LinkTuple tuple : TestConstants.VALID_DEFAULT_DEEPLINK_TO_WEB_URI_TUPLES_LIST) {
			strategy.applyStrategy(tuple.getDeeplinkUri(), LinkType.DEEPLINK);
			assertEquals(strategy.getResponseUriString(), tuple.web);
		}
	}
}
