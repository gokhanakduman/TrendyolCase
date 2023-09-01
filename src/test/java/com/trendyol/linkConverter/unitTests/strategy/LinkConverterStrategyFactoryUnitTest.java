package com.trendyol.linkConverter.unitTests.strategy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.trendyol.linkConverter.model.LinkTuple;
import com.trendyol.linkConverter.model.LinkType;
import com.trendyol.linkConverter.strategy.DefaultLinkConverterStrategy;
import com.trendyol.linkConverter.strategy.LinkConverterStrategyFactory;
import com.trendyol.linkConverter.strategy.ProductLinkConverterStrategy;
import com.trendyol.linkConverter.strategy.SearchLinkConverterStrategy;
import com.trendyol.linkConverter.strategy.exception.InvalidURIException;
import com.trendyol.linkConverter.strategy.exception.URIConversionException;
import com.trendyol.linkConverter.util.TestConstants;
import com.trendyol.linkConverter.util.TestUtils;

public class LinkConverterStrategyFactoryUnitTest {
		
	LinkConverterStrategyFactory linkConverterStrategyFactory = TestUtils.linkConverterStrategyFactory();

	@Test
	void checkProductFactory() throws InvalidURIException, URIConversionException {
		for(LinkTuple tuple : TestConstants.VALID_PRODUCT_URI_TUPLES_LIST) {
			assertTrue(linkConverterStrategyFactory.getLinkConverterStrategyFromUri(tuple.getWebUri(), LinkType.WEB) instanceof ProductLinkConverterStrategy);
			assertTrue(linkConverterStrategyFactory.getLinkConverterStrategyFromUri(tuple.getDeeplinkUri(), LinkType.DEEPLINK) instanceof ProductLinkConverterStrategy);
		}
	}
	
	@Test
	void checkSeachFactory() throws InvalidURIException, URIConversionException {
		for(LinkTuple tuple : TestConstants.VALID_SEARCH_URI_TUPLES_LIST) {
			assertTrue(linkConverterStrategyFactory.getLinkConverterStrategyFromUri(tuple.getWebUri(), LinkType.WEB) instanceof SearchLinkConverterStrategy);
			assertTrue(linkConverterStrategyFactory.getLinkConverterStrategyFromUri(tuple.getDeeplinkUri(), LinkType.DEEPLINK) instanceof SearchLinkConverterStrategy);
		}
	}
	
	@Test
	void checkDefaultFactory() throws InvalidURIException, URIConversionException {
		for(LinkTuple tuple : TestConstants.VALID_DEFAULT_WEB_TO_DEEP_LINK_TUPLES) {
			assertTrue(linkConverterStrategyFactory.getLinkConverterStrategyFromUri(tuple.getWebUri(), LinkType.WEB) instanceof DefaultLinkConverterStrategy);
		}
		
		for(LinkTuple tuple : TestConstants.VALID_DEFAULT_DEEPLINK_TO_WEB_URI_TUPLES_LIST) {
			assertTrue(linkConverterStrategyFactory.getLinkConverterStrategyFromUri(tuple.getDeeplinkUri(), LinkType.DEEPLINK) instanceof DefaultLinkConverterStrategy);
		}
	}
}
