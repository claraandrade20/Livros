package com.catalogo.pedido.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.catalogo.pedido.dto.LivroDTO;

@FeignClient(name = "catalogo-livros-service")
public interface LivroClient {
    
    @GetMapping("/livros/{id}")
    LivroDTO buscarLivroPorId(@PathVariable("id") Long id);
}
