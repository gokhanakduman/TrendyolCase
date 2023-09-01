package com.trendyol.linkConverter.strategy.exception;

public class URIConversionException extends Exception{
	
	public enum ConversionErrorReason {
		COULD_NOT_MAP_TO_MODEL,
		COULD_NOT_BUILD_URI
	}
	
	private static final long serialVersionUID = 1L;

	private ConversionErrorReason reason;
	private String requestUriString;
	
	public URIConversionException(ConversionErrorReason reason, String requestUriString) {
		super(	"\n\t------"
				+ "\n\tRequestUri:\t" + requestUriString
				+ "\n\tReason: \t" + reason.toString()
				+ "\n\t------"
				);
		this.reason = reason;
		this.requestUriString = requestUriString;
	}

	public ConversionErrorReason getReason() {
		return reason;
	}
	
	public String getRequestUriString() {
		return requestUriString;
	}
	
}