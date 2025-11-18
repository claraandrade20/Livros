package com.catalogo.livros.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuarios-service")
public interface UsuarioClient {
    
    @GetMapping("/usuarios/{id}")
    Object buscarUsuarioPorId(@PathVariable("id") Long id);
}
