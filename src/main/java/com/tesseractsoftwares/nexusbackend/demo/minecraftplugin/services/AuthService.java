package com.tesseractsoftwares.nexusbackend.demo.minecraftplugin.services;

import com.tesseractsoftwares.nexusbackend.sdkjava.AuthClient;
import com.tesseractsoftwares.nexusbackend.sdkjava.callbacks.AuthCallbacks;
import com.tesseractsoftwares.nexusbackend.sdkjava.dtos.NexusAuthenticationRequestDto;

public class AuthService {

    private final AuthClient authClient;

    public AuthService(AuthClient authClient) {
        this.authClient = authClient;
    }

    public void authenticatePlayer(String email,
                                   String password,
                                   AuthCallbacks.OnSuccess onSuccess,
                                   AuthCallbacks.OnInvalidCredentials onInvalidCredentials,
                                   AuthCallbacks.OnError onError) {
        NexusAuthenticationRequestDto requestDto = new NexusAuthenticationRequestDto(email, password);
        authClient.authenticate(requestDto, onSuccess, onInvalidCredentials, onError);
    }
}
