package lk.ijse.finalcoursework.shoeshop.service;

import lk.ijse.finalcoursework.shoeshop.auth.request.SignInRequest;
import lk.ijse.finalcoursework.shoeshop.auth.request.SignUpRequest;
import lk.ijse.finalcoursework.shoeshop.auth.response.JWTAuthResponse;

/**
 * @author: Vishal Sandakelum,
 * @Runtime version: 11.0.11+9-b1341.60 amd64
 **/

public interface AuthenticationService {
    JWTAuthResponse signIn(SignInRequest signInRequest);
    JWTAuthResponse signUp(SignUpRequest signUpRequest);
    JWTAuthResponse updateaccount(SignUpRequest signUpRequest);
}
