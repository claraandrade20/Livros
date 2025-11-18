package com.catalogo.pedido.controller;
import com.catalogo.pedido.model.Pedido;
import com.catalogo.pedido.service.PedidoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping("/{usuarioId}/{livroId}")
    public Pedido criar(@PathVariable Long usuarioId,
                        @PathVariable Long livroId) {
        return service.criarPedido(usuarioId, livroId);
    }

    @GetMapping
    public List<Pedido> listar() {
        return service.listar();
    }
}