package com.example.controller;

import com.example.entity.Compra;
import com.example.service.ComprarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/compra")
public class CompraController {

    @Autowired
    private ComprarService comprarService;

    @PostMapping
    public ResponseEntity<Compra> comprar(@RequestBody Compra compra) {
        Compra compraRealizada=comprarService.CompraConDescuento(compra);
        return ResponseEntity.ok(compraRealizada);
    }
}
