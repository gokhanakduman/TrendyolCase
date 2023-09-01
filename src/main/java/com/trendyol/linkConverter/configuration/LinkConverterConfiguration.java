package com.trendyol.linkConverter.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.trendyol.linkConverter.strategy.DefaultLinkConverterStrategy;
import com.trendyol.linkConverter.strategy.LinkConverterStrategy;
import com.trendyol.linkConverter.strategy.ProductLinkConverterStrategy;
import com.trendyol.linkConverter.strategy.SearchLinkConverterStrategy;
import com.trendyol.linkConverter.visitor.storage.PersistentStorageVisitor;
import com.trendyol.linkConverter.visitor.storage.SqlStorageVisitor;

@Configuration
public class LinkConverterConfiguration {

	@Bean
	PersistentStorageVisitor sqlPersistentStorageVisitor() {
		return new SqlStorageVisitor();
	}
	
	@Bean("LinkConverterStrategyClassesChain")
	public List<Class<? extends LinkConverterStrategy>> getStrategyClassesChain() {
		return List.of(
			ProductLinkConverterStrategy.class,
			SearchLinkConverterStrategy.class,
			DefaultLinkConverterStrategy.class);
	}
}
