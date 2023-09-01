package com.trendyol.linkConverter.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.trendyol.linkConverter.model.LinkTuple;
import com.trendyol.linkConverter.model.LinkType;
import com.trendyol.linkConverter.repository.DefaultLinkRepository;
import com.trendyol.linkConverter.repository.ProductLinkRepository;
import com.trendyol.linkConverter.repository.SearchLinkRepository;
import com.trendyol.linkConverter.service.LinkConverterService;
import com.trendyol.linkConverter.strategy.exception.InvalidURIException;
import com.trendyol.linkConverter.strategy.exception.URIConversionException;
import com.trendyol.linkConverter.util.TestConstants;
import com.trendyol.linkConverter.util.TestUtils;

@SpringBootTest
@ActiveProfiles("test")
public class LinkConverterServiceIntegrationTest {
	
	@Autowired
	LinkConverterService linkConverterService;
	
	@Autowired
	ProductLinkRepository productLinkRepository;
	
	@Autowired
	SearchLinkRepository searchLinkRepository;
	
	@Autowired
	DefaultLinkRepository defaultLinkRepository;
	
	
	@Test
	void validProductURIs() throws InvalidURIException, URIConversionException {		
		for(LinkTuple tuple : TestConstants.VALID_PRODUCT_URI_TUPLES_LIST) {
			assertEquals(linkConverterService.getConvertedUriString(tuple.web, LinkType.WEB), tuple.deeplink);
			TestUtils.checkIfGivenProductLinkSavedToDB(productLinkRepository, tuple.getWebUri(), LinkType.WEB);
			
			assertEquals(linkConverterService.getConvertedUriString(tuple.deeplink, LinkType.DEEPLINK), tuple.web);
			TestUtils.checkIfGivenProductLinkSavedToDB(productLinkRepository, tuple.getDeeplinkUri(), LinkType.DEEPLINK);
		}
	}
	
	@Test
	void validSearchURIs() throws InvalidURIException, URIConversionException {		
		for(LinkTuple tuple : TestConstants.VALID_SEARCH_URI_TUPLES_LIST) {
			assertEquals(linkConverterService.getConvertedUriString(tuple.web, LinkType.WEB), tuple.deeplink);
			TestUtils.checkIfGivenSearchLinkSavedToDB(searchLinkRepository, tuple.getWebUri(), LinkType.WEB);
			
			assertEquals(linkConverterService.getConvertedUriString(tuple.deeplink, LinkType.DEEPLINK), tuple.web);
			TestUtils.checkIfGivenSearchLinkSavedToDB(searchLinkRepository, tuple.getDeeplinkUri(), LinkType.DEEPLINK);
		}
	}
	
	@Test
	void validDefaultURIs() throws InvalidURIException, URIConversionException {		
		for(LinkTuple tuple : TestConstants.VALID_DEFAULT_WEB_TO_DEEP_LINK_TUPLES) {
			assertEquals(linkConverterService.getConvertedUriString(tuple.web, LinkType.WEB), tuple.deeplink);
			TestUtils.checkIfGivenDefaultLinkSavedToDB(defaultLinkRepository, tuple.getWebUri(), LinkType.WEB);
		}
		for(LinkTuple tuple : TestConstants.VALID_DEFAULT_DEEPLINK_TO_WEB_URI_TUPLES_LIST) {
			assertEquals(linkConverterService.getConvertedUriString(tuple.deeplink, LinkType.DEEPLINK), tuple.web);
			TestUtils.checkIfGivenDefaultLinkSavedToDB(defaultLinkRepository, tuple.getDeeplinkUri(), LinkType.DEEPLINK);
		}
	}
	
	@Test
	void invalidLinks() throws Exception {
		for(String uriString : TestConstants.INVALID_URI_LIST) {
			assertThrows(InvalidURIException.class, () -> {
				linkConverterService.getConvertedUriString(uriString, LinkType.WEB);
			});
			
			assertThrows(InvalidURIException.class, () -> {
				linkConverterService.getConvertedUriString(uriString, LinkType.DEEPLINK);
			});
		}
	}
	
	@Test
	void nullEmptyLinks() throws Exception {
		
		assertThrows(InvalidURIException.class, () -> {
			linkConverterService.getConvertedUriString("", LinkType.WEB);
		});
		
		assertThrows(InvalidURIException.class, () -> {
			linkConverterService.getConvertedUriString(null, LinkType.DEEPLINK);
		});
	}
	
	@Test
	void malformedLinks() throws Exception {
		for(String uriString : TestConstants.MALFORMED_URI_LIST) {
			assertThrows(InvalidURIException.class, () -> {
				linkConverterService.getConvertedUriString(uriString, LinkType.WEB);
			});
			
			assertThrows(InvalidURIException.class, () -> {
				linkConverterService.getConvertedUriString(uriString, LinkType.DEEPLINK);
			});
		}
	}
}
