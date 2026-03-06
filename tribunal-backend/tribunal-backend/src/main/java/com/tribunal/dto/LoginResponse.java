package com.tribunal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
/**
 * Clasa pentru răspunsul la autentificare.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private boolean success;
    private String message;
}
