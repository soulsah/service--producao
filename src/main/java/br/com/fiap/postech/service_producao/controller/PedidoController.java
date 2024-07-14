package br.com.fiap.postech.service_producao.controller;

import br.com.fiap.postech.service_producao.entity.Pedido;
import br.com.fiap.postech.service_producao.enums.StatusPedido;
import br.com.fiap.postech.service_producao.exception.PedidoNotFoundException;
import br.com.fiap.postech.service_producao.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/recebePedido/{id}")
    public Pedido recebePedido(@PathVariable String id) {
        return pedidoService.receberPedido(id);
    }

    @PutMapping("/atualizaPedido/{id}")
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