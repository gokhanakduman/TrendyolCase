package com.trendyol.linkConverter.integration.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import com.trendyol.linkConverter.repository.SearchLinkRepository;
import com.trendyol.linkConverter.repository.model.SearchLinkEntityModel;
import com.trendyol.linkConverter.repository.model.SearchLinkEntityModel.SearchLinkEntityModelBuilder;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class SearchLinkRepositoryIntegrationTest {

	@Resource private SearchLinkRepository searchLinkRepository;
	
	@Test
	void saveTest(){
		SearchLinkEntityModel model = getSearchBuilderBase()
				.query("query").build();
		SearchLinkEntityModel savedModel = searchLinkRepository.save(model);
		assertEquals(0, CompareToBuilder.reflectionCompare(model, savedModel, Set.of("id")));
	}

	@Test
	void nullSaveTest(){
		SearchLinkEntityModel model = getSearchBuilderBase()
				.query(null).build();
		assertThrows(DataIntegrityViolationException.class, () -> {
			searchLinkRepository.save(model);
		});
	}
	
	private SearchLinkEntityModelBuilder getSearchBuilderBase() {
		return SearchLinkEntityModel.builder()
				.requestUri("testRequestUri")
				.responseUri("testResponseUri")
				.requestSource("WEB")
				;
	}
	
	
}
