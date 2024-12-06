# URL - Shortener en Spring Boot

Este proyecto es una API para acortar URLs, utilizando Spring Boot y una base de datos H2. Permite tomar una URL larga y devolver una versión corta, que luego puede ser utilizada para redirigir al usuario a la URL original.

## Dependencias del Proyecto

El proyecto utiliza Maven para gestionar las dependencias. A continuación se detallan las dependencias principales de este proyecto:

### Dependencias Principales

1. **Spring Boot Starter Web/Test** 
2. **Spring Boot Starter Data JPA** 
3. **H2 Database** 
4. **Lombok** 
5. **Spring Boot Starter Validation** 


```
url-shortener
├── Dockerfile                  # Archivo para construir la imagen Docker
├── pom.xml                     # Archivo de configuración de Maven con dependencias
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── mss
│   │   │           └── urlshortener
│   │   │               ├── controller
│   │   │               │   └── UrlController.java     # Controlador de URLs
│   │   │               ├── dto
│   │   │               │   └── UrlRequestDTO.java     # DTO para la solicitud
│   │   │               ├── model
│   │   │               │   └── ShortenedUrl.java      # Entidad de la URL acortada
│   │   │               ├── repository
│   │   │               │   └── UrlRepository.java     # Repositorio para gestionar URLs
│   │   │               ├── service
│   │   │               │   └── UrlService.java        # Lógica de negocio de acortamiento
│   │   │               └── UrlShortenerApplication.java  # Clase principal de Spring Boot
│   │   └── resources
│   │       ├── application.properties  # Configuración del proyecto (puertos, base de datos)
│   │
├── .gitignore                    # Archivos y carpetas que Git debe ignorar
├── README.md                     # Documentación del proyecto
└── target/                        # Carpeta generada al compilar el proyecto (no se sube a Git)
```


##	Endpoints
####	POST 
`http://localhost:8080/api/shorten`
```json
{
  "longUrl": "https://www.google.com/"
}
```

####	GET
`http://localhost:8080/api/{shortUrl}` por ejemplo: `http://localhost:8080/api/E1kavz`