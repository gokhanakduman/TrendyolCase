package com.trendyol.linkConverter.strategy;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendyol.linkConverter.model.LinkModel;
import com.trendyol.linkConverter.model.LinkType;
import com.trendyol.linkConverter.model.SearchLinkModel;
import com.trendyol.linkConverter.strategy.exception.InvalidURIException;
import com.trendyol.linkConverter.strategy.exception.URIConversionException;
import com.trendyol.linkConverter.strategy.exception.URIConversionException.ConversionErrorReason;
import com.trendyol.linkConverter.util.Constants;

public class SearchLinkConverterStrategy extends LinkConverterStrategy{

	private SearchLinkModel searchLinkModel;
	
	public void applyStrategy(URI uri, LinkType sourceLinkType) throws InvalidURIException, URIConversionException {
		
		if (!isValidForUriAndLinkType(uri, sourceLinkType)) {
			throw new URIConversionException(ConversionErrorReason.COULD_NOT_MAP_TO_MODEL, uri.toString());
		}
		List<NameValuePair> params = URLEncodedUtils.parse(uri, Charset.forName("UTF-8"));
		Map<String, String> paramMap = params.stream().collect(
		        Collectors.toMap(NameValuePair::getName, NameValuePair::getValue));

		searchLinkModel = (new ObjectMapper()).convertValue(paramMap, SearchLinkModel.class);
		
		if(searchLinkModel == null) {
			throw new URIConversionException(ConversionErrorReason.COULD_NOT_MAP_TO_MODEL, uri.toString());
		}
		
		searchLinkModel.setSourceLinkType(sourceLinkType);
		searchLinkModel.setOriginalRequestURI(uri);
		
		URIBuilder uriBuilder = new URIBuilder();
		switch (sourceLinkType) {
		case WEB:
			uriBuilder
				.setScheme(Constants.DEEPLINK_URI_PROTOCOL)
				.setHost(null)
				.setPath("//")
				.addParameter("Page", "Search")
				.addParameter("Query", searchLinkModel.getQueryString() );
			break;

		case DEEPLINK:
			uriBuilder.setScheme("https")
				.setHost(Constants.WEB_URI_HOST)
				.setPath("/sr")
				.addParameter("q", searchLinkModel.getQueryString());
			break;
		}
		
		try {
			searchLinkModel.setResponseUriString(uriBuilder.build().toString());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			throw new URIConversionException(ConversionErrorReason.COULD_NOT_BUILD_URI, uri.toString());
		}
	}
	

	public boolean isValidForUriAndLinkType(URI uri, LinkType linkType) {
		if (linkType == LinkType.WEB) {
			if (uri.getPath().equals("/sr")) {
				return true;
			}
		} else {
			List<NameValuePair> params = URLEncodedUtils.parse(uri, Charset.forName("UTF-8"));
			Map<String, String> paramMap = params.stream().collect(
			        Collectors.toMap(NameValuePair::getName, NameValuePair::getValue));
			if (paramMap.get("Page") != null && paramMap.get("Page").equals("Search")) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public LinkModel getRequestModel() {
		return searchLinkModel;
	}

}
