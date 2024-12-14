package com.mss.urlshortener.repository;

import com.mss.urlshortener.model.ShortenedUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/*
 * Esta interfaz actúa como puente entre la aplicación y la base de datos, 
 * facilitando la persistencia y consulta de las URLs.
 */
@Repository
public interface UrlRepository extends JpaRepository<ShortenedUrl, Long> { // Repositorio para gestionar URLs acortadas
    
    // Busca una URL acortada específica en la base de datos
    Optional<ShortenedUrl> findByShortUrl(String shortUrl);

    // Busca una URL original específica en la base de datos
    Optional<ShortenedUrl> findByOriginalUrl(String originalUrl);
}