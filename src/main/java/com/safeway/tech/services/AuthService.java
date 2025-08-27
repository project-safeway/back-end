package com.safeway.tech.services;

import com.safeway.tech.config.CognitoConfig;
import com.safeway.tech.dto.AuthResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.ListUsersRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.ListUsersResponse;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Service
public class AuthService {

    private final CognitoIdentityProviderClient cognitoClient;
    private final CognitoConfig cognitoConfig;

    public AuthService(CognitoIdentityProviderClient cognitoClient, CognitoConfig cognitoConfig) {
        this.cognitoClient = cognitoClient;
        this.cognitoConfig = cognitoConfig;
    }

    public String register(String nome, String email, String senha) {
        String username = "user-" + UUID.randomUUID();
        String role = "COMMON";

        SignUpRequest signUpRequest = SignUpRequest.builder()
                .clientId(cognitoConfig.getClientId())
                .username(username)
                .password(senha)
                .userAttributes(
                        AttributeType.builder().name("email").value(email).build(),
                        AttributeType.builder().name("name").value(nome).build(),
                        AttributeType.builder().name("custom:role").value(role).build()
                )
                .build();

        SignUpResponse response = cognitoClient.signUp(signUpRequest);

        if (Boolean.TRUE.equals(cognitoConfig.getAutoConfirm())) {
            AdminConfirmSignUpRequest confirmRequest = AdminConfirmSignUpRequest.builder()
                    .userPoolId(cognitoConfig.getUserPoolId())
                    .username(username)
                    .build();
            cognitoClient.adminConfirmSignUp(confirmRequest);
        }

        return "Usuário registrado com sucesso! Sub: " + response.userSub();
    }

    public AuthResponse login(String email, String senha) {
        AdminInitiateAuthRequest authRequest = AdminInitiateAuthRequest.builder()
                .userPoolId(cognitoConfig.getUserPoolId())
                .clientId(cognitoConfig.getClientId())
                .authFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
                .authParameters(Map.of(
                        "USERNAME", email,
                        "PASSWORD", senha
                ))
                .build();

        AdminInitiateAuthResponse response = cognitoClient.adminInitiateAuth(authRequest);

        // Se o Cognito exigir algum desafio
        if (response.challengeName() != null) {
            System.out.println("Usuário precisa completar desafio: " + response.challengeName());
            System.out.println("Parâmetros do desafio: " + response.challengeParameters());

            if ("SMS_MFA".equals(response.challengeName())) {
                throw new RuntimeException("Usuário possui MFA habilitado, forneça código SMS para autenticação.");
            }

            if ("NEW_PASSWORD_REQUIRED".equals(response.challengeName())) {
                throw new RuntimeException("Usuário precisa redefinir a senha.");
            }

            // Outros tipos de desafio podem ser tratados aqui
            return null;
        }

        var result = response.authenticationResult();

        if (result == null) {
            throw new RuntimeException("Nenhum token recebido. Verifique se o usuário está confirmado.");
        }

        System.out.println("ID Token: " + result.idToken());
        System.out.println("Access Token: " + result.accessToken());
        System.out.println("Refresh Token: " + result.refreshToken());
        System.out.println("Expires in: " + result.expiresIn());

        return new AuthResponse(
                result.idToken(),
                result.accessToken(),
                result.refreshToken(),
                result.expiresIn()
        );
    }

    public void confirmSignUpByEmail(String email, String code) {
        // Buscar username pelo email
        ListUsersRequest listUsersRequest = ListUsersRequest.builder()
                .userPoolId(cognitoConfig.getUserPoolId())
                .filter("email = \"" + email + "\"")
                .limit(1)
                .build();

        ListUsersResponse listUsersResponse = cognitoClient.listUsers(listUsersRequest);

        if (listUsersResponse.users().isEmpty()) {
            throw new RuntimeException("Usuário com o e-mail informado não encontrado");
        }

        String username = listUsersResponse.users().get(0).username();
        System.out.println("Username encontrado: " + username);

        // Confirmar signup com username
        ConfirmSignUpRequest confirmRequest = ConfirmSignUpRequest.builder()
                .clientId(cognitoConfig.getClientId())
                .username(username)
                .confirmationCode(code)
                .build();

        ConfirmSignUpResponse response = cognitoClient.confirmSignUp(confirmRequest);
        System.out.println("Usuário confirmado com sucesso!");
    }
}
