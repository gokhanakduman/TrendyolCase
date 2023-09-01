package com.trendyol.linkConverter.model;

import java.net.URI;

public class LinkTuple {
	public String web;
	public String deeplink;
	private URI webUri;
	private URI deeplinkUri;
	
	public LinkTuple(String web, String deeplink) {
		this.web = web;
		this.deeplink = deeplink;
		try {
			this.webUri = new URI(web);
			this.deeplinkUri = new URI(deeplink);	
		} catch (Exception e) {

		}
	}

	public URI getWebUri() {
		return webUri;
	}

	public URI getDeeplinkUri() {
		return deeplinkUri;
	}

	
	
}
