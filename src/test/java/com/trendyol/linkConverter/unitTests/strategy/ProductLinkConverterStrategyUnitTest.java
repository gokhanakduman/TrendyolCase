package com.trendyol.linkConverter.unitTests.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.trendyol.linkConverter.model.LinkTuple;
import com.trendyol.linkConverter.model.LinkType;
import com.trendyol.linkConverter.strategy.ProductLinkConverterStrategy;
import com.trendyol.linkConverter.strategy.exception.InvalidURIException;
import com.trendyol.linkConverter.strategy.exception.URIConversionException;
import com.trendyol.linkConverter.util.TestConstants;

public class ProductLinkConverterStrategyUnitTest {

	@Test
	void checkIsValidForUriAndLinkType() {
		ProductLinkConverterStrategy strategy = new ProductLinkConverterStrategy();
		
		for(LinkTuple tuple : TestConstants.VALID_PRODUCT_URI_TUPLES_LIST) {
			assertTrue(strategy.isValidForUriAndLinkType(tuple.getWebUri(), LinkType.WEB));
			assertTrue(strategy.isValidForUriAndLinkType(tuple.getDeeplinkUri(), LinkType.DEEPLINK));
		}
		
		for(LinkTuple tuple : TestConstants.VALID_SEARCH_URI_TUPLES_LIST) {
			assertFalse(strategy.isValidForUriAndLinkType(tuple.getWebUri(), LinkType.WEB));
			assertFalse(strategy.isValidForUriAndLinkType(tuple.getDeeplinkUri(), LinkType.DEEPLINK));
		}
		
		for(LinkTuple tuple : TestConstants.VALID_DEFAULT_WEB_TO_DEEP_LINK_TUPLES) {
			assertFalse(strategy.isValidForUriAndLinkType(tuple.getWebUri(), LinkType.WEB));
		}
		
		for(LinkTuple tuple : TestConstants.VALID_DEFAULT_DEEPLINK_TO_WEB_URI_TUPLES_LIST) {
			assertFalse(strategy.isValidForUriAndLinkType(tuple.getDeeplinkUri(), LinkType.DEEPLINK));
		}
	}
	
	@Test
	void checkApplyStrategy() throws InvalidURIException, URIConversionException {
		ProductLinkConverterStrategy strategy = new ProductLinkConverterStrategy();
		
		for(LinkTuple tuple : TestConstants.VALID_PRODUCT_URI_TUPLES_LIST) {
			strategy.applyStrategy(tuple.getWebUri(), LinkType.WEB);
			assertEquals(strategy.getResponseUriString(), tuple.deeplink);
			
			strategy.applyStrategy(tuple.getDeeplinkUri(), LinkType.DEEPLINK);
			assertEquals(strategy.getResponseUriString(), tuple.web);
		}
		
		for(LinkTuple tuple : TestConstants.VALID_SEARCH_URI_TUPLES_LIST) {
			
			assertThrows(URIConversionException.class, () -> {
				strategy.applyStrategy(tuple.getWebUri(), LinkType.WEB);
			});
			
			assertThrows(URIConversionException.class, () -> {
				strategy.applyStrategy(tuple.getDeeplinkUri(), LinkType.DEEPLINK);
			});
		}
		
		for(LinkTuple tuple : TestConstants.VALID_DEFAULT_WEB_TO_DEEP_LINK_TUPLES) {
			assertThrows(URIConversionException.class, () -> {
				strategy.applyStrategy(tuple.getWebUri(), LinkType.WEB);
			});
		}
		
		for(LinkTuple tuple : TestConstants.VALID_DEFAULT_DEEPLINK_TO_WEB_URI_TUPLES_LIST) {
			assertThrows(URIConversionException.class, () -> {
				strategy.applyStrategy(tuple.getDeeplinkUri(), LinkType.DEEPLINK);
			});
		}
	}
}
