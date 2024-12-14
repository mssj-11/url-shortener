package com.mss.urlshortener.controller;

import com.mss.urlshortener.service.UrlService;
import com.mss.urlshortener.dto.UrlRequestDTO;
import com.mss.urlshortener.dto.UrlResponseDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }
    
    //	Get all shorts
    @GetMapping
    public ResponseEntity<List<UrlResponseDTO>> getAllUrls() {
        List<UrlResponseDTO> urls = urlService.getAllUrls();
        return ResponseEntity.ok(urls);
    }

    
    
    
    // Post short
    @PostMapping("/shorten")
    public ResponseEntity<Map<String, String>> shortenUrl(@Valid @RequestBody UrlRequestDTO request, BindingResult bindingResult, HttpServletRequest httpRequest) {
        if (bindingResult.hasErrors()) {
            // Obtener el primer mensaje de error y devolverlo en la respuesta
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(Map.of("error", errorMessage));
        }

        String originalUrl = request.getLongUrl();
        String shortUrl = urlService.shortenUrl(httpRequest, originalUrl);  // Aquí pasamos HttpServletRequest
        return ResponseEntity.ok(Map.of("shortUrl", shortUrl));
    }

    
    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortUrl) {
        String originalUrl = urlService.getOriginalUrl(shortUrl);
        return ResponseEntity.status(302).location(URI.create(originalUrl)).build();
    }
    
    
}