package com.safeway.tech.controllers;

import com.safeway.tech.dto.UsuarioResponse;
import com.safeway.tech.models.Usuario;
import com.safeway.tech.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public UsuarioResponse retornarUm(@PathVariable Long idUsuario){
        return toResponse(usuarioService.retornarUm(idUsuario));
    }

    @DeleteMapping("/{idUsuario}")
    public void excluir(@PathVariable Long idUsuario) {
        usuarioService.excluir(idUsuario);
    }

    @PutMapping("/{idUsuario}")
    public UsuarioResponse alterarUsuario(@RequestBody Usuario novoUsuario,@PathVariable Long idUsuario){
        Usuario atualizado = usuarioService.alterarUsuario(novoUsuario, idUsuario);
        return toResponse(atualizado);
    }
}