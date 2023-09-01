package com.trendyol.linkConverter.strategy.exception;

public class InvalidURIException extends Exception{
	
	public enum InvalidURIReason {
		IS_NULL_OR_EMPTY,
		SYNTAX_NOT_VALID,
		NOT_A_VALID_WEB_URI,
		NOT_A_VALID_DEEPLINK,
		CONTENT_ID_MISSING
	}
	
	private static final long serialVersionUID = 1L;

	private InvalidURIReason reason;
	private String requestUriString;
	
	public InvalidURIException(InvalidURIReason reason, String requestUriString) {
		super(	"\n\t------"
				+ "\n\tRequestUri:\t" + requestUriString
				+ "\n\tReason: \t" + getErrorDescriptionForReason(reason)
				+ "\n\t------"
				);
		this.reason = reason;
		this.requestUriString = requestUriString;
		
	}

	public InvalidURIReason getReason() {
		return reason;
	}
	
	public static String getErrorDescriptionForReason(InvalidURIReason reason) {
		switch (reason) {
		case IS_NULL_OR_EMPTY:
			 return "URI is null or empty";
		case SYNTAX_NOT_VALID:
			return  "URI syntax is invalid";
		case NOT_A_VALID_WEB_URI:
			return  "Provided WEB URI is not valid";
		case NOT_A_VALID_DEEPLINK:
			return "Provided DEEPLINK URI is not valid";
		case CONTENT_ID_MISSING:
			return "Product Content id is missing";
		}
		return null;
	}
	
	public String getErrorDescription() {
		return getErrorDescriptionForReason(reason);
	}

	public String getRequestUriString() {
		return requestUriString;
	}
}
