package com.nttdata.authentication.api.service;

public interface AuthService {

    String authenticate(String email, String password);

    boolean validateJwtToken(String token);
}
