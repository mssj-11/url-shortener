package com.mss.urlshortener.service;

import com.mss.urlshortener.dto.UrlResponseDTO;
import com.mss.urlshortener.model.ShortenedUrl;
import com.mss.urlshortener.repository.UrlRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;


//	Este servicio se encarga de gestionar las operaciones relacionadas con el acortamiento de URLs.
@Service
@RequiredArgsConstructor
public class UrlService {
    // Instancia al Repositorio para interactuar con la base de datos de URLs acortadas.
    private final UrlRepository urlRepository;
    /**
     * Obtiene todas las URLs almacenadas en la base de datos y las convierte en una lista
     * de objetos UrlResponseDTO para ser devueltas al cliente.
     *
     * @return Lista de URLs acortadas y sus correspondientes originales.
     */
    public List<UrlResponseDTO> getAllUrls() {
        return urlRepository.findAll()
                .stream()
                .map(url -> new UrlResponseDTO(url.getId(), url.getOriginalUrl(), url.getShortUrl()))
                .collect(Collectors.toList());
    }
    /**
     * Acorta una URL. Si la URL ya existe en la base de datos, devuelve la URL corta existente.
     * En caso contrario, genera una nueva URL corta, la almacena y devuelve.
     *
     * @param request      Objeto HttpServletRequest para obtener la URL base del servidor.
     * @param originalUrl  La URL original proporcionada por el cliente.
     * @return La URL corta generada o existente.
     */
    public String shortenUrl(HttpServletRequest request, String originalUrl) {
        // Verifica si la URL ya existe en la base de datos.
        Optional<ShortenedUrl> existing = urlRepository.findByOriginalUrl(originalUrl);
        if (existing.isPresent()) {
            return getBaseUrl(request) + "/api/" + existing.get().getShortUrl();
        }

        // Genera una nueva URL corta si no existe.
        String shortUrl = generateShortCode();
        ShortenedUrl shortenedUrl = new ShortenedUrl();
        shortenedUrl.setOriginalUrl(originalUrl);
        shortenedUrl.setShortUrl(shortUrl);
        urlRepository.save(shortenedUrl); // Guarda en la base de datos.

        return getBaseUrl(request) + "/api/" + shortUrl;
    }
    /**
     * Recupera la URL original asociada a una URL corta.
     *
     * @param shortUrl La URL corta proporcionada.
     * @return La URL original correspondiente.
     * @throws IllegalArgumentException si la URL corta no existe en la base de datos.
     */
    public String getOriginalUrl(String shortUrl) {
        ShortenedUrl shortenedUrl = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new IllegalArgumentException("URL no encontrada"));
        return shortenedUrl.getOriginalUrl();
    }
    /**
     * Genera un código aleatorio de 6 caracteres que se utilizará como parte de la URL corta.
     *
     * @return Código generado.
     */
    private String generateShortCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder shortCode = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            shortCode.append(characters.charAt(random.nextInt(characters.length())));
        }
        return shortCode.toString();
    }
    /**
     * Construye la URL base del servidor (incluye esquema, host y puerto).
     *
     * @param request Objeto HttpServletRequest para obtener la información del servidor.
     * @return URL base en formato "http(s)://host[:puerto]".
     */
    private String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme(); // Esquema: http o https.
        String host = request.getServerName(); // Nombre del servidor.
        int port = request.getServerPort(); // Puerto del servidor.
        return (port == 80 || port == 443) ?
                String.format("%s://%s", scheme, host) :
                String.format("%s://%s:%d", scheme, host, port);
    }
    
    
}