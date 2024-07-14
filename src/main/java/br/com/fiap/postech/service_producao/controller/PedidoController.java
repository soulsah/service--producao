package br.com.fiap.postech.service_producao.controller;

import br.com.fiap.postech.service_producao.entity.Pedido;
import br.com.fiap.postech.service_producao.enums.StatusPedido;
import br.com.fiap.postech.service_producao.exception.PedidoNotFoundException;
import br.com.fiap.postech.service_producao.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedido Controller", description = "Controlador para gerenciar pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/{id}")
    @Operation(summary = "Listar todos os pedidos", description = "Retorna uma lista de todos os pedidos")
    public Pedido getPedidoById(@PathVariable String id) throws PedidoNotFoundException {
        return pedidoService.getPedidoById(id);
    }

    @GetMapping
    @Operation(summary = "Buscar pedido por ID", description = "Retorna um pedido baseado no ID fornecido")
    public List<Pedido> getAllPedidos() {
        return pedidoService.getAllPedidos();
    }

    @PostMapping("/recebePedido/{id}")
    @Operation(summary = "Criar um novo pedido", description = "Cria um novo pedido com os dados fornecidos")
    public Pedido recebePedido(@PathVariable String id) {
        return pedidoService.receberPedido(id);
    }

    @PutMapping("/atualizaPedido/{id}")
    @Operation(summary = "Atualizar um pedido existente", description = "Atualiza um pedido existente com os dados fornecidos")
    public Pedido atualizaPedido(@PathVariable String id, @RequestParam String status) throws PedidoNotFoundException {
        StatusPedido statusPedido;
        try {
            statusPedido = StatusPedido.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status inválido!");
        }
        return pedidoService.atualizarStatusPedido(id, statusPedido);
    }
}