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
import com.trendyol.linkConverter.util.Constants;
import com.trendyol.linkConverter.model.LinkModel;
import com.trendyol.linkConverter.model.LinkType;
import com.trendyol.linkConverter.model.ProductLinkModel;
import com.trendyol.linkConverter.strategy.exception.InvalidURIException;
import com.trendyol.linkConverter.strategy.exception.InvalidURIException.InvalidURIReason;
import com.trendyol.linkConverter.strategy.exception.URIConversionException.ConversionErrorReason;
import com.trendyol.linkConverter.strategy.exception.URIConversionException;
import com.trendyol.linkConverter.util.StringUtils;

public class ProductLinkConverterStrategy extends LinkConverterStrategy{

	private ProductLinkModel productLinkModel;
	
	public void applyStrategy(URI uri, LinkType sourceLinkType) throws InvalidURIException, URIConversionException {
		
		if (!isValidForUriAndLinkType(uri, sourceLinkType)) {
			throw new URIConversionException(ConversionErrorReason.COULD_NOT_MAP_TO_MODEL, uri.toString());
		}
		List<NameValuePair> params = URLEncodedUtils.parse(uri, Charset.forName("UTF-8"));
		Map<String, String> paramMap = params.stream().collect(
		        Collectors.toMap(NameValuePair::getName, NameValuePair::getValue));
		
		if (sourceLinkType == LinkType.WEB) {
			if (uri.getPath().matches(".+-p-[0-9]+")) {
				String[] splitPath = uri.getPath().split("-p-"); 
				String contentId = splitPath[ splitPath.length - 1 ];
				paramMap.put("contentId", contentId);
			}
		}
		try {
			productLinkModel = (new ObjectMapper()).convertValue(paramMap, ProductLinkModel.class);
		} catch (Exception e) {
			throw new URIConversionException(ConversionErrorReason.COULD_NOT_MAP_TO_MODEL, uri.toString());
		}
		
		
		if(productLinkModel == null || StringUtils.isNullOrEmpty(productLinkModel.getContentId())) {
			throw new InvalidURIException(InvalidURIReason.CONTENT_ID_MISSING, uri.toString());
		}
		
		productLinkModel.setSourceLinkType(sourceLinkType);
		productLinkModel.setOriginalRequestURI(uri);
		
		URIBuilder uriBuilder = new URIBuilder();
		switch (sourceLinkType) {
		case WEB:
			uriBuilder
				.setScheme(Constants.DEEPLINK_URI_PROTOCOL)
				.setHost(null)
				.setPath("//")
				.addParameter("Page", "Product")
				.addParameter("ContentId", productLinkModel.getContentId());
			if (!StringUtils.isNullOrEmpty(productLinkModel.getCampaignId())) {
				uriBuilder.addParameter("CampaignId", productLinkModel.getCampaignId());
			}
			if (!StringUtils.isNullOrEmpty(productLinkModel.getMerchantId())) {
				uriBuilder.addParameter("MerchantId", productLinkModel.getMerchantId());
			}
			break;

		case DEEPLINK:
			uriBuilder.setScheme("https")
				.setHost(Constants.WEB_URI_HOST)
				.setPath("/brand/name-p-" + productLinkModel.getContentId());
			if (!StringUtils.isNullOrEmpty(productLinkModel.getCampaignId())) {
				uriBuilder.addParameter("boutiqueId", productLinkModel.getCampaignId());
			}
			if (!StringUtils.isNullOrEmpty(productLinkModel.getMerchantId())) {
				uriBuilder.addParameter("merchantId", productLinkModel.getMerchantId());
			}
			break;
		}
		
		try {
			productLinkModel.setResponseUriString(uriBuilder.build().toString());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			throw new URIConversionException(ConversionErrorReason.COULD_NOT_BUILD_URI, uri.toString());
		}
	}
	

	public boolean isValidForUriAndLinkType(URI uri, LinkType linkType) {
		if (linkType == LinkType.WEB) {
			if (uri.getPath().matches(".+-p-[0-9]+")) {
				return true;
			}
		} else {
			List<NameValuePair> params = URLEncodedUtils.parse(uri, Charset.forName("UTF-8"));
			Map<String, String> paramMap = params.stream().collect(
			        Collectors.toMap(NameValuePair::getName, NameValuePair::getValue));
			if (paramMap.get("Page") != null && paramMap.get("Page").equals("Product")) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public LinkModel getRequestModel() {
		return productLinkModel;
	}

}
