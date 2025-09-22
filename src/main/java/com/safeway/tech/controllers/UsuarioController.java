package com.safeway.tech.controllers;

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

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public Usuario salvarUsuario(@RequestBody Usuario usuario){
        return usuarioService.salvarUsuario(usuario);
    }

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{idUsuario}")
    public Usuario retornarUm(@PathVariable int idUsuario){
        return usuarioService.retornarUm(idUsuario);
    }

    @DeleteMapping("/{idUsuario}")
    public void excluir(@PathVariable Integer idUsuario) {
        usuarioService.excluir(idUsuario);
    }

    @PutMapping("/{idUsuario}")
    public Usuario alterarUsuario(@RequestBody Usuario novoUsuario,@PathVariable int idUsuario){
        return usuarioService.alterarUsuario(novoUsuario, idUsuario);
    }
}