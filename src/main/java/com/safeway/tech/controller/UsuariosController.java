package com.safeway.tech.controller;

import com.safeway.tech.entity.Usuario;
import com.safeway.tech.services.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {
    @Autowired
    private UsuariosService usuariosService;

    @PostMapping
    public Usuario salvarUsuario(@RequestBody Usuario usuario){
        return usuariosService.salvarUsuario(usuario);
    }

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuariosService.listarUsuarios();
    }

    @GetMapping("/{idUsuario}")
    public Usuario retornarUm(@PathVariable int idUsuario){
        return usuariosService.retornarUm(idUsuario);
    }

    @DeleteMapping("/{idUsuario}")
    public void excluir(@PathVariable Integer idUsuario) {
        usuariosService.excluir(idUsuario);
    }

    @PutMapping("/{idUsuario}")
    public Usuario alterarUsuario(@RequestBody Usuario novoUsuario,@PathVariable int idUsuario){
        return usuariosService.alterarUsuario(novoUsuario, idUsuario);
    }


}