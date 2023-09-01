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

import com.trendyol.linkConverter.repository.DefaultLinkRepository;
import com.trendyol.linkConverter.repository.model.DefaultLinkEntityModel;
import com.trendyol.linkConverter.repository.model.DefaultLinkEntityModel.DefaultLinkEntityModelBuilder;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class DefaultLinkRepositoryIntegrationTest {

	@Resource private DefaultLinkRepository defaultLinkRepository;
	
	@Test
	void saveTest(){
		DefaultLinkEntityModel model = getDefaultLinkBuilderBase()
				.requestPath("path").build();
		DefaultLinkEntityModel savedModel = defaultLinkRepository.save(model);
		assertEquals(0, CompareToBuilder.reflectionCompare(model, savedModel, Set.of("id")));
	}

	@Test
	void nullSaveTest(){
		DefaultLinkEntityModel model = getDefaultLinkBuilderBase()
				.requestPath(null).build();
		assertThrows(DataIntegrityViolationException.class, () -> {
			defaultLinkRepository.save(model);
		});
	}
	
	private DefaultLinkEntityModelBuilder getDefaultLinkBuilderBase() {
		return DefaultLinkEntityModel.builder()
				.requestUri("testRequestUri")
				.responseUri("testResponseUri")
				.requestSource("WEB")
				;
	}
	
	
}
