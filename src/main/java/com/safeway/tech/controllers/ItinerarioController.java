package com.safeway.tech.controllers;

import com.safeway.tech.services.ItinerarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/itinerarios")
public class ItinerarioController {

    @Autowired
    private ItinerarioService itinerarioService;

    public ResponseEntity<Void> buscarTodosItinerarios() {
        return ResponseEntity.ok().build();
    }

}
