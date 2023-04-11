package com.potarski.vethub.service;

import com.potarski.vethub.web.dto.auth.JwtRequest;
import com.potarski.vethub.web.dto.auth.JwtResponse;

public interface AuthService {

    JwtResponse login(JwtRequest loginRequest);
    JwtResponse refresh(String refreshToken);

}
