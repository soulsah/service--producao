package br.com.fiap.postech.service_producao.repository;

import br.com.fiap.postech.service_producao.entity.Pedido;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface  PedidoRepository extends MongoRepository<Pedido, String> {
}