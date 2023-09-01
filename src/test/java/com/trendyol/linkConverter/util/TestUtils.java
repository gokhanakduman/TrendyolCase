package com.trendyol.linkConverter.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendyol.linkConverter.controller.dto.ConvertLinkRequestDto;
import com.trendyol.linkConverter.model.DefaultLinkModel;
import com.trendyol.linkConverter.model.LinkType;
import com.trendyol.linkConverter.model.ProductLinkModel;
import com.trendyol.linkConverter.model.SearchLinkModel;
import com.trendyol.linkConverter.repository.DefaultLinkRepository;
import com.trendyol.linkConverter.repository.ProductLinkRepository;
import com.trendyol.linkConverter.repository.SearchLinkRepository;
import com.trendyol.linkConverter.repository.model.DefaultLinkEntityModel;
import com.trendyol.linkConverter.repository.model.ProductLinkEntityModel;
import com.trendyol.linkConverter.repository.model.SearchLinkEntityModel;
import com.trendyol.linkConverter.strategy.DefaultLinkConverterStrategy;
import com.trendyol.linkConverter.strategy.LinkConverterStrategyFactory;
import com.trendyol.linkConverter.strategy.ProductLinkConverterStrategy;
import com.trendyol.linkConverter.strategy.SearchLinkConverterStrategy;
import com.trendyol.linkConverter.strategy.exception.InvalidURIException;
import com.trendyol.linkConverter.strategy.exception.URIConversionException;

public class TestUtils {

	private static ObjectMapper objectMapper = new ObjectMapper();
	
	public static void checkIfGivenProductLinkSavedToDB(ProductLinkRepository repository, URI uri, LinkType linkType) throws InvalidURIException, URIConversionException {
		ProductLinkConverterStrategy productLinkConverterStrategy = new ProductLinkConverterStrategy();
		productLinkConverterStrategy.applyStrategy(uri, linkType);
		ProductLinkEntityModel entityModelToBeSaved = ProductLinkEntityModel.initFromProductLinkModel((ProductLinkModel)productLinkConverterStrategy.getRequestModel());
		ProductLinkEntityModel actualSavedEntityModel = repository.findTopByOrderByIdDesc();
		assertEquals(0, CompareToBuilder.reflectionCompare(entityModelToBeSaved, actualSavedEntityModel, Set.of("id")));
	}
	
	public static void checkIfGivenSearchLinkSavedToDB(SearchLinkRepository repository, URI uri, LinkType linkType) throws InvalidURIException, URIConversionException {
		SearchLinkConverterStrategy strategy = new SearchLinkConverterStrategy();
		strategy.applyStrategy(uri, linkType);
		SearchLinkEntityModel entityModelToBeSaved = SearchLinkEntityModel.initFromSearchLinkModel((SearchLinkModel)strategy.getRequestModel());
		SearchLinkEntityModel actualSavedEntityModel = repository.findTopByOrderByIdDesc();
		assertEquals(0, CompareToBuilder.reflectionCompare(entityModelToBeSaved, actualSavedEntityModel, Set.of("id")));
	}
	
	public static void checkIfGivenDefaultLinkSavedToDB(DefaultLinkRepository repository, URI uri, LinkType linkType) throws InvalidURIException, URIConversionException {
		DefaultLinkConverterStrategy strategy = new DefaultLinkConverterStrategy();
		strategy.applyStrategy(uri, linkType);
		DefaultLinkEntityModel entityModelToBeSaved = DefaultLinkEntityModel.initFromDefaultLinkModel((DefaultLinkModel)strategy.getRequestModel());
		DefaultLinkEntityModel actualSavedEntityModel = repository.findTopByOrderByIdDesc();
		assertEquals(0, CompareToBuilder.reflectionCompare(entityModelToBeSaved, actualSavedEntityModel, Set.of("id")));
	}
	
	public static void performMvcExpectError(MockMvc mvc, String path, String requestUriString, int errorStatus, String errorMessage) throws Exception {
		ConvertLinkRequestDto requestBody = new ConvertLinkRequestDto(requestUriString); 
		mvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().is(errorStatus))
                .andExpect(jsonPath("$.message", is(errorMessage)));
	}
	
	public static void performMvcExpectError(MockMvc mvc, String path, String requestUriString, int errorStatus) throws Exception {
		ConvertLinkRequestDto requestBody = new ConvertLinkRequestDto(requestUriString); 
		mvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().is(errorStatus));
	}
	
	public static void performMvcExpectSuccess(MockMvc mvc, String path, String requestUriString, String expectedUriString) throws Exception {
		ConvertLinkRequestDto requestBody = new ConvertLinkRequestDto(requestUriString); 
		mvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
				.andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.uri", is(expectedUriString)));
	}
	
	public static LinkConverterStrategyFactory linkConverterStrategyFactory() {
		return new LinkConverterStrategyFactory(
				List.of(
						ProductLinkConverterStrategy.class,
						SearchLinkConverterStrategy.class,
						DefaultLinkConverterStrategy.class)
				);
	}
}
