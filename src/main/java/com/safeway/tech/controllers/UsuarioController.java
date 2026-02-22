package com.safeway.tech.controllers;

import com.safeway.tech.dto.UsuarioFeignResponse;
import com.safeway.tech.dto.UsuarioResponse;
import com.safeway.tech.models.Usuario;
import com.safeway.tech.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    private UsuarioResponse toResponse(Usuario u) {
        return UsuarioResponse.fromEntity(u);
    }

    @PostMapping
    public UsuarioResponse salvarUsuario(@RequestBody Usuario usuario){
        Usuario salvo = usuarioService.salvarUsuario(usuario);
        return toResponse(salvo);
    }

    @GetMapping
    public List<UsuarioResponse> listarUsuarios() {
        return usuarioService.listarUsuarios()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{idUsuario}")
    public UsuarioResponse retornarUm(@PathVariable UUID idUsuario){
        return toResponse(usuarioService.retornarUm(idUsuario));
    }

    @DeleteMapping("/{idUsuario}")
    public void excluir(@PathVariable UUID idUsuario) {
        usuarioService.excluir(idUsuario);
    }

    @PutMapping("/{idUsuario}")
    public UsuarioResponse alterarUsuario(@RequestBody Usuario novoUsuario,@PathVariable UUID idUsuario){
        Usuario atualizado = usuarioService.alterarUsuario(novoUsuario, idUsuario);
        return toResponse(atualizado);
    }

    /*

        MÉTODOS USADOS NO FEIGN CLIENT

     */

    @GetMapping("/feign/{idUsuario}")
    public UsuarioFeignResponse buscarUsuario(@PathVariable UUID idUsuario) {
        Usuario usuario = usuarioService.retornarUm(idUsuario);
        return UsuarioFeignResponse.fromEntity(usuario);
    }
}