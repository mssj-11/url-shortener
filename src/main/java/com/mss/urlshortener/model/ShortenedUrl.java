package com.mss.urlshortener.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortenedUrl {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La URL no puede estar vacía")
    @Pattern(regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$", 
             message = "Debe proporcionar una URL válida")
    private String originalUrl;

    @Column(unique = true, nullable = false)
    private String shortUrl;
    
    
}