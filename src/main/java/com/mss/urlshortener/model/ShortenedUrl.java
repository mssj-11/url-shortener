package com.mss.urlshortener.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
 * Esta clase representa la entidad/modelo que se utiliza para persistir en la base de datos la información de las URLs originales y sus equivalentes acortadas. 
 * Define las propiedades necesarias para almacenar una URL original, su versión acortada y un identificador único.
 */
@Entity // Marca esta clase como una entidad de JPA.
@Data // Genera automáticamente getters, setters, equals, hashCode y toString.
@AllArgsConstructor // Crea un constructor con todos los campos.
@NoArgsConstructor // Crea un constructor vacío.
public class ShortenedUrl {

    @Id // Indica que este campo es la clave primaria.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera un ID único de forma automática.
    private Long id; // Identificador único para cada entrada.

    @NotBlank(message = "La URL no puede estar vacía") // Valida que la URL original no esté vacía.
    @Pattern(regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$", 
             message = "Debe proporcionar una URL válida") // Valida que la URL original tenga un formato válido.
    private String originalUrl; // Almacena la URL original ingresada por el usuario.

    @Column(unique = true, nullable = false) // Asegura que cada URL acortada sea única y no nula.
    private String shortUrl; // Almacena la versión acortada de la URL.
    
}