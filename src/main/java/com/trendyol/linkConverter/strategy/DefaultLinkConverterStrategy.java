package com.trendyol.linkConverter.strategy;

import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.client.utils.URIBuilder;
import com.trendyol.linkConverter.model.DefaultLinkModel;
import com.trendyol.linkConverter.model.LinkModel;
import com.trendyol.linkConverter.model.LinkType;
import com.trendyol.linkConverter.strategy.exception.InvalidURIException;
import com.trendyol.linkConverter.strategy.exception.URIConversionException.ConversionErrorReason;
import com.trendyol.linkConverter.strategy.exception.URIConversionException;
import com.trendyol.linkConverter.util.Constants;

public class DefaultLinkConverterStrategy  extends LinkConverterStrategy{

	private DefaultLinkModel defaultLinkModel;
	
	public void applyStrategy(URI uri, LinkType sourceLinkType) throws InvalidURIException, URIConversionException {
		
		defaultLinkModel = new DefaultLinkModel();
		
		defaultLinkModel.setSourceLinkType(sourceLinkType);
		defaultLinkModel.setOriginalRequestURI(uri);
		
		String pathString = uri.toString().replaceFirst(uri.getScheme() + "\\/\\/" + uri.getHost(), "");
		defaultLinkModel.setPath(pathString);
				
		URIBuilder uriBuilder = new URIBuilder();
		switch (sourceLinkType) {
		case WEB:
			uriBuilder
				.setScheme(Constants.DEEPLINK_URI_PROTOCOL)
				.setHost(null)
				.setPath("//")
				.addParameter("Page", "Home");
			break;

		case DEEPLINK:
			uriBuilder.setScheme("https")
				.setHost(Constants.WEB_URI_HOST);
			break;
		}
		
		try {
			defaultLinkModel.setResponseUriString(uriBuilder.build().toString());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			throw new URIConversionException(ConversionErrorReason.COULD_NOT_BUILD_URI, uri.toString());
		}
	}
	
	
	@Override
	public LinkModel getRequestModel() {
		return defaultLinkModel;
	}

}
