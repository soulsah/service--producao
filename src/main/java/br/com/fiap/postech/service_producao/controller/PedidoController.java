package br.com.fiap.postech.service_producao.controller;

import br.com.fiap.postech.service_producao.entity.Pedido;
import br.com.fiap.postech.service_producao.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/recebePedido")
    public Pedido recebePedido(@RequestParam String id) {
        return pedidoService.receberPedido(id);
    }

    @PutMapping("/atualizaPedido/{id}")
    public Pedido atualizaPedido(@PathVariable String id, @RequestParam String status) {
        return pedidoService.atualizarStatusPedido(id, status);
    }
}