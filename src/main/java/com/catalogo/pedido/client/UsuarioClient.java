package com.catalogo.pedido.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.catalogo.pedido.dto.UsuarioDTO;

@FeignClient(name = "usuarios-service", url = "http://localhost:8081")
public interface UsuarioClient {
    
    @GetMapping("/usuarios/{id}")
    UsuarioDTO buscarUsuarioPorId(@PathVariable("id") Long id);
}
