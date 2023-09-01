package com.trendyol.linkConverter.controller.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Convert Link Request Object", description = "Model")
public class ConvertLinkRequestDto {
	@JsonAlias("uri")
	@JsonProperty("uri")
	@ApiModelProperty(value = "URI String to be converted")
	String uriString;	 
}
