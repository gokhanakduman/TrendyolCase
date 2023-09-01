package com.trendyol.linkConverter.controller.dto;
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
@ApiModel(value = "Convert Link Response Object", description = "Model")
public class ConvertLinkResponseDto {
	@ApiModelProperty(value = "Converted URI String")
	 String uri;	 
}
