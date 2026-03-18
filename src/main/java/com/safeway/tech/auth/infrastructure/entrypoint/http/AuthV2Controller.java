package com.safeway.tech.auth.infrastructure.entrypoint.http;

import com.safeway.tech.auth.core.application.LoginCommand;
import com.safeway.tech.auth.core.application.LoginResult;
import com.safeway.tech.auth.core.application.LoginUseCase;
import com.safeway.tech.auth.core.application.RegisterTransporteCommand;
import com.safeway.tech.auth.core.application.RegisterUserCommand;
import com.safeway.tech.auth.core.application.RegisterUserUseCase;
import com.safeway.tech.auth.infrastructure.entrypoint.dto.AuthResponseV2;
import com.safeway.tech.auth.infrastructure.entrypoint.dto.LoginRequestV2;
import com.safeway.tech.auth.infrastructure.entrypoint.dto.RegisterRequestV2;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/v2")
@RequiredArgsConstructor
public class AuthV2Controller {

    private final LoginUseCase loginUseCase;
    private final RegisterUserUseCase registerUserUseCase;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestV2 request) {
        RegisterUserCommand command = new RegisterUserCommand(
                request.nome(),
                request.email(),
                request.senha(),
                request.telefone(),
                new RegisterTransporteCommand(
                        request.transporte().placa(),
                        request.transporte().modelo(),
                        request.transporte().capacidade()
                )
        );

        registerUserUseCase.execute(command);
        return ResponseEntity.ok("Usuário registrado com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseV2> login(@Valid @RequestBody LoginRequestV2 request) {
        LoginResult result = loginUseCase.execute(new LoginCommand(request.email(), request.senha()));

        AuthResponseV2 response = new AuthResponseV2(
                result.accessToken(),
                result.expiresIn(),
                result.nomeUsuario(),
                result.userId(),
                result.transporteId()
        );

        return ResponseEntity.ok(response);
    }
}
