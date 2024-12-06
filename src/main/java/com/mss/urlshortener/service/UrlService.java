package com.mss.urlshortener.service;

import com.mss.urlshortener.model.ShortenedUrl;
import com.mss.urlshortener.repository.UrlRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    public String shortenUrl(HttpServletRequest request, String originalUrl) {
        // Verificar si la URL ya existe en la base de datos
        Optional<ShortenedUrl> existing = urlRepository.findByOriginalUrl(originalUrl);
        if (existing.isPresent()) {
            return getBaseUrl(request) + "/api/" + existing.get().getShortUrl();
        }

        // Generar una nueva URL corta
        String shortUrl = generateShortCode();
        ShortenedUrl shortenedUrl = new ShortenedUrl();
        shortenedUrl.setOriginalUrl(originalUrl);
        shortenedUrl.setShortUrl(shortUrl);
        urlRepository.save(shortenedUrl);

        return getBaseUrl(request) + "/api/" + shortUrl;
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

    
    private String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme(); // http o https
        String host = request.getServerName(); // localhost o dominio
        int port = request.getServerPort(); // puerto
        return (port == 80 || port == 443) ? 
                String.format("%s://%s", scheme, host) : 
                String.format("%s://%s:%d", scheme, host, port);
    }
    
    
}