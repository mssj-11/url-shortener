package com.mss.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class UrlRequestDTO {

    @NotBlank(message = "La URL no puede estar vacía")
    @Pattern(regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$", message = "Debe proporcionar una URL válida")
    private String longUrl;
    
}