package com.mss.urlshortener.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
/*	
 * Esta clase es un Data Transfer Object (DTO) que encapsula los datos que se envían en las respuestas del sistema de acortamiento de URLs. 
 * Este DTO facilita el envío estructurado de información desde el backend al frontend.
 */
@Data
@AllArgsConstructor
public class UrlResponseDTO {	// DTO para las respuestas de la API

    private Long id;  // Identificador único de la URL en la base de datos
    private String longUrl;  // La URL original proporcionada por el usuario
    private String shortUrl;  // La URL acortada generada por el sistema
    
}