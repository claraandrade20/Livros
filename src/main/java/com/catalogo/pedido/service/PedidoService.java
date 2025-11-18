package com.catalogo.pedido.service;

import com.catalogo.pedido.dto.LivroDTO;
import com.catalogo.pedido.dto.UsuarioDTO;
import com.catalogo.pedido.model.Pedido;
import com.catalogo.pedido.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final RestTemplate restTemplate;

    public PedidoService(PedidoRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public Pedido criarPedido(Long usuarioId, Long livroId) {

        // CHAMAR MS-Usuarios
        UsuarioDTO usuario =
                restTemplate.getForObject("http://localhost:8082/usuarios/" + usuarioId, UsuarioDTO.class);

        // CHAMAR MS-Livros
        LivroDTO livro =
                restTemplate.getForObject("http://localhost:8081/livros/" + livroId, LivroDTO.class);

        if (usuario == null || livro == null) {
            return null; // ou lançar exception
        }

        return repository.save(new Pedido(usuarioId, livroId));
    }

    public List<Pedido> listar() {
        return repository.findAll();
    }
}
