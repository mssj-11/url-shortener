package com.mss.urlshortener.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

/*
 * Esta clase Proporciona un manejo centralizado para errores comunes, como la validación de datos, 
 * asegurando respuestas claras y consistentes a los usuarios.
 */
@ControllerAdvice
public class GlobalExceptionHandler {  // Clase para manejar excepciones de forma global en la aplicación.
	
	
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Extrae el resultado de la validación fallida
        BindingResult bindingResult = ex.getBindingResult();
        // Obtiene el mensaje del primer error encontrado
        String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
        // Devuelve una respuesta con el mensaje de error en formato JSON
        return ResponseEntity.badRequest().body(Map.of("error", errorMessage));
    }
    
    
}