package com.trendyol.linkConverter.util;

import java.net.URI;
import java.net.URISyntaxException;

import com.trendyol.linkConverter.model.LinkType;
import com.trendyol.linkConverter.strategy.exception.InvalidURIException;
import com.trendyol.linkConverter.strategy.exception.InvalidURIException.InvalidURIReason;

public class URIUtils {
	
	public static URI getUriFromStringForLinkType(String uriString, LinkType sourceLinkType) throws InvalidURIException {
		if (StringUtils.isNullOrEmpty(uriString)) {
			throw new InvalidURIException(InvalidURIReason.IS_NULL_OR_EMPTY, uriString);
		}
		URI uri = null;
		try {
			uri = new URI(uriString);
			if (uri.getScheme() == null) {
				throw new InvalidURIException(InvalidURIReason.SYNTAX_NOT_VALID, uriString);
			}
			if (!checkValidityOfUriForLinkType(uri, sourceLinkType)) {
				throw new InvalidURIException(sourceLinkType == LinkType.WEB ? InvalidURIReason.NOT_A_VALID_WEB_URI : InvalidURIReason.NOT_A_VALID_DEEPLINK, uriString);
			}
			return uri;
		} catch (URISyntaxException e) {
			throw new InvalidURIException(InvalidURIReason.SYNTAX_NOT_VALID, uriString);
		}
	}
	
	public static boolean checkValidityOfUriForLinkType(URI uri, LinkType sourceLinkType) {
		if (sourceLinkType == LinkType.WEB) {
			if( Constants.WEB_URI_PROTOCOLS.contains(uri.getScheme().toLowerCase()) 
				&& Constants.WEB_URI_HOST.equalsIgnoreCase(uri.getHost())) {
				return true;
			}
		} else if (sourceLinkType == LinkType.DEEPLINK) {
			if(uri.getScheme().equalsIgnoreCase(Constants.DEEPLINK_URI_PROTOCOL)
					&& StringUtils.isNullOrEmpty(uri.getHost())
					&& StringUtils.isNullOrEmpty(uri.getPath())) {
				return true;
			}
		}
		return false;
	}
}
