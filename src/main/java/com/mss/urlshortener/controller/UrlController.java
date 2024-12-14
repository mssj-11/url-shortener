package com.mss.urlshortener.controller;

import com.mss.urlshortener.service.UrlService;
import com.mss.urlshortener.dto.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
/**
 * Controlador principal para manejar las operaciones relacionadas con URLs.
 * Proporciona endpoints para obtener todas las URLs, acortar una nueva URL y redirigir a una URL original desde su versión corta.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class UrlController {
	//	Instancia de la clase UrlService
    private final UrlService urlService;
    /**
     * Constructor para inyectar las dependencias necesarias del servicio URL.
     * @param urlService Servicio encargado de las operaciones con URLs.
     */
    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }
    /**
     * Endpoint para obtener una lista de todas las URLs almacenadas.
     * @return Lista de URLs en formato DTO.
     */
    @GetMapping
    public ResponseEntity<List<UrlResponseDTO>> getAllUrls() {
        List<UrlResponseDTO> urls = urlService.getAllUrls();
        return ResponseEntity.ok(urls);
    }
    /**
     * Endpoint para acortar una URL proporcionada por el usuario.
     * Valida que la URL sea válida antes de procesarla.
     * 
     * @param request Objeto DTO que contiene la URL original.
     * @param bindingResult Resultado de la validación.
     * @param httpRequest Objeto HTTP para capturar el contexto de la solicitud.
     * @return Respuesta con la URL acortada o un mensaje de error si no es válida.
     */
    @PostMapping("/shorten")
    public ResponseEntity<Map<String, String>> shortenUrl(
            @Valid @RequestBody UrlRequestDTO request, 
            BindingResult bindingResult, 
            HttpServletRequest httpRequest) {
        if (bindingResult.hasErrors()) {
            // Obtiene el primer mensaje de error de validación y lo devuelve al cliente.
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(Map.of("error", errorMessage));
        }

        String originalUrl = request.getLongUrl();
        // Llama al servicio para acortar la URL.
        String shortUrl = urlService.shortenUrl(httpRequest, originalUrl);
        return ResponseEntity.ok(Map.of("shortUrl", shortUrl));
    }
    /**
     * Endpoint para redirigir a la URL original usando su versión acortada.
     * 
     * @param shortUrl Versión corta de la URL.
     * @return Redirección a la URL original.
     */
    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortUrl) {
        // Obtiene la URL original desde el servicio.
        String originalUrl = urlService.getOriginalUrl(shortUrl);
        return ResponseEntity.status(302).location(URI.create(originalUrl)).build();
    }
    
    
}