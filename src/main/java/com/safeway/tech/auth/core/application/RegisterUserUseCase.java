package com.safeway.tech.auth.core.application;

import com.safeway.tech.auth.core.exception.EmailAlreadyInUseException;
import com.safeway.tech.auth.core.exception.TransporteAlreadyInUseException;
import com.safeway.tech.auth.core.model.RegisterAuthUserData;
import com.safeway.tech.auth.core.model.RegisteredAuthUser;
import com.safeway.tech.auth.core.port.PasswordHasherPort;
import com.safeway.tech.auth.core.port.RegisterAuthUserPort;

public class RegisterUserUseCase {

    private final RegisterAuthUserPort registerAuthUserPort;
    private final PasswordHasherPort passwordHasherPort;

    public RegisterUserUseCase(
            RegisterAuthUserPort registerAuthUserPort,
            PasswordHasherPort passwordHasherPort
    ) {
        this.registerAuthUserPort = registerAuthUserPort;
        this.passwordHasherPort = passwordHasherPort;
    }

    public RegisterUserResult execute(RegisterUserCommand command) {
        if (registerAuthUserPort.existsByEmail(command.email())) {
            throw new EmailAlreadyInUseException();
        }

        String placaNormalizada = command.transporte().placa().trim().toUpperCase();
        if (registerAuthUserPort.existsByPlaca(placaNormalizada)) {
            throw new TransporteAlreadyInUseException();
        }

        RegisterAuthUserData data = new RegisterAuthUserData(
                command.nome(),
                command.email(),
                passwordHasherPort.hash(command.senha()),
                command.telefone(),
                placaNormalizada,
                command.transporte().modelo(),
                command.transporte().capacidade()
        );

        RegisteredAuthUser registeredAuthUser = registerAuthUserPort.create(data);

        return new RegisterUserResult(registeredAuthUser.userId(), registeredAuthUser.idTransporte());
    }
}
