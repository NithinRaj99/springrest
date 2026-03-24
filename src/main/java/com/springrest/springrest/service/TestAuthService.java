package com.springrest.springrest.service;

import com.springrest.springrest.dto.UserData;

public interface TestAuthService {

    public UserData getUserFromToken(String token) ;

}
