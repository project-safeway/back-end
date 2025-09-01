package com.safeway.tech.controller;

import com.safeway.tech.entity.Usuarios;
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
    public Usuarios salvarUsuario(@RequestBody Usuarios usuario){
        return usuariosService.salvarUsuario(usuario);
    }

    @GetMapping
    public List<Usuarios> listarUsuarios() {
        return usuariosService.listarUsuarios();
    }

    @GetMapping("/{idUsuario}")
    public Usuarios retornarUm(@PathVariable int idUsuario){
        return usuariosService.retornarUm(idUsuario);
    }

    @DeleteMapping("/{idUsuario}")
    public void excluir(@PathVariable Integer idUsuario) {
        usuariosService.excluir(idUsuario);
    }

    @PutMapping("/{idUsuario}")
    public Usuarios alterarUsuario(@RequestBody Usuarios novoUsuario,@PathVariable int idUsuario){
        return usuariosService.alterarUsuario(novoUsuario, idUsuario);
    }


}