package com.trendyol.linkConverter.unitTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.trendyol.linkConverter.model.LinkType;
import com.trendyol.linkConverter.strategy.exception.InvalidURIException;
import com.trendyol.linkConverter.util.TestConstants;
import com.trendyol.linkConverter.util.URIUtils;

public class URIUtilsUnitTest {

	@Test
	public void checkValidProductWebURIs() throws URISyntaxException, InvalidURIException {
		List<String> list = TestConstants.VALID_PRODUCT_URI_TUPLES_LIST.stream().map(tuple -> tuple.web).collect(Collectors.toList());
		checkValidityOfUriList(list,LinkType.WEB);
	}
	
	@Test
	public void checkValidSearchWebURIs() throws URISyntaxException, InvalidURIException {
		List<String> list = TestConstants.VALID_SEARCH_URI_TUPLES_LIST.stream().map(tuple -> tuple.web).collect(Collectors.toList());
		checkValidityOfUriList(list,LinkType.WEB);
	}
	
	@Test
	public void checkValidDefaultWebURIs() throws URISyntaxException, InvalidURIException {
		List<String> list = TestConstants.VALID_DEFAULT_WEB_TO_DEEP_LINK_TUPLES.stream().map(tuple -> tuple.web).collect(Collectors.toList());
		checkValidityOfUriList(list,LinkType.WEB);
	}
	
	@Test
	public void checkValidProductDeeplinkURIs() throws URISyntaxException, InvalidURIException {
		List<String> list = TestConstants.VALID_PRODUCT_URI_TUPLES_LIST.stream().map(tuple -> tuple.deeplink).collect(Collectors.toList());
		checkValidityOfUriList(list,LinkType.DEEPLINK);
	}
	
	@Test
	public void checkValidSearchDeeplinkURIs() throws URISyntaxException, InvalidURIException {
		List<String> list = TestConstants.VALID_SEARCH_URI_TUPLES_LIST.stream().map(tuple -> tuple.deeplink).collect(Collectors.toList());
		checkValidityOfUriList(list,LinkType.DEEPLINK);
	}
	
	@Test
	public void checkValidDefaultDeeplinkURIs() throws URISyntaxException, InvalidURIException {
		List<String> list = TestConstants.VALID_DEFAULT_DEEPLINK_TO_WEB_URI_TUPLES_LIST.stream().map(tuple -> tuple.deeplink).collect(Collectors.toList());
		checkValidityOfUriList(list,LinkType.DEEPLINK);
	}
	
	@Test
	public void checkIvalidUrls() throws URISyntaxException {
		for(String uriString : TestConstants.INVALID_URI_LIST) {
			URI uri = new URI(uriString);
			assertFalse(URIUtils.checkValidityOfUriForLinkType(uri, LinkType.WEB));
			assertFalse(URIUtils.checkValidityOfUriForLinkType(uri, LinkType.DEEPLINK));
		}
	}
	
	@Test
	void checkMalformedUriList() {
		for(String uriString : TestConstants.MALFORMED_URI_LIST) {
			assertThrows(InvalidURIException.class, () -> {
				URIUtils.getUriFromStringForLinkType(uriString, LinkType.WEB);
				URIUtils.getUriFromStringForLinkType(uriString, LinkType.DEEPLINK);
			});
		}
	}
	
	private void checkValidityOfUriList(List<String> uriList, LinkType linkType) throws URISyntaxException, InvalidURIException {
		for(String uriString : uriList) {
			 URI uri = URIUtils.getUriFromStringForLinkType(uriString, linkType);
			 assertTrue(URIUtils.checkValidityOfUriForLinkType(uri, linkType));
		}
	}
}
