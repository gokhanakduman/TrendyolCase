package com.trendyol.linkConverter.util;

import java.util.List;

import com.trendyol.linkConverter.model.LinkTuple;

public class TestConstants {
	
	public static final String WEB_LINK_CONVERT_ENDPOINT_PATH = "/v1/link/convertWebLink";
	public static final String DEEP_LINK_CONVERT_ENDPOINT_PATH = "/v1/link/convertDeepLink";
	
	public static final List<LinkTuple> VALID_PRODUCT_URI_TUPLES_LIST = List.of(
			new LinkTuple(
					"https://www.trendyol.com/brand/name-p-1925865?boutiqueId=439892&merchantId=105064",
					"ty://?Page=Product&ContentId=1925865&CampaignId=439892&MerchantId=105064"),
			new LinkTuple(
					"https://www.trendyol.com/brand/name-p-1925865?boutiqueId=439892",
					"ty://?Page=Product&ContentId=1925865&CampaignId=439892"),
			new LinkTuple(
					"https://www.trendyol.com/brand/name-p-1925865?merchantId=105064",
					"ty://?Page=Product&ContentId=1925865&MerchantId=105064"),
			new LinkTuple(
					"https://www.trendyol.com/brand/name-p-1925865",
					"ty://?Page=Product&ContentId=1925865"
					)
		);
	
	public static final List<LinkTuple> VALID_SEARCH_URI_TUPLES_LIST = List.of(
			new LinkTuple(
					"https://www.trendyol.com/sr?q=439892",
					"ty://?Page=Search&Query=439892"
					),
			new LinkTuple(
					"https://www.trendyol.com/sr?q=qwasdbad",
					"ty://?Page=Search&Query=qwasdbad"
					),
			new LinkTuple(
					"https://www.trendyol.com/sr?q=%C3%BCt%C3%BC",
					"ty://?Page=Search&Query=%C3%BCt%C3%BC"
					)
		);
	
	public static final List<LinkTuple> VALID_DEFAULT_WEB_TO_DEEP_LINK_TUPLES = List.of(
			new LinkTuple(
					"https://www.trendyol.com/Hesabim/Favoriler",
					"ty://?Page=Home"),
			new LinkTuple(
					"https://www.trendyol.com/Hesabim/#/Siparislerim",
					"ty://?Page=Home")
		);
	
	public static final List<LinkTuple> VALID_DEFAULT_DEEPLINK_TO_WEB_URI_TUPLES_LIST = List.of(
			new LinkTuple(
					"https://www.trendyol.com",
					"ty://?Page=Favorites"),
			new LinkTuple(
					"https://www.trendyol.com",
					"ty://?Page=Orders")
		);
	
	public static final List<String> INVALID_URI_LIST = List.of(
			"ftp://www.trendyol.com/Hesabim/Favoriler",
			"tty://?Page=Orders",
			"https://www.google.com"
		);
	
	public static final List<String> MALFORMED_URI_LIST = List.of(
			"http-//example.com:-80/",
			"\\uD800\\uDFFF",
			"JAVAsqw:asda//123QW-qw12"
		);
}
