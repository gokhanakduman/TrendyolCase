package com.trendyol.linkConverter.repository.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntityModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;
	
	@Column(name = "request_uri", nullable = false)
    protected String requestUri;
	
	@Column(name = "response_uri", nullable = false)
	protected String responseUri;
	
	@Column(name = "request_source", nullable = false)
	protected String requestSource;
}
