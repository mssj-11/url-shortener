package com.mss.urlshortener.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class UrlResponseDTO {	// DTO de respuestas
	
    private Long id;
    private String longUrl;
    private String shortUrl;
    
}