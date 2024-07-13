package br.com.fiap.postech.service_producao.service;

import br.com.fiap.postech.service_producao.entity.Pedido;
import br.com.fiap.postech.service_producao.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido receberPedido(String id) {
        Pedido pedido = new Pedido();
        pedido.setId(id);
        pedido.setStatus("Recebido");
        return pedidoRepository.save(pedido);
    }

    public Pedido atualizarStatusPedido(String id, String status) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        pedido.setStatus(status);
        return pedidoRepository.save(pedido);
    }
}
