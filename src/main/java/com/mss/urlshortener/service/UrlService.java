package com.mss.urlshortener.service;

import com.mss.urlshortener.model.ShortenedUrl;
import com.mss.urlshortener.repository.UrlRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    @Value("${url.base}")
    private String baseUrl;

    public String shortenUrl(String originalUrl) {
        // Verificar si ya existe la URL en la base de datos
        Optional<ShortenedUrl> existing = urlRepository.findByOriginalUrl(originalUrl);
        if (existing.isPresent()) {
            return baseUrl + existing.get().getShortUrl();
        }

        // Generar una nueva URL corta
        String shortUrl = generateShortCode();
        ShortenedUrl shortenedUrl = new ShortenedUrl();
        shortenedUrl.setOriginalUrl(originalUrl);
        shortenedUrl.setShortUrl(shortUrl);
        urlRepository.save(shortenedUrl);

        return baseUrl + shortUrl;
    }

    public String getOriginalUrl(String shortUrl) {
        ShortenedUrl shortenedUrl = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new IllegalArgumentException("URL no encontrada"));
        return shortenedUrl.getOriginalUrl();
    }

    private String generateShortCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder shortCode = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            shortCode.append(characters.charAt(random.nextInt(characters.length())));
        }
        return shortCode.toString();
    }
    
    
}