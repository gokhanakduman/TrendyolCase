package com.trendyol.linkConverter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.trendyol.linkConverter.controller.dto.ConvertLinkRequestDto;
import com.trendyol.linkConverter.controller.dto.ConvertLinkResponseDto;
import com.trendyol.linkConverter.model.LinkType;
import com.trendyol.linkConverter.service.LinkConverterService;
import com.trendyol.linkConverter.strategy.exception.InvalidURIException;
import com.trendyol.linkConverter.strategy.exception.URIConversionException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(value = "LinkConverterService Api")
@RestController

public class HttpController {

	@Autowired
	private LinkConverterService linkConverterService;

	@PostMapping(value= "/v1/link/convertWebLink" , consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get Converted Link Http Endpoint")
	public @ResponseBody ConvertLinkResponseDto getConvertedWebLinkFromUri(@RequestBody ConvertLinkRequestDto body) throws InvalidURIException, URIConversionException {
		return new ConvertLinkResponseDto(linkConverterService.getConvertedUriString(body.getUriString(), LinkType.WEB));
		//return linkConverterService.getConvertedUriString(body, LinkType.WEB);
    }
	
	@PostMapping(value= "/v1/link/convertDeepLink" , consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get Converted Link Http Endpoint")
	public @ResponseBody ConvertLinkResponseDto getConvertedDeepLinkFromUri(@RequestBody ConvertLinkRequestDto body) throws InvalidURIException, URIConversionException {
		return new ConvertLinkResponseDto(linkConverterService.getConvertedUriString(body.getUriString(), LinkType.DEEPLINK));
		//return linkConverterService.getConvertedUriString(body, LinkType.DEEPLINK);
    }
}
