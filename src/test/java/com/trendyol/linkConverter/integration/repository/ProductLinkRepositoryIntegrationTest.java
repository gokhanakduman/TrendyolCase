package com.trendyol.linkConverter.integration.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import com.trendyol.linkConverter.repository.ProductLinkRepository;
import com.trendyol.linkConverter.repository.model.ProductLinkEntityModel;
import com.trendyol.linkConverter.repository.model.ProductLinkEntityModel.ProductLinkEntityModelBuilder;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductLinkRepositoryIntegrationTest {

	@Resource private ProductLinkRepository productLinkRepository;
	
	@Test
	void productWithoutCampaignAndMerchantSaveTest(){
		ProductLinkEntityModel model = getProductBuilderBase().build();
		ProductLinkEntityModel savedModel = productLinkRepository.save(model);
		assertEquals(0, CompareToBuilder.reflectionCompare(model, savedModel, Set.of("id")));
	}

	@Test
	void productWithoutMerchantSaveTest(){
		ProductLinkEntityModel model = getProductBuilderBase()
				.campaignId("testCampaignId").build();
		ProductLinkEntityModel savedModel = productLinkRepository.save(model);
		assertEquals(0, CompareToBuilder.reflectionCompare(model, savedModel, Set.of("id")));
	}
	
	@Test
	void productWithoutCampaignSaveTest(){
		ProductLinkEntityModel model = getProductBuilderBase()
				.merchantId("testMerchantId").build();
		ProductLinkEntityModel savedModel = productLinkRepository.save(model);
		assertEquals(0, CompareToBuilder.reflectionCompare(model, savedModel, Set.of("id")));
	}
	
	@Test
	void productWithCampaignAndMerchantSaveTest(){
		ProductLinkEntityModel model = getProductBuilderBase()
				.campaignId("testCampaignId")
				.merchantId("testMerchantId").build();
		ProductLinkEntityModel savedModel = productLinkRepository.save(model);
		assertEquals(0, CompareToBuilder.reflectionCompare(model, savedModel, Set.of("id")));
	}
	
	@Test
	void productFailWithoutContentSaveTest(){
		ProductLinkEntityModel model = getProductBuilderBase()
				.contentId(null).build();
		assertThrows(DataIntegrityViolationException.class, () -> {
			productLinkRepository.save(model);
		  });
	}
	
	@Test
	void productFailWithoutRequestUriSaveTest(){
		ProductLinkEntityModel model = (ProductLinkEntityModel) getProductBuilderBase()
				.requestUri(null).build();
		assertThrows(DataIntegrityViolationException.class, () -> {		
			productLinkRepository.save(model);
			assertTrue(false);
		});
	}
	
	@Test
	void productFailWithoutResponseUriSaveTest(){
		ProductLinkEntityModel model = (ProductLinkEntityModel) getProductBuilderBase()
				.responseUri(null).build();
		assertThrows(DataIntegrityViolationException.class, () -> {	
			productLinkRepository.save(model);
		});
	}
	
	@Test
	void productFailWithoutRequestSourceSaveTest(){
		ProductLinkEntityModel model = (ProductLinkEntityModel) getProductBuilderBase()
				.requestSource(null).build();
		assertThrows(DataIntegrityViolationException.class, () -> {
			productLinkRepository.save(model);
		});
	}
	
	private ProductLinkEntityModelBuilder getProductBuilderBase() {
		return ProductLinkEntityModel.builder()
				.contentId("testContentId")
				.requestUri("testRequestUri")
				.responseUri("testResponseUri")
				.requestSource("WEB")
				;
	}
	
	
}
