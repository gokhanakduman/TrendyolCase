package com.trendyol.linkConverter.acceptance;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.trendyol.linkConverter.model.LinkTuple;
import com.trendyol.linkConverter.model.LinkType;
import com.trendyol.linkConverter.repository.DefaultLinkRepository;
import com.trendyol.linkConverter.repository.ProductLinkRepository;
import com.trendyol.linkConverter.repository.SearchLinkRepository;
import com.trendyol.linkConverter.service.LinkConverterService;
import com.trendyol.linkConverter.strategy.exception.InvalidURIException;
import com.trendyol.linkConverter.strategy.exception.InvalidURIException.InvalidURIReason;
import com.trendyol.linkConverter.util.TestConstants;
import com.trendyol.linkConverter.util.TestUtils;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class LinkConverterApplicationAcceptanceTest {

	@Autowired
	MockMvc mvc;
	
	@Autowired
	LinkConverterService linkConverterService;
	
	@Autowired
	ProductLinkRepository productLinkRepository;
	
	@Autowired
	SearchLinkRepository searchLinkRepository;
	
	@Autowired
	DefaultLinkRepository defaultLinkRepository;
	
	
	@Test
	void validProductURIs() throws Exception {		
		for(LinkTuple tuple : TestConstants.VALID_PRODUCT_URI_TUPLES_LIST) {
			TestUtils.performMvcExpectSuccess(mvc, TestConstants.WEB_LINK_CONVERT_ENDPOINT_PATH, tuple.web, tuple.deeplink);
			TestUtils.checkIfGivenProductLinkSavedToDB(productLinkRepository, tuple.getWebUri(), LinkType.WEB);
			
			TestUtils.performMvcExpectSuccess(mvc, TestConstants.DEEP_LINK_CONVERT_ENDPOINT_PATH, tuple.deeplink, tuple.web);
			TestUtils.checkIfGivenProductLinkSavedToDB(productLinkRepository, tuple.getDeeplinkUri(), LinkType.DEEPLINK);
		}
	}
	
	@Test
	void validSearchURIs() throws Exception {		
		for(LinkTuple tuple : TestConstants.VALID_SEARCH_URI_TUPLES_LIST) {
			TestUtils.performMvcExpectSuccess(mvc, TestConstants.WEB_LINK_CONVERT_ENDPOINT_PATH, tuple.web, tuple.deeplink);
			TestUtils.checkIfGivenSearchLinkSavedToDB(searchLinkRepository, tuple.getWebUri(), LinkType.WEB);
			
			TestUtils.performMvcExpectSuccess(mvc, TestConstants.DEEP_LINK_CONVERT_ENDPOINT_PATH, tuple.deeplink, tuple.web);
			TestUtils.checkIfGivenSearchLinkSavedToDB(searchLinkRepository, tuple.getDeeplinkUri(), LinkType.DEEPLINK);
		}
	}
	
	@Test
	void validDefaultURIs() throws Exception {		
		for(LinkTuple tuple : TestConstants.VALID_DEFAULT_WEB_TO_DEEP_LINK_TUPLES) {
			TestUtils.performMvcExpectSuccess(mvc, TestConstants.WEB_LINK_CONVERT_ENDPOINT_PATH, tuple.web, tuple.deeplink);
			TestUtils.checkIfGivenDefaultLinkSavedToDB(defaultLinkRepository, tuple.getWebUri(), LinkType.WEB);
		}
		for(LinkTuple tuple : TestConstants.VALID_DEFAULT_DEEPLINK_TO_WEB_URI_TUPLES_LIST) {
			TestUtils.performMvcExpectSuccess(mvc, TestConstants.DEEP_LINK_CONVERT_ENDPOINT_PATH, tuple.deeplink, tuple.web);
			TestUtils.checkIfGivenDefaultLinkSavedToDB(defaultLinkRepository, tuple.getDeeplinkUri(), LinkType.DEEPLINK);
		}
	}
	
	@Test
	void invalidLinks() throws Exception {
		for(String uriString : TestConstants.INVALID_URI_LIST) {
			TestUtils.performMvcExpectError(mvc, TestConstants.WEB_LINK_CONVERT_ENDPOINT_PATH, uriString, 400, InvalidURIException.getErrorDescriptionForReason(InvalidURIReason.NOT_A_VALID_WEB_URI));
			TestUtils.performMvcExpectError(mvc, TestConstants.DEEP_LINK_CONVERT_ENDPOINT_PATH, uriString, 400, InvalidURIException.getErrorDescriptionForReason(InvalidURIReason.NOT_A_VALID_DEEPLINK));
		}
	}
	
	@Test
	void nullEmptyLinks() throws Exception {
		TestUtils.performMvcExpectError(mvc, TestConstants.WEB_LINK_CONVERT_ENDPOINT_PATH, null, 400, InvalidURIException.getErrorDescriptionForReason(InvalidURIReason.IS_NULL_OR_EMPTY));
		TestUtils.performMvcExpectError(mvc, TestConstants.DEEP_LINK_CONVERT_ENDPOINT_PATH, "", 400, InvalidURIException.getErrorDescriptionForReason(InvalidURIReason.IS_NULL_OR_EMPTY));
	}
	
	@Test
	void malformedLinks() throws Exception {
		for(String uriString : TestConstants.MALFORMED_URI_LIST) {
			TestUtils.performMvcExpectError(mvc, TestConstants.WEB_LINK_CONVERT_ENDPOINT_PATH, uriString, 400);
			TestUtils.performMvcExpectError(mvc, TestConstants.DEEP_LINK_CONVERT_ENDPOINT_PATH, uriString, 400);
		}
	}
}
