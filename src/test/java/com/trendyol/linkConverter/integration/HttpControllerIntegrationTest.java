package com.trendyol.linkConverter.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.trendyol.linkConverter.model.DefaultLinkModel;
import com.trendyol.linkConverter.model.LinkTuple;
import com.trendyol.linkConverter.model.ProductLinkModel;
import com.trendyol.linkConverter.model.SearchLinkModel;
import com.trendyol.linkConverter.service.LinkConverterService;
import com.trendyol.linkConverter.strategy.LinkConverterStrategyFactory;
import com.trendyol.linkConverter.strategy.exception.InvalidURIException;
import com.trendyol.linkConverter.strategy.exception.InvalidURIException.InvalidURIReason;
import com.trendyol.linkConverter.util.TestConstants;
import com.trendyol.linkConverter.util.TestUtils;
import com.trendyol.linkConverter.visitor.storage.PersistentStorageVisitor;


@ActiveProfiles("test")
@ContextConfiguration(classes= {HttpControllerIntegrationTest.MockConfiguration.class})
@WebMvcTest
@ComponentScan(basePackages = "com.trendyol.linkConverter.controller")
public class HttpControllerIntegrationTest {

	@Autowired
	MockMvc mvc;
	
	
	@TestConfiguration
	public static class MockConfiguration {
		
		@Primary
		@Bean
		PersistentStorageVisitor storageVisitor() {
			return new PersistentStorageVisitor() {
				
				@Override
				public void visitSearchLink(SearchLinkModel productLinkModel) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void visitProductLink(ProductLinkModel productLinkModel) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void visitDefaultLink(DefaultLinkModel defaultLinkModel) {
					// TODO Auto-generated method stub
					
				}
			};
		}
		
		@Bean
		LinkConverterStrategyFactory linkConverterStrategyFactory() {
			return TestUtils.linkConverterStrategyFactory();
		}
		
		@Bean
		LinkConverterService linkConverterService() {
			return new LinkConverterService();
		}
	}
	
	@Test
	void validProductLinks() throws Exception {
		for (LinkTuple tuple: TestConstants.VALID_PRODUCT_URI_TUPLES_LIST) {
			TestUtils.performMvcExpectSuccess(mvc, TestConstants.WEB_LINK_CONVERT_ENDPOINT_PATH, tuple.web, tuple.deeplink);
			TestUtils.performMvcExpectSuccess(mvc, TestConstants.DEEP_LINK_CONVERT_ENDPOINT_PATH, tuple.deeplink, tuple.web);
		}
	}
	
	@Test
	void validSearchLinks() throws Exception {
		for (LinkTuple tuple: TestConstants.VALID_SEARCH_URI_TUPLES_LIST) {
			TestUtils.performMvcExpectSuccess(mvc, TestConstants.WEB_LINK_CONVERT_ENDPOINT_PATH, tuple.web, tuple.deeplink);
			TestUtils.performMvcExpectSuccess(mvc, TestConstants.DEEP_LINK_CONVERT_ENDPOINT_PATH, tuple.deeplink, tuple.web);
		}
	}
	
	@Test
	void validDefaultLinks() throws Exception {
		for (LinkTuple tuple: TestConstants.VALID_DEFAULT_WEB_TO_DEEP_LINK_TUPLES) {
			TestUtils.performMvcExpectSuccess(mvc, TestConstants.WEB_LINK_CONVERT_ENDPOINT_PATH, tuple.web, tuple.deeplink);
		}
		for (LinkTuple tuple: TestConstants.VALID_DEFAULT_DEEPLINK_TO_WEB_URI_TUPLES_LIST) {
			TestUtils.performMvcExpectSuccess(mvc, TestConstants.DEEP_LINK_CONVERT_ENDPOINT_PATH, tuple.deeplink, tuple.web);
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
