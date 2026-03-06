package com.tribunal.dto;

import lombok.Getter;
import lombok.Setter;
/**
 * Clasa pentru datele de autentificare trimise de utilizator.
 * @author Buterez Daniela-Georgiana
 * @version ianuarie 2026
 */

@Getter
@Setter
public class LoginRequest {
    private String username;
    private String password;
}
