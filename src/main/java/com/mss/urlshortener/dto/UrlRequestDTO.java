package com.mss.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
//	Este DTO asegura que solo se procesen solicitudes con URLs válidas y no vacías, proporcionando mensajes claros en caso de errores.
@Data
public class UrlRequestDTO {	// DTO para las solicitudes de acortamiento de URLs

    @NotBlank(message = "La URL no puede estar vacía")
    // Valida que la URL tenga un formato correcto según los protocolos HTTP, HTTPS o FTP
    @Pattern(regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$", message = "Debe proporcionar una URL válida")
    private String longUrl;  // URL original que el usuario desea acortar
    
}